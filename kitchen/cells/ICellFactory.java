package net.ccs.android.kitchen.cells;

import android.os.Bundle;

/**
 * Interface for cell factories.
 * 
 * @author "Alexey Dmitriev"
 *
 */
public interface ICellFactory {
	
	/**
	 * Subclasses define which types of cells to create.
	 * 
	 * @param args arguments which can be passed to specific cell constructors
	 * @return created cell
	 */
	public ICell createCell(Bundle args);

}
