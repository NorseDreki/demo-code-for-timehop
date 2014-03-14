package sg.shopster.android.frags.builders;

import cn.shopster.android.R;
import sg.shopster.android.dm.SearchInfo;
import sg.shopster.android.frags.ListLoaderFragment;
import sg.shopster.android.frags.builders.search.SearchFactory.ISearchInfoProvider;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;

/**
 * Base implementation of {@link IFragmentBuilder}.
 * 
 * @author "Alexey Dmitriev"
 * 
 * @param <T> type of items in constructed fragment
 */
public abstract class BaseFragmentBuilder<T> implements IFragmentBuilder<T> {
	
	/** activity under which this fragment is used */
	protected Activity activity;
	
	/** adapter for data in fragment */
	protected ArrayAdapter<T> adapter;
	
	/** notifies of changes in data */
	protected ChangeObserver changeObserver;
	
	/** search helper information */
	protected SearchInfo searchInfo;

	public BaseFragmentBuilder(Activity activity) {
		this.activity = activity;
		searchInfo = new SearchInfo();
	}
	
	public BaseFragmentBuilder(Activity activity, ChangeObserver changeObserver) {
		this(activity);
		
		this.changeObserver = changeObserver;
	}
	
	@Override
	public ArrayAdapter<T> getAdapter() {
		return null; 
	}
	
	@Override
	public OnItemClickListener getOnItemClickListener() {
		return null;
	}
	
	@Override
	public Class<T> getEntityClass() {
		return null;
	}
	
	@Override
	public ISearchInfoProvider getSearchInfoProvider() {
		return null; //not interested in search
	}
	
	@Override
	public ListLoaderFragment<T> getFragment() {
		return new ListLoaderFragment<T>();
	}

	@Override
	public View getEmptyView() {
		return activity.getLayoutInflater().inflate(R.layout.loading, null);
	}
}
