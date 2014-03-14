package net.ccs.android.kitchen.tables;

import java.util.Date;
import java.util.List;

import net.ccs.android.core.dao.KitchenDao;
import net.ccs.android.events.EnteredMealComponentsEvent;
import net.ccs.android.events.Events;
import net.ccs.android.events.UnregisterSubscriberEvent;
import net.ccs.android.kitchen.rows.PreplannedTotalRow;
import net.ccs.android.kitchen.rows.MealComponentRowFactory;
import net.ccs.android.kitchen.rows.HeaderRow;
import net.ccs.android.kitchen.rows.PreheaderRow;
import net.ccs.android.model.EnumerationValue;
import net.ccs.android.model.kitchen.MenuMealItem;
import net.ccs.android.model.kitchen.MenuTemplateMealItem;
import android.widget.TableLayout;

import com.squareup.otto.Produce;
import com.squareup.otto.Subscribe;

/**
 * Populates main Meal Service table and brings it on screen.
 * 
 * @author "Alexey Dmitriev"
 *
 */
public class KitchenTableBuilder extends MealItemTableBuilder {

	public KitchenTableBuilder(TableLayout table, long ageGroupMask, EnumerationValue mealType,
			boolean inEdit) {

		super(table, ageGroupMask, mealType, inEdit);
	}

	@Override
	public void save() {
		super.save();
		
		KitchenDao.INSTANCE.saveMealMenu(templateMeal, enteredComponents);
	}

	@Override
	public void invalidate() {
		table.removeAllViews();

		List<MenuMealItem> components =
				KitchenDao.INSTANCE.loadMenuMealItems(new Date(), templateMeal);

		List<MenuTemplateMealItem> templateMealItems = 
				KitchenDao.INSTANCE.getMenuTemplateMealItems(ageGroupMask, mealType);

		addRowToTable(new PreplannedTotalRow(table.getContext(), ageGroupMask, 
				mealType.getBitKey(), inEdit));
		
		addRowToTable(new PreheaderRow(table.getContext()));
		
		addRowToTable(new HeaderRow(table.getContext(), ageGroupMask, 
				mealType.getBitKey()));
		
		addMealComponentMetaRows(templateMealItems, components, new MealComponentRowFactory());

		Events.INSTANCE.get().register(this);
	}

	/**
	 * Broadcasts all meal components which are present in table.
	 * 
	 * @return broadcasted event
	 */
	@Produce
	public EnteredMealComponentsEvent produceEnteredComponents() {
		return new EnteredMealComponentsEvent(enteredComponents);
	}

	@Override
	public void destroy() {
		Events.INSTANCE.get().unregister(this);
		
		super.destroy();
	}
}
