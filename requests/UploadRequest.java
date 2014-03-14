package net.ccs.android.requests;

import net.ccs.android.core.LOG;

import com.android.volley.AuthFailureError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Request which converts object queued to upload to JSON format.
 * 
 * @author "Alexey Dmitriev"
 *
 * @param <T> type of object to be uploaded
 */
public class UploadRequest<T> extends BaseRequest<T> {

	/** object to be uploaded */
	private Object toUpload;

	/** numerical code for verb (GET / POST / PUT) */
	private int method;
	
	/** converts objects to JSON */
	private Gson gson;

	public UploadRequest(int method, String url, Class<T> clazz, Listener<T> listener, 
			ErrorListener errorListener, Object toUpload) {

		super(method, url, clazz, listener, errorListener);
		this.method = method;
		this.toUpload = toUpload;

		gson = new GsonBuilder().
				excludeFieldsWithoutExposeAnnotation().
				serializeNulls().
				setDateFormat(RestUrl.DATE_FORMAT).
				create();
	}

	@Override
	public byte[] getBody() throws AuthFailureError {
		if (toUpload != null) {
			String json = gson.toJson(toUpload);
			
			if (Logging.REQUESTS)
				LOG.v("UPLOAD: %d, %s: %s", method, clazz.getSimpleName(), json);

			return json.getBytes();
			
		} else {
			
			return super.getBody();
		}
	}

	@Override
	public String getBodyContentType() {
		if (toUpload != null) {
			return "application/json";
		} else {
			return super.getBodyContentType();
		}
	}
}
