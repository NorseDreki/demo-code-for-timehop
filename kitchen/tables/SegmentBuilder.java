package net.ccs.android.kitchen.tables;

import net.ccs.android.model.EnumerationValue;
import net.ccs.android.model.kitchen.MenuTemplateMealItem;
import android.widget.TableLayout;

/**
 * Decorates edit mode table builders. Adds behaviour to be used
 * when building pop-up dialogs in which user edits table values.
 * 
 * @author "Alexey Dmitriev"
 *
 */
public enum SegmentBuilder implements ILeaf {
	HEAD_COUNT("Edit preplanned totals"), MEAL_ITEM("Edit meal components for ");
	
	/** title of pop-up dialog */
	private String title;
	
	/** template meal item which is used for filtering */
	private MenuTemplateMealItem templateMealItem;
	
	/** actual table builder which does the job */
	private ILeaf delegate;

	private SegmentBuilder(String title) {
		this.title = title;
	}
	
	@Override
	public String toString() {
		String tmp = title;
		
		if (templateMealItem != null)
			tmp += templateMealItem.getDescription();
		
		return tmp;
	}
	
	/**
	 * Builds table according to the selected mode.
	 */
	public void build(TableLayout table, long ageGroupMask, 
			EnumerationValue mealType, MenuTemplateMealItem templateMealItem) {
		
		this.templateMealItem = templateMealItem;
		
		if (this.name().equals(HEAD_COUNT.name())) {
			delegate = new EditPreplannedTotalBuilder(table, ageGroupMask, mealType);
			
		} else if (this.name().equals(MEAL_ITEM.name())) {
			delegate = new EditMealComponentBuilder(table, ageGroupMask, 
					mealType, templateMealItem);
		}
		
		invalidate();
	}
	
	@Override
	public void invalidate() {
		delegate.invalidate();
	}

	@Override
	public void save() {
		delegate.save();
	}

	@Override
	public void destroy() {
		delegate.destroy();
	}

	@Override
	public void undo() {
		delegate.undo();
	}

	@Override
	public void enable(boolean enabled) {
		delegate.enable(enabled);
	}
}
