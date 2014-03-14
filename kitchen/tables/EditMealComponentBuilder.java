package net.ccs.android.kitchen.tables;

import java.util.ArrayList;
import java.util.List;

import net.ccs.android.events.EnteredMealComponentsEvent;
import net.ccs.android.events.Events;
import net.ccs.android.events.PickMealComponentEvent;
import net.ccs.android.kitchen.rows.edit.EditMealComponentRowFactory;
import net.ccs.android.kitchen.rows.edit.EditMealHeaderRow;
import net.ccs.android.model.EnumerationValue;
import net.ccs.android.model.kitchen.MenuMealItem;
import net.ccs.android.model.kitchen.MenuTemplateMealItem;
import android.widget.TableLayout;

import com.squareup.otto.Subscribe;

/**
 * Builds the pop-up dialog in which user enters meal components.
 * 
 * @author "Alexey Dmitriev"
 *
 */
public class EditMealComponentBuilder extends MealItemTableBuilder {

	/** meal items are filtered based on this template */
	private MenuTemplateMealItem mtmi;
	
	/** list container for all template meal items */
	private List<MenuTemplateMealItem> templateMealItems;
	
	/** existing components loaded from disk */
	private List<MenuMealItem> loadedComponents;

	public EditMealComponentBuilder(TableLayout table, long ageGroupMask,
			EnumerationValue mealType, MenuTemplateMealItem mtmi) {
		super(table, ageGroupMask, mealType, true);
		this.mtmi = mtmi;

		templateMealItems = new ArrayList<MenuTemplateMealItem>();
		templateMealItems.add(mtmi);
		Events.INSTANCE.get().register(this);
	}

	@Override
	public void destroy() {
		Events.INSTANCE.get().unregister(this);
		
		super.destroy();
	}

	/**
	 * Receives meal components entered in meal service table.
	 * 
	 * @param e event containing meal components
	 */
	@Subscribe
	public void onEvent(EnteredMealComponentsEvent e) {
		loadedComponents = e.getComponents();
	}

	@Override
	public void invalidate() {
		if (loadedComponents != null) {
			table.removeAllViews();
			table.setStretchAllColumns(true);

			addRowToTable(new EditMealHeaderRow(table.getContext(), 
					ageGroupMask, mealType.getBitKey()));

			addMealComponentMetaRows(templateMealItems, loadedComponents, 
					new EditMealComponentRowFactory());

		} else {
			throw new Error("Loaded components must not be null!");
		}
	}

	/**
	 * When saving, send {@link PickMealComponentEvent} for each entered component.
	 */
	@Override
	public void save() {
		List<MenuMealItem> components = findMealComponents(loadedComponents, mtmi);

		for (int i = 0; i < components.size(); i++) {
			MenuMealItem mealItem = components.get(i);

			PickMealComponentEvent e = new PickMealComponentEvent(mealItem.hashCode(), 
					mealItem.getMealComponent(), true);

			Events.INSTANCE.get().post(e);
		}
	}
}
