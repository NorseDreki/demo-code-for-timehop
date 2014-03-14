package net.ccs.android.helpers;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import android.support.v4.content.Loader;

/**
 * Keeps a list of weekly coupled loaders which are interested in
 * content change notifications.
 * 
 * @author "Alexey Dmitriev"
 * 
 */
public class ChangeObserver {
	
	/** list of all observers to be notified */
	private List<WeakReference<Loader<?>>> observers;
	
	/** whether notifications are allowed */
	private boolean enabled;

	public ChangeObserver() {
		observers = new ArrayList<WeakReference<Loader<?>>>();
		enableNotifications();
	}

	/** 
	 * Notifies all remaining observers about content change. 
	 * */
	public void notifyObservers() {
		if (enabled)
			for (WeakReference<Loader<?>> wr : observers)
				if (wr.get() != null)
					wr.get().onContentChanged();
	}

	/** 
	 * Registers the given observer for notifications.
	 */
	public void registerObserver(Loader<?> loader) {
		if (loader != null)
			observers.add(new WeakReference<Loader<?>>(loader));
	}
	
	/**
	 * Allows notifications to be received by observers.
	 */
	public void enableNotifications() {
		enabled = true;
	}

	/**
	 * Disallows notifications to be received by observers.
	 */
	public void disableNotifications() {
		enabled = false;
	}
}
