package net.ccs.android.kitchen.cells;

import net.ccs.android.model.kitchen.MenuMealItem;
import android.view.View;
import android.widget.TableRow;

/**
 * Adapter for {@link AgeGroupMetaCell} to support {@link MealItemCell}.
 * 
 * @author "Alexey Dmitriev"
 *
 */
public class MealItemMetaCell extends MealItemCell {
	
	/** does the actual job */
	private AgeGroupMetaCell delegate;

	public MealItemMetaCell(long ageGroupMask, long mealMask, ICellFactory cellFactory) {
		super(null, false);
		
		delegate = new AgeGroupMetaCell(ageGroupMask, mealMask, cellFactory);
	}
	                
	@Override
	public void setMealItem(MenuMealItem mealItem) {
		for (ICell c : delegate.getCells())
			((MealItemCell) c).setMealItem(mealItem);
	}

	@Override
	public void addToRow(TableRow row) {
		delegate.addToRow(row);
	}

	@Override
	public void invalidate() {
		delegate.invalidate();
	}

	@Override
	public void enable(boolean enabled) {
		delegate.enable(enabled);
	}

	@Override
	public void destroy() {
		delegate.destroy();
	}

	@Override
	public void undo() {
		delegate.undo();
	}

	@Override
	public void save() {
		delegate.save();
	}

	@Override
	public String getDisplayedText() {
		return delegate.getDisplayedText();
	}

	@Override
	public View getEditView(TableRow row) {
		return delegate.getEditView(row);
	}
}
