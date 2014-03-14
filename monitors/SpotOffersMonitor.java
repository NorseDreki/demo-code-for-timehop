package sg.shopster.android.monitors;

import sg.shopster.android.core.ShopsterDao;
import sg.shopster.android.dm.BaseSpot;
import sg.shopster.android.monitors.dm.SpotOffersEntry;

/**
 * Monitors whether offers of the spot were shared or not.
 * 
 * @author "Alexey Dmitriev"
 * 
 */
public class SpotOffersMonitor extends DayObjectMonitor<SpotOffersEntry> {

	public SpotOffersMonitor(Class<SpotOffersEntry> clazz) {
		super(clazz);
	}

	@Override
	protected SpotOffersEntry createItem() {
		return new SpotOffersEntry();
	}

	@Override
	public boolean isUsed(int id, String type) {
		boolean res = super.isUsed(id, type);
		
		BaseSpot bs = ShopsterDao.INSTANCE.getSpot(id, type);
		boolean noOffersForSharing = bs.getOffersShareCount() == 0;
		
		return res || noOffersForSharing;
	}
}
