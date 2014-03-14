package net.ccs.android.kitchen.rows;

import net.ccs.android.model.kitchen.MenuMealItem;

/**
 * Use this fatory to create instances of {@link MealComponentRow}. 
 * 
 * @author "Alexey Dmitriev"
 * 
 */
public class MealComponentRowFactory implements IRowFactory {
	
	@Override
	public IRow createRow(MenuMealItem mealItem, boolean primary, boolean inEdit) {
		return new MealComponentRow(mealItem, primary, inEdit);
	}
}
