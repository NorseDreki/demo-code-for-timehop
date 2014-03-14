package net.ccs.android.kitchen.rows;

import net.ccs.android.kitchen.ILeaf;
import android.widget.TableLayout;

/**
 * Defines a row interface. Meal Service tables consist of rows.
 * 
 * @author "Alexey Dmitriev"
 *
 */
public interface IRow extends ILeaf {
	
	/**
	 * Implement to add cells to row.
	 */
	public void addCells();
	
	/**
	 * Implement to add row to a Meal Service table
	 * 
	 * @param table target table to add row to
	 */
	public void addToTable(TableLayout table);

}
