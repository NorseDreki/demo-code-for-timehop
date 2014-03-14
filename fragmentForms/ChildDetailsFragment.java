package net.ccs.android.frags;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.ccs.android.R;
import net.ccs.android.adapters.NothingSelectedSpinnerAdapter;
import net.ccs.android.core.LOG;
import net.ccs.android.core.dao.BaseDao;
import net.ccs.android.core.dao.ChildCareDao;
import net.ccs.android.dialogs.BirthdayPickerDialog;
import net.ccs.android.model.Child;
import net.ccs.android.model.Classroom;
import net.ccs.android.model.EnumerationValue;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Fragment which displays information on the selected child.
 * 
 * @author "Alexey Dmitriev"
 *
 */
public class ChildDetailsFragment extends CreateOrEditFragment {

	/**
	 * Default value for the control which can display current value
	 * after user selects it from dialog / dropdown list
	 */
	private String DEFAULT_FIELD_VALUE;

	public static ChildDetailsFragment newInstance(long childId) {
		ChildDetailsFragment frag = null;

		if (canBeShown()) {
			frag = new ChildDetailsFragment();
			Bundle args = new Bundle();
			args.putLong("childId", childId);
			frag.setArguments(args);
		}
		return frag;
	}

	/** UI control for child's first name */
	private EditText firstNameField;
	
	/** UI control for child's last name */
	private EditText lastNameField;
	
