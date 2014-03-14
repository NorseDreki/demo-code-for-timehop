package net.ccs.android.kitchen.cells;

import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * Base implementation of {@link ICell} interface.
 * 
 * @author "Alexey Dmitriev"
 *
 */
public abstract class BaseCell implements ICell {

	/**
	 * layout params with which cell is added to row
	 */
	protected TableRow.LayoutParams rowParams;
	
	/**
	 * whether cell is in edit mode; edit mode means user can change cell's data
	 */
	protected boolean inEdit;
	
	/**
	 * view to display when cell is in edit mode
	 */
	private View editView;
	
	/**
	 * view to display when cell is in read-only mode
	 */
	protected TextView displayView;

	public BaseCell(boolean inEdit) {
		this.inEdit = inEdit;

		rowParams = new TableRow.LayoutParams(
				TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
	}
	
	@Override
	public String getDisplayedText() {
		return null;
	}
	
	@Override
	public void invalidate() {	
		if (!inEdit && displayView != null)
			displayView.setText(getDisplayedText());
	}

	@Override
	public void enable(boolean enabled) {
		if (editView != null)
			editView.setEnabled(enabled);
	}
	
	/**
	 * {@inheritDoc}
	 * Edit mode is not allowed by default.
	 * 
	 * @return null if edit mode is not allowed
	 */
	@Override
	public View getEditView(TableRow row) {
		return null;
	}

	protected void addTextView(TableRow row, String text) {
		addTextView(row, text, null);
	}

	/**
	 * Creates and adds text view to the row.
	 * 
	 * @param row
	 * @param text
	 * @param colspan
	 */
	protected void addTextView(TableRow row, String text, Integer colspan) {
		if (colspan != null)
			rowParams.span = colspan;

		// no way to set style (no function)

		displayView = new TextView(row.getContext());
		styleDisplayView();
		displayView.setLayoutParams(rowParams);
		displayView.setText(text);

		row.addView(displayView);
	}
	
	@Override
	public void addToRow(TableRow row) {
		editView = getEditView(row);
		if (inEdit && editView != null) {
			editView.setLayoutParams(rowParams);
			row.addView(editView);
		} else {
			addTextView(row, getDisplayedText());
		}
	}
	
	@Override
	public void destroy() {
		// base implementation does nothing
	}

	@Override
	public void undo() {
		// base implementation does nothing		
	}

	@Override
	public void save() {
		// base implementation does nothing
	}
}
