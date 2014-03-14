package sg.shopster.android.monitors;

import java.util.List;

import sg.shopster.android.core.ShopsterDao;
import sg.shopster.android.dm.entities.Offer;
import sg.shopster.android.monitors.dm.OfferShareEntry;

/**
 * Monitors whether the offer was shared.
 */
public class OfferShareMonitor extends DayObjectMonitor<OfferShareEntry> {

	public OfferShareMonitor(Class<OfferShareEntry> clazz) {
		super(clazz);
	}

	@Override
	protected OfferShareEntry createItem() {
		return new OfferShareEntry();
	}
	
	@Override
	public void use(int id, String type) {
		super.use(id, type);

		List<Offer> entries = ShopsterDao.INSTANCE.getEntries(Offer.class, "id", id);
		if (entries.size() > 0) {
			Offer o = entries.get(0);
			
			if (isEverythingUsed(o))
				Monitors.INSTANCE.getSpotOffersMonitor().use(o.getOwnerId(), 
						o.getOwnerType());

		}
	}

	private boolean isEverythingUsed(Offer o) {
		boolean res = true;

		List<Offer> entries = ShopsterDao.INSTANCE.getOffers(
				o.getOwnerType(), o.getOwnerId());

		for (Offer entry : entries)
			if (!isUsed(entry.getId(), null)) {
				res = false;
				break;
			}

		return res;
	}
}