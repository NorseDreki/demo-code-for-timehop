package sg.shopster.android.monitors;

import java.sql.SQLException;
import java.util.List;

import sg.shopster.android.core.ShopsterDao;
import sg.shopster.android.monitors.dm.MonitorEntry;
import android.text.TextUtils;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * Base implementation of monitor.
 * 
 * @author "Alexey Dmitriev"
 */
public abstract class BaseObjectMonitor<T extends MonitorEntry> implements IObjectMonitor {
	
	/** class of type of monitor entry */
	private Class<T> clazz;
	
	/** entries belonging to this monitor */
	protected List<T> entries;

	/** subclasses implement to create items */
	protected abstract T createItem();
	
	public BaseObjectMonitor(Class<T> clazz) {
		this.clazz = clazz;
		
		ConnectionSource cs = ShopsterDao.INSTANCE.getHelper().getConnectionSource();
		try {
			TableUtils.createTableIfNotExists(cs, clazz);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		reloadEntries();
	}

	@Override
	public void use(int id, String type) {
		int compositeId = id;
		if (!TextUtils.isEmpty(type)) {
			compositeId = idHashCode(id, type);
		} else {
			compositeId = id;
		}
		
		T item = createItem();
		item.setId(compositeId);
		item.setTimestamp(System.currentTimeMillis());
		ShopsterDao.INSTANCE.saveEntry(item);
		reloadEntries();
	}

	@Override
	public void unuse(int id, String type) {
		int compositeId = id;
		if (!TextUtils.isEmpty(type)) {
			compositeId = idHashCode(id, type);
		} else {
			compositeId = id;
		}
		
		ShopsterDao.INSTANCE.deleteEntry(clazz, compositeId);
		reloadEntries();
	}
	
	@Override
	public boolean isUsed(int id, String type) {
		boolean res = false;
		
		int compositeId = id;
		if (!TextUtils.isEmpty(type)) {
			compositeId = idHashCode(id, type);
		} else {
			compositeId = id;
		}

		for (T entry : entries)
			if (entry.getId() == compositeId) 
				res = true;

		return res;
	}
	
	/** reloads monitor entries from disk */
	protected void reloadEntries() {
		entries = ShopsterDao.INSTANCE.getEntries(clazz);
	}
	
	protected int idHashCode(int id, String type) {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result
				+ ((type == null) ? 0 : type.hashCode());
		return result;
	}
	
	public List<T> getEntries() {
		return entries;
	}
	
	/** deletes all the entries from disk */
	public void deleteEntries() {
		ShopsterDao.INSTANCE.deleteEntries(getEntries());
		reloadEntries();
	}
}
s