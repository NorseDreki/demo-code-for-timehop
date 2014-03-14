package net.ccs.android.frags;

import net.ccs.android.R;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

/**
 * Fragment which can be in two modes: create new record; edit existing record.
 * Various checks are performed when user is about to save his changed:
 * - whether the form is filled;
 * - whether the form has changed;
 * - whether the record already exists;
 *  
 * @author "Alexey Dmitriev"
 *
 */
public abstract class CreateOrEditFragment extends SingleTopFragment {

	/** whether the form is in edit mode or a new entry is being created */
	private boolean inEditMode;

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		createOrEdit();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.ab_save_cancel, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.save:
			save();
			break;

		case R.id.cancel:
			dismiss();
			break;
		}

		return false;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		setHasOptionsMenu(true);
		setRetainInstance(true);
	}

	///////////////////////////////////////////////////////////////////////////

	/** whether the form is in edit mode */
	protected abstract boolean inEditMode();

	protected abstract int getCreateTitleResId();

	protected abstract int getEditTitleResId();

	protected abstract int getFormNotFilledMessageResId();

	protected abstract int getDuplicateEntityMessageResId();

	protected abstract int getEntityCreatedMessageResId();

	protected abstract int getEntityEditedMessageResId();

	/** hook which is called when entering 'create new' mode */
	protected abstract void create();

	/** hook which is called when entering edit mode */
	protected abstract void edit();

	/** whether all necessary form field are filled so it can be saved */
	protected abstract boolean isFormFilled();

	/** whether an entry which is being edited is changed by user from its original state */
	protected abstract boolean isFormChanged();

	/** hook which is called to check whether the entered entity already exists */
	protected abstract boolean entityAlreadyExists();

	/** does the job to create a new entity in DAO */
	protected abstract void saveNew();

	/** does the job to save edited entity to DAO */
	protected abstract void saveEdited();

	/** allows subclasses to define their own checks */
	protected abstract boolean doCustomChecks();

	/** called to notify user in UI that entered entity already exists */
	protected void notifyAlreadyExists() {
		showToast(getDuplicateEntityMessageResId());
	}

	/**
	 * Template method to enter either "create new" or "edit existing" mode
	 */
	private final void createOrEdit() {
		inEditMode = inEditMode();

		if (inEditMode) {
			title = getActivity().getString(getEditTitleResId());
			edit();
		} else {
			title = getActivity().getString(getCreateTitleResId());
			create();
		}
	}

	/**
	 * Template method to perform checks prior saving:
	 * - whether the form is filled;
	 * - whether the form has changed;
	 * - whether the record already exists;
	 */
	private final void save() {
		if (isFormFilled()) { 

			if (!doCustomChecks()) {
				return;
			}

			if (inEditMode) {
				// user could have edited the name of the entity so 
				// it would overlap with the existing one
				if (isFormChanged() && entityAlreadyExists()) {
					notifyAlreadyExists();
					return;
				}

				saveEdited();
				showToast(getEntityEditedMessageResId());

			} else {

				if (!entityAlreadyExists()) {
					saveNew();
					showToast(getEntityCreatedMessageResId());

				} else {
					notifyAlreadyExists();
					return;
				}
			}

			dismiss();

		} else {
			// not all required fields are filled up
			showToast(getFormNotFilledMessageResId());
		}
	}

	/** 
	 * Shows message to user 
	 * */
	protected void showToast(int messageResId) {
		Toast.makeText(getActivity(), 
				getActivity().getString(messageResId),
				Toast.LENGTH_LONG).show();
	}
}
