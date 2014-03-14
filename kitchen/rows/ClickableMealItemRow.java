package net.ccs.android.kitchen.rows;

import net.ccs.android.R;
import net.ccs.android.dialogs.EditTableDialog;
import net.ccs.android.kitchen.tables.SegmentBuilder;
import net.ccs.android.model.kitchen.MenuTemplateMealItem;
import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;

/**
 * Row which supports clicks and long clicks.
 * This row is meal item aware (knows its group of components).
 * 
 * @author "Alexey Dmitriev"
 *
 */
public abstract class ClickableMealItemRow extends BaseRow {

	/**
	 * compound age group mask which can consist of several age groups
	 */
	protected Long ageGroupMask;

	/**
	 * meals to which projected calculations are applied
	 */
	protected Long mealMask;

	/**
	 * template group of meal components
	 */
	protected MenuTemplateMealItem templateMealItem;

	/**
	 * which table to build in popup dialog
	 */
	private SegmentBuilder segmentBuilder;

	public ClickableMealItemRow(SegmentBuilder segmentBuilder) {
		this.segmentBuilder = segmentBuilder;
	}

	@Override
	public void addToTable(TableLayout table) {
		super.addToTable(table);

		registerRowClicks(table, tableRow);
	}

	protected void registerRowClicks(TableLayout table, TableRow row) {
		final Context context = table.getContext();

		// no clicks in edit mode
		if (!inEdit) {
			row.setClickable(true);
			row.setBackgroundResource(R.drawable.list_item_pressed_selector);

			row.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					DialogFragment frag = EditTableDialog.newInstance(context, 
							segmentBuilder, ageGroupMask, mealMask, templateMealItem);

					SherlockFragmentActivity sfa = (SherlockFragmentActivity) context;
					frag.show(sfa.getSupportFragmentManager(), "dialog");
				}
			});

			row.setOnLongClickListener(new View.OnLongClickListener() {
				@Override
				public boolean onLongClick(View arg0) {
					String text = getTextForLongClick();
					
					if (text != null) {
						Toast.makeText(context, text, Toast.LENGTH_LONG).show();
						return true;
					} else {
						return false;
					}
				}
			});
		}
	}

	/**
	 * Subclasses override to provide text for long clicks.
	 * 
	 * @return null if no action needed for long click
	 */
	protected String getTextForLongClick() {
		return null;
	}
}
