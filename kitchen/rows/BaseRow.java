package net.ccs.android.kitchen.rows;

import java.util.ArrayList;
import java.util.List;

import net.ccs.android.kitchen.cells.ICell;
import android.widget.TableLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TableRow;

/**
 * Base implementation of IRow interface.
 * 
 * @author "Alexey Dmitriev"
 *
 */
public abstract class BaseRow implements IRow {

	/**
	 * list of cells contained in this row
	 */
	protected List<ICell> cells;

	/**
	 * whether this row is in edit mode
	 */
	protected boolean inEdit;

	/**
	 * table row
	 */
	protected TableRow tableRow;

	/**
	 * 
	 */
	public BaseRow() {
		cells = new ArrayList<ICell>();
		inEdit = false;
	}

	/**
	 * IMPORTANT: one must implement and then CALL this function explicitly
	 * otherwise cells will not be added to a table.
	 * 
	 */
	@Override
	public abstract void addCells();

	/**
	 * Defines basic layout params, creates row and adds cells to it.
	 */
	@Override
	public void addToTable(TableLayout table) {
		LayoutParams tableParams = new TableLayout.LayoutParams(
				TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);

		tableRow = new TableRow(table.getContext());
		tableRow.setLayoutParams(tableParams);

		for (ICell c : cells)
			c.addToRow(tableRow);

		table.addView(tableRow);
	}

	@Override
	public void invalidate() {  
		for (ICell c : cells)
			c.invalidate();
	}

	@Override
	public void enable(boolean enabled) {
		for (ICell c : cells)
			c.enable(enabled);
	}

	@Override
	public void save() {
		for (ICell c : cells)
			c.save();
	}

	@Override
	public void undo() {
		for (ICell c : cells)
			c.undo();
	}

	@Override
	public void destroy() {
		for (ICell c : cells)
			c.destroy();
	}
}
