package sg.shopster.android.monitors;

import sg.shopster.android.core.ShopsterDao;
import sg.shopster.android.frags.builders.ChangeObserver;
import sg.shopster.android.monitors.dm.FavouriteEntry;
import android.text.TextUtils;

/**
 * Monitors objects of type "favourite". Has change observer which notifies
 * subscribers of state change.
 * 
 * @author "Alexey Dmitriev"
 * 
 */
public abstract class FavouriteMonitor<T extends FavouriteEntry> 
	extends BaseObjectMonitor<T> {

	/** notifies subscribers about changes */
	private ChangeObserver favObserver;

	public FavouriteMonitor(Class<T> clazz) {
		super(clazz);
		favObserver = new ChangeObserver();
	}

	@Override
	public void use(int id, String type) {
		T item = createItem();

		int compositeId = id;
		if (!TextUtils.isEmpty(type)) {
			compositeId = idHashCode(id, type);
		} else {
			compositeId = id;
		}

		item.setId(compositeId);
		item.setOwnerId(id);
		item.setOwnerType(type);
		item.setTimestamp(System.currentTimeMillis());

		ShopsterDao.INSTANCE.saveEntry(item);
		reloadEntries();
	}

	@Override
	protected void reloadEntries() {
		super.reloadEntries();
		if (favObserver != null)
			favObserver.notifyObservers();
	}

	public ChangeObserver getChangeObserver() {
		return favObserver;
	}
}
