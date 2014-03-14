package net.ccs.android.kitchen.rows;

import net.ccs.android.R;
import net.ccs.android.kitchen.cells.AgeCaptionCell;
import net.ccs.android.kitchen.cells.AgeGroupMetaCell;
import net.ccs.android.kitchen.cells.TextCell;
import android.content.Context;

/**
 * Row with text captions for meal part of the table.
 * 
 * @author "Alexey Dmitriev"
 *
 */
public class HeaderRow extends BaseRow {
	
	/** over how many columns does total prepared cell spans */
	protected static final int TOTAL_PREPARED_SPAN = 2;
	
	/** context on behalf of which this row is added */
	protected Context context;
	
	/** applicable age group */
	private long ageGroupMask;
	
	/** applicable meal */
	private long mealMask;

	public HeaderRow(Context context, long ageGroupMask, long mealMask) {
		this.context = context;
		this.ageGroupMask = ageGroupMask;
		this.mealMask = mealMask;
		
		addCells();
	}

	@Override
	public void addCells() {
		cells.add(new TextCell(context.getString(R.string.table_header_food_group)));
		
		cells.add(new TextCell(context.getString(R.string.table_header_code)));
		
		cells.add(new TextCell(context.getString(R.string.table_header_ratio)));
		
		cells.add(new TextCell(context.getString(R.string.table_header_estimated_qty)));
		
		cells.add(new TextCell(context.getString(R.string.table_header_total_prepared), 
				TOTAL_PREPARED_SPAN));
		
		cells.add(new AgeGroupMetaCell(ageGroupMask, mealMask, new AgeCaptionCell.Factory()));
		
		cells.add(new TextCell(context.getString(R.string.table_header_unit)));
	}
}
