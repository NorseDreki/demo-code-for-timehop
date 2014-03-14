package sg.shopster.android.monitors;

import sg.shopster.android.monitors.dm.TutorialShownEntry;

/**
 * Monitors whether a tutorial screen was shown to user.
 */
public class TutorialShownMonitor extends BaseObjectMonitor<TutorialShownEntry> {

	public TutorialShownMonitor(Class<TutorialShownEntry> clazz) {
		super(clazz);
	}

	@Override
	protected TutorialShownEntry createItem() {
		return new TutorialShownEntry();
	}
}
