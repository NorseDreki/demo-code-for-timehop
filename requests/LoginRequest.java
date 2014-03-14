package net.ccs.android.requests;

import java.util.concurrent.ExecutionException;

import net.ccs.android.core.VolleyQueue;
import net.ccs.android.core.dao.ChildCareDao;
import net.ccs.android.helpers.DebugHelper;
import net.ccs.android.model.ApplicationUser;
import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.RequestFuture;

/**
 * Network request which logs in user to the system. Server returns actual user
 * information which is then saved locally.
 * 
 * @author "Alexey Dmitriev"
 */
public class LoginRequest extends BaseRequest<ApplicationUser> {
	
	/** user name */
	private String username;
	
	/** corresponding password */
	private String password;
	
	/** context which calls this request */
	private Context context;

	private LoginRequest(String username, String password, Listener<ApplicationUser> listener,
			ErrorListener errorListener, Context context) {
		
		super(Method.POST, RestUrl.fullUrl(RestUrl.LOGIN), ApplicationUser.class, 
				listener, errorListener);
		
		this.context = context;
		this.username = username;
		this.password = password;
	}
	
	/**
	 * Creates login request and blocks until it returns. Executed in the 
	 * background thread.
	 * 
	 * @param login
	 * @param password
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	public static void invoke(String login, String password, Context context) 
			throws ExecutionException, InterruptedException {
		
		RequestFuture<ApplicationUser> loginFuture = RequestFuture.newFuture();

		LoginRequest loginRequest = 
				new LoginRequest(login, password, loginFuture, loginFuture, context);
		
		VolleyQueue.get().add(loginRequest);

		ApplicationUser appUser = loginFuture.get();
		appUser.setUsername(login);
		appUser.setPassword(password);
		
		ChildCareDao.INSTANCE.reconfigure(appUser);
	}

	@Override
	public byte[] getBody() throws AuthFailureError {
		Account account = new Account();
		account.username = username;
		account.password = password;
		account.deviceId = DebugHelper.getMacAddress(context);
		account.appVersion = DebugHelper.getAppVersion(context);
		
		String json = gson.toJson(account);
		
		return json.getBytes();
	}

	@Override
	public String getBodyContentType() {
		 return "application/json";
	}
	
	/**
	 * Helper class which holds credentials information to be used when
	 * performing /login call.
	 */
	@SuppressWarnings("unused")
	private static class Account {
		String username;
		String password;
		String deviceId;
		String appVersion;
	}
}
