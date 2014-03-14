package sg.shopster.android.frags.builders;

import sg.shopster.android.frags.ListLoaderFragment;
import android.support.v4.app.Fragment;

public class FragmentDirector<T> {
	
	private IFragmentBuilder<T> fb;

	public FragmentDirector(IFragmentBuilder<T> fb) {
		this.fb = fb;
	}
	
	public Fragment buildFragment() {
		ListLoaderFragment<T> result = fb.getFragment();
		result.setAdapter(fb.getAdapter());
		result.setLoader(fb.getLoader());
		result.setItemClick(fb.getOnItemClickListener());
		result.setEntityClass(fb.getEntityClass());
		result.setSearchInfoProvider(fb.getSearchInfoProvider());
		result.setEmptyView(fb.getEmptyView());
		
		return result;
	}
}
