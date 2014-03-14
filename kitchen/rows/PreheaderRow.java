package net.ccs.android.kitchen.rows;

import net.ccs.android.R;
import net.ccs.android.kitchen.cells.TextCell;
import android.content.Context;

/**
 * Defines a textual preheader row with some captions.
 * "Meal Components. Minimum quantity requirements per child."
 * 
 * @author "Alexey Dmitriev"
 *
 */
public class PreheaderRow extends BaseRow {
	
	private static final int FIRST_COLUMN_SPAN = 6;
	
	private static final int SECOND_COLUMN_SPAN = 4;
	
	private Context context;

	public PreheaderRow(Context context) {
		this.context = context;
		
		addCells();
	}

	@Override
	public void addCells() {
		cells.add(new TextCell(context.getString(R.string.table_header_meal_components), 
				FIRST_COLUMN_SPAN));
		
		cells.add(new TextCell(context.getString(R.string.table_header_quantity_requirements), 
				SECOND_COLUMN_SPAN));
	}
}
