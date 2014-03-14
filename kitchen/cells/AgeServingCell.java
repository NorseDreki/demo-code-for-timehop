package net.ccs.android.kitchen.cells;

import java.util.Locale;

import net.ccs.android.core.dao.KitchenDao;
import net.ccs.android.model.kitchen.MenuMealItem;
import android.os.Bundle;
import android.view.Gravity;

/**
 * Cell which displays serving size of the meal component, for
 * the specified age group.
 * 
 * @author "Alexey Dmitriev"
 *
 */
public class AgeServingCell extends MealItemCell {

	/** 
	 * Use this factory to create {@link AgeServingCell}
	 * Expected arguments:
	 * - "bit_key", long
	 * - "meal_mask", long
	 * 
	 */
	public static class Factory implements ICellFactory {

		private MenuMealItem mealItem;

		public Factory(MenuMealItem mealItem) {
			this.mealItem = mealItem;
		}

		@Override
		public ICell createCell(Bundle args) {
			long ageGroupMask = args.getLong("bit_key");
			long mealMask = args.getLong("meal_mask");

			return new AgeServingCell(mealItem, ageGroupMask, mealMask);
		}
	}

	///////////////////////////////////////////////////////////////////////////

	/**
	 * mask which represents single age group for which serving is retrieved
	 */
	private long ageGroupMask;
	
	/**
	 * default serving size, for the whole portion
	 */
	private double defaultSize;

	/**
	 * meal to which this serving is calculated
	 */
	private long mealMask;

	public AgeServingCell(MenuMealItem mealItem, long ageGroupMask, long mealMask) {
		super(mealItem, false);
		this.ageGroupMask = ageGroupMask;
		this.mealMask = mealMask;
	}
	
	@Override
	public void invalidate() {
		defaultSize = KitchenDao.INSTANCE.getServingSize(mealItem, ageGroupMask, 
				mealMask);
		
		super.invalidate();
	}

	@Override
	public String getDisplayedText() {
		double finalSize = defaultSize * mealItem.getMealRatio();
		
		return String.format(Locale.US, "%.2f", finalSize);
	}
	
	@Override
	protected void styleDisplayView() {
		super.styleDisplayView();
		
		displayView.setGravity(Gravity.RIGHT);
	}
}
