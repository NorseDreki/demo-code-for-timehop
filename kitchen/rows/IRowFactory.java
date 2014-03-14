package net.ccs.android.kitchen.rows;

import net.ccs.android.model.kitchen.MenuMealItem;

/**
 * This interface is for factories which can create rows.
 */
public interface IRowFactory {
	
	/** */
	public IRow createRow(MenuMealItem mealItem, boolean primary, boolean inEdit);

}
