package net.ccs.android.kitchen.cells;

import net.ccs.android.kitchen.ILeaf;
import android.view.View;
import android.widget.TableRow;

/**
 * Interface encapsulating cell behaviour.
 * 
 * @author "Alexey Dmitriev"
 *
 */
public interface ICell extends ILeaf {

	/**
	 * When cell is in edit mode, return view that allows to edit its content.
	 * @param row to which edit view will be added
	 * 
	 * @return view to be added to row
	 */
	public View getEditView(TableRow row);
	
	/**
	 * When cell is in read-only mode, returns text to display.
	 * 
	 * @return text to be added to cell
	 */
	public String getDisplayedText();
	
	/**
	 * Adds cell to the row.
	 * 
	 * @param row pre-created row.
	 */
	public void addToRow(TableRow row);
	
}