	/* -- code stripped -- */

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.frag_child_details, container, false);

		firstNameField = (EditText) v.findViewById(R.id.frag_child_details_first_name);
		lastNameField = (EditText) v.findViewById(R.id.frag_child_details_last_name);
		
		/* -- code stripped -- */
		
		populateClassrooms();

		return v;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		// should be called after super.createOrEdit()
		populateProgramTypes();
	}

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Whether the child is older than twelve years.
	 * 
	 * @param birthDate
	 * @return
	 */
	private boolean isOlderThanTwelve(Calendar birthDate) {
		boolean result = false;

		Calendar now = Calendar.getInstance();
		if (now.get(Calendar.YEAR) -  birthDate.get(Calendar.YEAR) > 12)
			result = true;

		return result;
	}

	/**
	 * Creates date picker dialog. Presets date to birthDate if applicable.
	 */
	private void pickBirthDate() {
		int year = 0;
		int month = 0;
		int day = 0;

		if (birthDate != null) {

			Calendar cal = Calendar.getInstance();
			cal.setTime(birthDate);

			try {
				year = Integer.valueOf(cal.get(Calendar.YEAR));
				month = Integer.valueOf(cal.get(Calendar.MONTH));
				day = Integer.valueOf(cal.get(Calendar.DAY_OF_MONTH));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}

		BirthdayPickerDialog frag = BirthdayPickerDialog.newInstance(birthdayChosen,
				year, month, day);
		frag.show(getActivity().getSupportFragmentManager(), "datePicker");
	}

	/**
	 * Reads UI controls and fills current child record.
	 */
	private void readUiFields() {
		String firstName = firstNameField.getText().toString().trim();
		String lastName = lastNameField.getText().toString().trim();
		child.setFirstName(firstName);
		child.setLastName(lastName);
		child.setProgramType((EnumerationValue) programTypeField.getSelectedItem());
		child.setBirthDate(birthDate);
	}

	/**
	 * Fills a corresponding spinner with the list of program types.
	 * If activity is in edit mode, sets the selected item according to child's program type.
	 */
	private void populateProgramTypes() {
		List<EnumerationValue> programTypes = 
				ChildCareDao.INSTANCE.getApprovedProgramTypes();

		final ArrayAdapter<EnumerationValue> programTypesAdapter = 
				new ArrayAdapter<EnumerationValue>(
						getActivity(), 
						R.layout.spinner_program_type_vert,
						android.R.id.text1,
						programTypes);

		programTypesAdapter.setDropDownViewResource(
				android.R.layout.simple_spinner_dropdown_item);

		programTypeField.setAdapter(programTypesAdapter);

		if (child.getProgramType() != null)
			for (int i = 0; i < programTypes.size(); i++)
				if (child.getProgramType().getId()
						.compareTo(programTypes.get(i).getId()) == 0) {

					programTypeField.setSelection(i);
				}
	}

	/**
	 * Fills corresponding spinner with list of classrooms.
	 * When in edit mode, this spinner is disabled.
	 * 
	 */
	private void populateClassrooms() {
		List<Classroom> classrooms = ChildCareDao.INSTANCE.getClassrooms(null);

		final ArrayAdapter<Classroom> classroomAdapter = new ArrayAdapter<Classroom>(
				getActivity(), 
				R.layout.spinner_classroom_vert,
				android.R.id.text1,
				classrooms);

		classroomAdapter.setDropDownViewResource(
				android.R.layout.simple_spinner_dropdown_item);

		classroomField.setAdapter(
				new NothingSelectedSpinnerAdapter(
						classroomAdapter,
						R.layout.spinner_classroom_vert,
						getActivity()));
	}

	@Override
	protected boolean inEditMode() {
		childId = getArguments().getLong("childId", ChildCareDao.CHILD_NO_ID);
		return childId != ChildCareDao.CHILD_NO_ID;
	}

	@Override
	protected int getCreateTitleResId() {
		return R.string.child_details_title_new;
	}

	@Override
	protected int getEditTitleResId() {
		return R.string.child_details_title_edit;
	}

	@Override
	protected int getFormNotFilledMessageResId() {
		return R.string.child_details_empty_fields;
	}

	@Override
	protected int getDuplicateEntityMessageResId() {
		return R.string.child_details_save_failed_already_exists;
	}

	@Override
	protected int getEntityCreatedMessageResId() {
		return R.string.child_details_enroll_succeeded;
	}

	@Override
	protected int getEntityEditedMessageResId() {
		return R.string.child_details_edit_succeeded;
	}

	@Override
	protected void create() {
		child = new Child();
		dateField.setText(ChildCareDao.INSTANCE.dateUsingLocale(new Date())); 
	}

	@Override
	protected void edit() {
		
		/* -- code stripped -- */
		
	}

	@Override
	protected boolean isFormFilled() {
		return (!TextUtils.isEmpty(firstNameField.getText())
				&& !TextUtils.isEmpty(lastNameField.getText())
				&& birthDate != null);
	}

	@Override
	protected boolean isFormChanged() {
		String firstName = firstNameField.getText().toString().trim();
		String lastName = lastNameField.getText().toString().trim();

		boolean result = !firstName.equals(child.getFirstName())
				|| !lastName.equals(child.getLastName())
				|| !birthDate.equals(child.getBirthDate());

		return result;
	}

	@Override
	protected boolean entityAlreadyExists() {
		String firstName = firstNameField.getText().toString().trim();
		String lastName = lastNameField.getText().toString().trim();

		Child tmpChild = new Child();
		tmpChild.setFirstName(firstName);
		tmpChild.setLastName(lastName);
		tmpChild.setProgramType((EnumerationValue) programTypeField.getSelectedItem());
		tmpChild.setBirthDate(birthDate);

		boolean result = ChildCareDao.INSTANCE.doesChildExist(tmpChild) != null;

		return result;
	}

	@Override
	protected void saveNew() {
		
		/* -- code stripped -- */
		
		Classroom selectedClassroom = (Classroom) classroomField.getSelectedItem();
		ChildCareDao.INSTANCE.enrollNewChild(child, selectedClassroom);
	}

	@Override
	protected void saveEdited() {
		
		/* -- code stripped -- */

		BaseDao.INSTANCE.saveEntry(child);
	}

	@Override
	protected boolean doCustomChecks() {
		Classroom selectedClassroom = (Classroom) classroomField.getSelectedItem();

		/* -- code stripped -- */

		return true;
	}

	@Override
	protected void notifyAlreadyExists() {
		String message = 
				getActivity().getString(R.string.child_details_save_failed_already_exists);

		String state = "";
		if (child.isEnrolled()) {
			state = getActivity().getString(R.string.child_details_enrolled_child);
		} else if (child.isWithdrawn()) {
			state = getActivity().getString(R.string.child_details_withdrawn_child);
		}

		Toast.makeText(getActivity(), 
				String.format(message, state),
				Toast.LENGTH_LONG).show();	
	}
}
