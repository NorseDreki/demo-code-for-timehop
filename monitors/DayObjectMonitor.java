package sg.shopster.android.monitors;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

import android.text.TextUtils;

import sg.shopster.android.monitors.dm.MonitorEntry;

/**
 * Monitor which expires in 24 hours.
 * 
 * @author "Alexey Dmitriev"
 */
public abstract class DayObjectMonitor<T extends MonitorEntry> extends BaseObjectMonitor<T> {

	public DayObjectMonitor(Class<T> clazz) {
		super(clazz);
	}

	@Override
	protected abstract T createItem();

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
			if (entry.getId() == compositeId) {
				Date now = new Date();
				Date stamp = new Date(entry.getTimestamp());

				if (DateUtils.isSameDay(stamp, now)) {
					res = true;
				} else {
					unuse(id, type);
				}
			}

		return res;
	}
}
