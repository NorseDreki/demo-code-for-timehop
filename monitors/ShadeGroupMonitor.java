package sg.shopster.android.monitors;

import java.util.List;

import sg.shopster.android.core.ShopsterDao;
import sg.shopster.android.dm.entities.ShadeGroup;
import sg.shopster.android.monitors.dm.ShadeGroupEntry;

/**
 * Monitors whether user checked in at this shade group.
 * 
 * @author "Alexey Dmitriev"
 */
public class ShadeGroupMonitor extends DayObjectMonitor<ShadeGroupEntry> {

	public ShadeGroupMonitor(Class<ShadeGroupEntry> clazz) {
		super(clazz);
	}

	@Override
	protected ShadeGroupEntry createItem() {
		return new ShadeGroupEntry();
	}

	@Override
	public void use(int id, String type) {
		super.use(id, type);

		List<ShadeGroup> entries = 
				ShopsterDao.INSTANCE.getEntries(ShadeGroup.class, "id", id);
		
		if (entries.size() > 0) {
			ShadeGroup sg = entries.get(0);
			
			if (sg.isEntrance()) 
				Monitors.INSTANCE.getSpotEntranceMonitor().use(sg.getOwnerId(), 
						sg.getOwnerType());
				
			if (isEverythingUsed(sg))
				Monitors.INSTANCE.getSpotCheckinMonitor().use(sg.getOwnerId(), 
						sg.getOwnerType());
		}
	}

	/** Whether all shade groups of the owner were checked in */
	private boolean isEverythingUsed(ShadeGroup sg) {
		boolean res = true;

		List<ShadeGroup> entries = ShopsterDao.INSTANCE.getShadeGroups(
				sg.getOwnerType(), sg.getOwnerId());

		for (ShadeGroup entry : entries)
			if (!isUsed(entry.getId(), null)) {
				res = false;
				break;
			}

		return res;
	}
}
