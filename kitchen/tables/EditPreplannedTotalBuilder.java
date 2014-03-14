package net.ccs.android.kitchen.tables;

import net.ccs.android.kitchen.rows.CopyLastServiceRow;
import net.ccs.android.kitchen.rows.LastServiceTotalRow;
import net.ccs.android.kitchen.rows.edit.EditPreplannedTotalRow;
import net.ccs.android.kitchen.rows.edit.EditTitlePreplannedTotalRow;
import net.ccs.android.model.EnumerationValue;
import android.widget.TableLayout;

/**
 * Builds table to edit preplanned totals in corresponding pop-up dialog.
 * 
 * @author "Alexey Dmitriev"
 *
 */
public class EditPreplannedTotalBuilder extends MealItemTableBuilder {
	
	public EditPreplannedTotalBuilder(TableLayout table, long ageGroupMask, 
			EnumerationValue mealType) {
		
		super(table, ageGroupMask, mealType, true);
	}

	@Override
	public void invalidate() {
		table.removeAllViews();

		addRowToTable(new EditTitlePreplannedTotalRow(table.getContext(), 
				ageGroupMask, mealType.getBitKey()));
		
		addRowToTable(new LastServiceTotalRow(table.getContext(), 
				mealType.getBitKey()));
		
		addRowToTable(new EditPreplannedTotalRow(table.getContext(), 
				ageGroupMask, mealType.getBitKey()));
		
		addRowToTable(new CopyLastServiceRow());
	}
}
