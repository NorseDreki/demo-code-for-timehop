package net.ccs.android.frags;

import java.util.Date;

import net.ccs.android.R;
import net.ccs.android.core.dao.BaseDao;
import net.ccs.android.core.dao.ChildCareDao;
import net.ccs.android.events.AcquireActionBarEvent;
import net.ccs.android.events.Events;
import net.ccs.android.model.Classroom;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * Fragment which displays information on the selected classroom.
 * 
 * @author "Alexey Dmitriev"
 *
 */
public class ClassroomDetailsFragment extends CreateOrEditFragment {

	/** maximum children allowed in classroom */
	private static final int MAX_CHILDREN = 100;

	/**
	 * Constructs new instance of this fragment.
	 */
	public static ClassroomDetailsFragment newInstance(long classroomId) {
		ClassroomDetailsFragment frag = null;

		if (canBeShown()) {
			frag = new ClassroomDetailsFragment();
			Bundle args = new Bundle();
			args.putLong("classroomId", classroomId);
			frag.setArguments(args);
		}
		return frag;
	}

	///////////////////////////////////////////////////////////////////////////

	/** classroom name field **/
	private EditText nameField;

	/** onCreate parameter based on which Classroom is loaded **/
	private long classroomId;
	
	/* -- code stripped -- */

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.frag_classroom_details, container, false);

		nameField = (EditText) v.findViewById(R.id.frag_classroom_details_name);
		maxChildrenField = (EditText) v.findViewById(R.id.frag_classroom_details_max_children);

		/* -- code stripped -- */

		return v;
	}

	///////////////////////////////////////////////////////////////////////////

	@Override
	protected boolean inEditMode() {
		classroomId = getArguments().getLong("classroomId", ChildCareDao.CLASSROOM_NO_ID);
		return classroomId != ChildCareDao.CLASSROOM_NO_ID;	
	}

	@Override
	protected int getCreateTitleResId() {
		return R.string.classroom_details_title_new;
	}

	@Override
	protected int getEditTitleResId() {
		return R.string.classroom_details_title_edit;
	}

	@Override
	protected int getFormNotFilledMessageResId() {
		return R.string.classroom_details_empty_fields;
	}

	@Override
	protected int getDuplicateEntityMessageResId() {
		return R.string.classroom_details_save_failed_already_exists;
	}

	@Override
	protected int getEntityCreatedMessageResId() {
		return R.string.classroom_details_create_succeeded;
	}
	
	@Override
	protected int getEntityEditedMessageResId() {
		return R.string.classroom_details_edit_succeeded;
	}

	@Override
	protected void create() {
		classroom = new Classroom();

		maxChildrenField.setText(classroom.getMaxChildren().toString());
		
		/* -- code stripped -- */
	}

	@Override
	protected void edit() {
		Classroom tmp = 
				BaseDao.INSTANCE.getEntry(Classroom.class, "uid", classroomId);

		if (tmp != null) {
			classroom = tmp;
			nameField.setText(classroom.getDescription());
		}

		maxChildrenField.setText(classroom.getMaxChildren().toString());
		
		/* -- code stripped -- */
	}

	@Override
	protected boolean isFormFilled() {
		String name = nameField.getText().toString().trim();
		return !TextUtils.isEmpty(name);
	}

	@Override
	protected boolean entityAlreadyExists() {
		String name = nameField.getText().toString().trim();
		
		/* -- code stripped -- */
		
		return ChildCareDao.INSTANCE.doesClassroomExist(name);
	}

	@Override
	protected void saveNew() {
		String name = nameField.getText().toString().trim();
		
		/* -- code stripped -- */
		
		setMaxChildrenAndSave();
	}

	@Override
	protected void saveEdited() {
		String name = nameField.getText().toString().trim();
		
		/* -- code stripped -- */
		
		setMaxChildrenAndSave();
	}

	@Override
	protected boolean doCustomChecks() { 
		return true;
	}
	
	@Override
	protected boolean isFormChanged() {
		String name = nameField.getText().toString().trim();
		return !name.equals(classroom.getDescription());
	}

	private void setMaxChildrenAndSave() {

		/* -- code stripped -- */

		BaseDao.INSTANCE.saveEntry(classroom);
	}
}
