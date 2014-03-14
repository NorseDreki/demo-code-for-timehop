package net.ccs.android.kitchen.rows;

import java.util.ArrayList;
import java.util.List;

import net.ccs.android.core.dao.KitchenDao;
import net.ccs.android.events.Events;
import net.ccs.android.events.PickMealComponentEvent;
import net.ccs.android.kitchen.cells.AgeServingCell;
import net.ccs.android.kitchen.cells.CodeCell;
import net.ccs.android.kitchen.cells.DescriptionCell;
import net.ccs.android.kitchen.cells.EstimatedQtyCell;
import net.ccs.android.kitchen.cells.FoodGroupCell;
import net.ccs.android.kitchen.cells.MealItemMetaCell;
import net.ccs.android.kitchen.cells.RatioCell;
import net.ccs.android.kitchen.cells.TextCell;
import net.ccs.android.kitchen.cells.TotalPreparedQtyCell;
import net.ccs.android.kitchen.cells.TotalPreparedUnitCell;
import net.ccs.android.kitchen.cells.UnitCell;
import net.ccs.android.model.kitchen.MenuMealItem;
import net.ccs.android.model.kitchen.MenuTemplateMeal;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TableRow;

import com.squareup.otto.Subscribe;

/**
 * Defines a row which is associated with a meal item. Meal items can contain several
 * meal components.
 * 
 * @author "Alexey Dmitriev"
 *
 */
public class MealComponentRow extends BaseMealComponentRow {

	private static final int NUM_CELLS_IN_FIRST_LINE = 8;

	private static final int NUM_CELLS_IN_SECOND_LINE = 3;


	public MealComponentRow(MenuMealItem mealItem, boolean primary,
			boolean inEdit) {
		super(mealItem, primary, inEdit);
	}

	/**
	 * Actual row consists of two lines; second line is for description only.
	 * This function adds only cells for the first line.
	 */
	@Override
	public void addCells() {
		
		/* -- code stripped */
		
		cells.add(new CodeCell(mealItem, ageGroupMask, mealMask, inEdit));
		
		cells.add(new RatioCell(mealItem, inEdit, primary));
		
		cells.add(new EstimatedQtyCell(mealItem, ageGroupMask, mealMask));
		
		cells.add(new TotalPreparedQtyCell(mealItem, false));
		
		cells.add(new TotalPreparedUnitCell(mealItem));
		
		cells.add(new MealItemMetaCell(ageGroupMask, mealMask, 
				new AgeServingCell.Factory(mealItem)));
		
		cells.add(new UnitCell(mealItem));
	}

	/**
	 * Adds two lines (which are tableRows) instead on just one.
	 */
	@Override
	public void addToTable(TableLayout table) {
		Double ratio = mealItem.getMealRatio();
		boolean rowShown = ratio != 0.0;

		LayoutParams tableParams = new TableLayout.LayoutParams(
				TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);

		/* -- code stripped */
		
		descriptionRow = new TableRow(table.getContext());
		descriptionRow.setLayoutParams(tableParams);
		descriptionRow.setVisibility(rowShown ? View.VISIBLE : View.GONE);
		
		/* -- code stripped */

		table.addView(descriptionRow);
		registerRowClicks(table, descriptionRow);
	}

	@Override
	public void invalidate() {
		super.invalidate();

		Double ratio = mealItem.getMealRatio();
		boolean rowShown = ratio != 0.0;
		tableRow.setVisibility(rowShown ? View.VISIBLE : View.GONE);
		descriptionRow.setVisibility(rowShown ? View.VISIBLE : View.GONE);
	}

	/**
	 * Called when user picks a meal component from the pop-up dialog.
	 * 
	 * @param e
	 */
	@Subscribe
	public void onEvent(PickMealComponentEvent e) {
		processPickMealComponent(e);
	}
}
