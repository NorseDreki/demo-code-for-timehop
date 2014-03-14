package net.ccs.android.kitchen.rows;

import java.util.List;

import net.ccs.android.R;
import net.ccs.android.core.dao.ChildCareDao;
import net.ccs.android.events.BroadcastLastServiceTotalEvent;
import net.ccs.android.events.Events;
import net.ccs.android.events.ProgramTypeChangedEvent;
import net.ccs.android.kitchen.cells.AgeGroupMetaCell;
import net.ccs.android.kitchen.cells.LastServiceTotalCell;
import net.ccs.android.kitchen.cells.TextCell;
import net.ccs.android.model.AttendanceCount;
import net.ccs.android.model.EnumerationValue;
import net.ccs.android.model.Provider;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TableLayout;

import com.squareup.otto.Subscribe;

/**
 * This row contains cells, and each of those cells display last service total
 * number for the corresponding age group.
 * 
 * Age groups are "unwinded" from the mask by {@link AgeGroupMetaCell}
 * 
 * @author "Alexey Dmitriev"
 * 
 */
public class LastServiceTotalRow extends BaseRow {

	/** context on behalf of which this row is added */
	protected Context context;
	
	/** applicable age group */
	private long ageGroupMask;
	
	/** applicable meal */
	private long mealMask;

	public LastServiceTotalRow(Context context, long mealMask) {
		this.context = context;
		this.mealMask = mealMask;
		this.inEdit = false;

		Provider provider = ChildCareDao.INSTANCE.getCurrentProvider();
		ageGroupMask = provider.getActualHistory().getAgeGroups();

		addCells();
	}
	
	/** Catches global program type change */
	@Subscribe
	public void onEvent(ProgramTypeChangedEvent e) {
		broadcastLastServiceTotals(e.getProgramType());
	}
	
	/** After calculation, notifies subscribers about last service totals */
	private void broadcastLastServiceTotals(final EnumerationValue programType) {
		new AsyncTask<Void, Void, List<AttendanceCount>>() {
			
			@Override
			protected List<AttendanceCount> doInBackground(Void... params) {
				List<AttendanceCount> lastServiceTotals = 
						ChildCareDao.INSTANCE.getLastServiceTotals(programType);
				
				return lastServiceTotals;
			}

			@Override
			protected void onPostExecute(List<AttendanceCount> result) {
				Events.INSTANCE.get().post(
						new BroadcastLastServiceTotalEvent(result));
			}
			
		}.execute();
	}

	@Override
	public void addCells() {
		cells.add(new TextCell(context.getString(
				R.string.table_header_last_service_totals)));
		
		AgeGroupMetaCell metaCell = new AgeGroupMetaCell(ageGroupMask, mealMask,
				new LastServiceTotalCell.Factory(mealMask));

		cells.add(metaCell);
	}
}
