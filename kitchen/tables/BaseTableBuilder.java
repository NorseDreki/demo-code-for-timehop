package net.ccs.android.kitchen.tables;

import java.util.ArrayList;
import java.util.List;

import android.widget.TableLayout;

import net.ccs.android.kitchen.rows.IRow;

/**
 * Base implementation of table builder. It knows UI container on
 * behalf of which this table is built. It keeps track of
 * rows under control.
 * 
 * @author "Alexey Dmitriev"
 * 
 */
public class BaseTableBuilder implements ILeaf {
	
	/**
	 * UI container
	 */
	protected TableLayout table;
	
	/**
	 * rows under control of this table builder
	 */
	protected List<IRow> rows;
	
	public BaseTableBuilder(TableLayout table) {
		this.table = table;
		
		rows = new ArrayList<IRow>();
	}
	
	/**
	 * Defines a way to add row to this table builder.
	 */
	protected void addRowToTable(IRow row) {
		rows.add(row);
		row.addToTable(table);
	}

	@Override
	public void enable(boolean enabled) {
		for (IRow row : rows)
			row.enable(enabled);
	}

	@Override
	public void invalidate() {
		for (IRow row : rows)
			row.invalidate();
	}

	@Override
	public void save() {
		for (IRow row : rows)
			row.save();
	}

	@Override
	public void destroy() {
		for (IRow row : rows)
			row.destroy();
	}

	@Override
	public void undo() {
		for (IRow row : rows)
			row.undo();
	}
}
