package net.ccs.android.kitchen.cells;

import net.ccs.android.model.kitchen.MenuMealItem;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.TableRow;

/**
 * This cells displays total amount of food prepared for the given meal item.
 * 
 * @author "Alexey Dmitriev"
 */
public class TotalPreparedQtyCell extends MealItemCell {

	/** min width of this column */
	private static final int MIN_WIDTH = 56;

	/** view for the edit mode */
	private EditText edit;

	/** key handler for the edit view */
	private OnKeyListener keyListener;

	/** actual prepared quantity */
	private Double actualQty;

	public TotalPreparedQtyCell(MenuMealItem mealItem, boolean inEdit) {
		super(mealItem, inEdit);
	}

	@Override
	public String getDisplayedText() {
		return String.valueOf(getActualQty());
	}

	/**
	 * Returns current total prepared quantity.
	 */
	private double getActualQty() {
		return mealItem.getActualMealQty() == null ? 0.0 : mealItem.getActualMealQty();
	}

	@Override
	public void enable(boolean enabled) {
		edit.setEnabled(enabled);
	}

	@Override
	public View getEditView(TableRow row) {
		keyListener = new QtyKeyListener();

		edit = new EditText(row.getContext());
		edit.setLayoutParams(rowParams);
		edit.setText(String.valueOf(getActualQty()));
		edit.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
		edit.setOnKeyListener(keyListener);
		edit.setGravity(Gravity.RIGHT);
		edit.setSelectAllOnFocus(true);

		return edit;
	}

	/** Key event handler for the edit view */
	private class QtyKeyListener implements View.OnKeyListener	{
		@Override
		public boolean onKey(View v, int keyCode, KeyEvent event) {
			try {
				actualQty = Double.valueOf(edit.getText().toString());
			} catch (NumberFormatException e) {
				actualQty = 0.0;
			}
			mealItem.setActualMealQty(actualQty);

			return false;
		}
	}

	@Override
	protected void styleDisplayView() {
		super.styleDisplayView();

		rowParams = new TableRow.LayoutParams(MIN_WIDTH, TableRow.LayoutParams.WRAP_CONTENT);
		displayView.setEllipsize(TextUtils.TruncateAt.END);
		displayView.setGravity(Gravity.RIGHT);
	}
}
