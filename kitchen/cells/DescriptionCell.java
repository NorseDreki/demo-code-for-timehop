package net.ccs.android.kitchen.cells;

import net.ccs.android.model.kitchen.MenuMealItem;
import android.widget.TableRow;

/**
 * This cell displays meal component's description.
 * 
 * @author "Alexey Dmitriev"
 *
 */
public final class DescriptionCell extends MealItemCell {
	
	public DescriptionCell(MenuMealItem mealItem) {
		super(mealItem, false);
	}
	
	@Override
	public String getDisplayedText() {
		String result = "";
		
		if (mealItem.getMealComponent() != null)
			result = mealItem.getMealComponent().getDescription();
		
		return result;
	}
}
