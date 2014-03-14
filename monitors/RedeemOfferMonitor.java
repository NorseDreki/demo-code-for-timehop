package sg.shopster.android.monitors;

import sg.shopster.android.monitors.dm.RedeemOfferEntry;

/**
 * Monitors whether user redeemed the offer
 */
public class RedeemOfferMonitor extends BaseObjectMonitor<RedeemOfferEntry> {

	public RedeemOfferMonitor(Class<RedeemOfferEntry> clazz) {
		super(clazz);
	}

	@Override
	protected RedeemOfferEntry createItem() {
		return new RedeemOfferEntry();
	}
}
