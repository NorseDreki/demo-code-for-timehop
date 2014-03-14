package net.ccs.android.kitchen.cells;

import net.ccs.android.activities.PickMealComponentActivity;
import net.ccs.android.model.kitchen.MenuMealItem;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TableRow;

/**
 * Cell which displays meal component's entry code.
 * 
 * @author "Alexey Dmitriev"
 *
 */
public final class CodeCell extends MealItemCell {
	
	/** view for the edit mode */
	private EditText edit;
	
	/** applicable age group */
	private long ageGroupMask;
	
	/** applicable meal mask */
	private long mealMask;

	public CodeCell(MenuMealItem mealItem, long ageGroupMask, long mealMask, 
			boolean inEdit) {
		super(mealItem, inEdit);
		this.ageGroupMask = ageGroupMask;
		this.mealMask = mealMask;
	}

	@Override
	public String getDisplayedText() {
		String result = "";
		
		if (mealItem.getMealComponent() != null)
			result = mealItem.getMealComponent().getEntryCode();
		
		return result;
	}
	
	@Override
	public View getEditView(final TableRow row) {
		edit = new EditText(row.getContext());
		edit.setText(getDisplayedText());
		edit.setInputType(InputType.TYPE_CLASS_NUMBER);
		edit.setKeyListener(null);

		edit.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					
					long idFoodGroup = 
							mealItem
							.getMealTemplateItem()
							.getFoodGroup()
							.getId();
					
					// launches a dialog to choose a meal component
					PickMealComponentActivity.launch(
							row.getContext(), 
							mealItem.hashCode(), 
							idFoodGroup, 
							mealMask, 
							ageGroupMask);
					break;

				default:
					break;
				}
				
				return true;
			}
		});

		return edit;
	}

	@Override
	public void invalidate() {
		super.invalidate();
		edit.setText(getDisplayedText());
	}
}
