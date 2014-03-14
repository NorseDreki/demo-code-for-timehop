/**
 * 
 */
package net.ccs.android.kitchen.cells;

import net.ccs.android.model.kitchen.MenuMealItem;

/**
 * Meal component aware cell. Has a meal item in which chosen component resides.
 * 
 * @author "Alexey Dmitriev"
 *
 */
public abstract class MealItemCell extends BaseCell {
	
	/** actual container for the meal component */
	protected MenuMealItem mealItem;

	public MealItemCell(MenuMealItem mealItem, boolean inEdit) {
		super(inEdit);
		this.mealItem = mealItem;
	}

	public void setMealItem(MenuMealItem mealItem) {
		this.mealItem = mealItem;
	}
}
