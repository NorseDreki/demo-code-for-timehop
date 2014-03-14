package net.ccs.android.requests;

import java.io.UnsupportedEncodingException;

import net.ccs.android.core.LOG;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

/**
 * Base request which can parse server JSON response with GSON.
 * 
 * @author "Alexey Dmitriev"
 *
 * @param <T> type of response expected from server
 */
public class BaseRequest<T> extends Request<T> {
	
	/** parsing engine */
	protected final Gson gson;
	
	/** class of the type of response */
	protected final Class<T> clazz;
	
	/** result listener */
	private final Listener<T> listener;

	/**
	 * Make a GET request and return a parsed object from JSON.
	 *
	 * @param url URL of the request to make
	 * @param clazz Relevant class object, for Gson's reflection
	 */
	public BaseRequest(int method, String url, Class<T> clazz, Listener<T> listener, 
			ErrorListener errorListener) {
		
		super(method, url, errorListener);
		this.clazz = clazz;
		this.listener = listener;
		this.gson = new GsonBuilder().setDateFormat(RestUrl.DATE_FORMAT).create();

		setShouldCache(false);
	}

	@Override
	protected void deliverResponse(T response) {
		listener.onResponse(response);
	}
	
	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response) {
		try {
			String json = new String(
					response.data, HttpHeaderParser.parseCharset(response.headers));
			
			if (Logging.REQUESTS)
				LOG.v("%s: %s", clazz.getSimpleName(), json);
			
			return Response.success(gson.fromJson(json, clazz),
					HttpHeaderParser.parseCacheHeaders(response));
			
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return Response.error(new ParseError(e));
		}
	}
}
