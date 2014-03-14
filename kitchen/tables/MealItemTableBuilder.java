package net.ccs.android.kitchen.tables;

import java.util.ArrayList;
import java.util.List;

import net.ccs.android.core.dao.KitchenDao;
import net.ccs.android.kitchen.rows.IRow;
import net.ccs.android.kitchen.rows.IRowFactory;
import net.ccs.android.kitchen.rows.MealComponentMetaRow;
import net.ccs.android.model.EnumerationValue;
import net.ccs.android.model.kitchen.MenuMealItem;
import net.ccs.android.model.kitchen.MenuTemplateMeal;
import net.ccs.android.model.kitchen.MenuTemplateMealItem;
import android.widget.TableLayout;

/**
 * Builds table which can host meal items. Takes in account currently
 * selected age group and meal type.
 * 
 * @author "Alexey Dmitriev"
 *
 */
public abstract class MealItemTableBuilder extends BaseTableBuilder {

	/**
	 * age group to which current table applies
	 */
	protected long ageGroupMask;

	/**
	 * meal type to which current table applies
	 */
	protected EnumerationValue mealType;

	/**
	 * list of empty meal items, one or two (depends on componentCount)
	 * per template meal item;
	 * 
	 * if user filled a particular meal item, meal component will be attached
	 * so will not be empty anymore
	 */
	protected List<MenuMealItem> enteredComponents;

	/**
	 * meal for which this table is built
	 */
	protected MenuTemplateMeal templateMeal;

	/**
	 * whether is in edit mode - so cells can be edited by user
	 */
	protected boolean inEdit;

	public MealItemTableBuilder(TableLayout table, long ageGroupMask, EnumerationValue mealType,
			boolean inEdit) {
		
		super(table);

		this.inEdit = inEdit;
		this.ageGroupMask = ageGroupMask;
		this.mealType = mealType;

		templateMeal = 
				KitchenDao.INSTANCE.getMenuTemplateMeal(ageGroupMask, mealType);

		enteredComponents = new ArrayList<MenuMealItem>();
	}

	/**
	 * Given the list of meal item templates for the current meal and the list of
	 * meal items to init these templates (if any), add as many meal component meta
	 * rows as the number of templates. Each meta row adds one or two actual
	 * meal row as defined by {@link MenuTemplateMealItem#getComponentCount()}.
	 * All rows are added to UI.
	 * 
	 * @param templateMealItems list of templates from which rows are built
	 * @param components list of meal items to populate rows if needed
	 * @param rowFactory factory which is used to create rows in a meta row
	 * 
	 * NOTE componentCount is hardcoded to 2, in theory there could be more
	 * components but in real life this should never happen
	 * 
	 */
	protected List<IRow> addMealComponentMetaRows(List<MenuTemplateMealItem> templateMealItems,
			List<MenuMealItem> components, IRowFactory rowFactory) {

		List<IRow> result = new ArrayList<IRow>();

		/* -- code stripped -- */
		 		
		return result;
	}

	/* -- code stripped -- */
	
}
