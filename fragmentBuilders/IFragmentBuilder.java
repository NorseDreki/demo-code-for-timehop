package sg.shopster.android.frags.builders;

import java.util.List;

import sg.shopster.android.frags.ListLoaderFragment;
import sg.shopster.android.frags.builders.search.SearchFactory.ISearchInfoProvider;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

/**
 * Helper interface which helps {@link FragmentDirector} to build fragment.
 *   
 * @author Alexey Dmitriev
 *
 * @param <T> type of data in list
 */
public interface IFragmentBuilder<T> {
	
	/**
	 * Constructs new instance of fragment.
	 * 
	 * @return new fragment
	 */
	public ListLoaderFragment<T> getFragment();
	
	/**
	 * Constructs loader to load content in async manner (typically, from DB).
	 * 
	 * @return loader which loads list of items from data source
	 */
	public Loader<List<T>> getLoader();
	
	/**
	 * Constructs item click listener which is used to handle clicks on list items.
	 * 
	 * @return instance of item click listener
	 */
	public AdapterView.OnItemClickListener getOnItemClickListener();
	
	/**
	 * Constructs adapter which is bound to list control.
	 * Loader pours its data in this adapter.
	 * 
	 * @return instance of adapter
	 */
	public ArrayAdapter<T> getAdapter();
	
	/**
	 * Returns class of entities populated in the list.
	 */
	public Class<T> getEntityClass();
	
	/**
	 * Returns search helper which can search in this list.
	 */
	public ISearchInfoProvider getSearchInfoProvider();
	
	/**
	 * Returns empty view which is displayed when list has no data.
	 */
	public View getEmptyView();

}
