package net.ccs.android.requests;

import java.util.List;
import java.util.concurrent.ExecutionException;

import net.ccs.android.core.VolleyQueue;
import net.ccs.android.core.dao.ChildCareDao;
import net.ccs.android.events.RestEvent;
import net.ccs.android.model.ServerEntity;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.RequestFuture;

/**
 * Common request used to upload pending data to server, or download new data
 * and store locally.
 * 
 * @author "Alexey Dmitriev"
 * 
 * @param <T> type of response expected from server
 */
public class ChildCareRequest<T> extends UploadRequest<T> {

	/** holds request result, blocks until execution is finished */
	private RequestFuture<T> future;

	/**
	 * Performs POST or PUT operation to the specified URL, throws an exception
	 * if interrupted by user or if network exception happens.
	 * 
	 * @param method numeric code for verb (POST or PUT)
	 * @param relativeUrl address of target method on server
	 * @param doneEvent object which will be filled after request completes
	 * @param clazz class of object which is about to be uploaded
	 * @param clazzOfArr class of array of objects which is about to be uploaded
	 * @throws ExecutionException if network exception happens
	 * @throws InterruptedException if interrupted by user
	 */
	public static <E extends ServerEntity> void upload(int method, String relativeUrl, 
			RestEvent doneEvent, Class<E> clazz, Class<E[]> clazzOfArr) 
					throws ExecutionException, InterruptedException {

		List<E> entriesToUpload = ChildCareDao.INSTANCE.getEntriesToUpload(method, clazz);

		RequestFuture<E[]> future = RequestFuture.newFuture();
		String fullUrl = RestUrl.fullUrl(relativeUrl);

		ChildCareRequest<E[]> request = new ChildCareRequest<E[]>(method, fullUrl, 
				clazzOfArr, future, entriesToUpload);

		request.setTag(ChildCareRequest.class);
		E[] returnedEntries = request.executeSync();

		if (returnedEntries != null) {
			doneEvent.addProgressMessage(method, clazzOfArr, returnedEntries);
			ChildCareDao.INSTANCE.clearUploadStatus(method, entriesToUpload, returnedEntries);
		}
	}

	/**
	 * Performs GET operation to the specified URL, throws an exception
	 * if interrupted by user or if network exception happens.
	 * 
	 * @param relativeUrl address of target method on server
	 * @param doneEvent object which will be filled after request completes
	 * @param clazz class of object which is about to be downloaded
	 * @throws ExecutionException if network exception happens
	 * @throws InterruptedException if interrupted by user
	 */
	public static <E> void download(String relativeUrl, RestEvent doneEvent, Class<E> clazz) 
			throws ExecutionException, InterruptedException {

		if (Thread.currentThread().isInterrupted())
			throw new InterruptedException("Interrupted by user");

		RequestFuture<E> future = RequestFuture.newFuture();
		String fullUrl = RestUrl.fullUrl(relativeUrl);

		ChildCareRequest<E> request = new ChildCareRequest<E>(Method.GET, fullUrl, clazz, 
				future, null);

		request.setTag(ChildCareRequest.class);
		E returnedEntries = request.executeSync();

		doneEvent.addProgressMessage(Method.GET, clazz, returnedEntries);
		ChildCareDao.INSTANCE.importEntries(returnedEntries);
	}

	/** */
	private ChildCareRequest(int method, String url, Class<T> clazz, RequestFuture<T> future, 
			Object toUpload) {
		
		super(method, url, clazz, future, future, toUpload);
		this.future = future;
	}

	/** */
	private ChildCareRequest(int method, String url, Class<T> clazz, Listener<T> listener, 
			ErrorListener errorListener, Object toUpload) {
		
		super(method, url, clazz, listener, errorListener, toUpload);
	}

	/** */
	private ChildCareRequest(String url, Class<T> clazz, Listener<T> listener, 
			ErrorListener errorListener) {

		super(Method.GET, 
				url,
				clazz,
				listener, 
				errorListener,
				null);
	}

	/** Block until request execution completes */
	private T executeSync() throws InterruptedException, ExecutionException {
		VolleyQueue.get().add(this);
		
		return future.get();
	}
}
