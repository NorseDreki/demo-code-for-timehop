package sg.shopster.android.frags.builders;

import java.util.ArrayList;
import java.util.List;

import cn.shopster.android.R;
import sg.shopster.android.activities.OfferSwipeActivity;
import sg.shopster.android.adapters.BaseSpotAdapter;
import sg.shopster.android.core.ShopsterDao;
import sg.shopster.android.dm.BaseSpot;
import sg.shopster.android.dm.entities.Offer;
import sg.shopster.android.frags.ListLoader;
import sg.shopster.android.frags.ListLoaderFragment;
import sg.shopster.android.monitors.Monitors;
import android.app.Activity;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;

/**
 * This builder constructs a fragment in which a list of offers
 * applicable to the current user is displayed. It also links clicking
 * on offer with opening a screen with information on that offer
 * 
 * @author "Alexey Dmitriev"
 * 
 */
public class FavSpotFragmentBuilder extends BaseFragmentBuilder<BaseSpot> {
	
	private ListLoader<BaseSpot> loader;

	public FavSpotFragmentBuilder(Activity activity) {
		super(activity);
		changeObserver = 
				Monitors.INSTANCE.getFavouriteSpotMonitor().getChangeObserver();
		
		changeObserver.enableNotifications();
	}

	@Override
	public Loader<List<BaseSpot>> getLoader() {
		loader = new ListLoader<BaseSpot>(activity) {
			@Override
			protected List<BaseSpot> getData() {
				return ShopsterDao.INSTANCE.getFavouriteSpots();
			}
		};
		
		changeObserver.registerObserver(loader);
		return loader;
	}

	@Override
	public OnItemClickListener getOnItemClickListener() {
		return new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, 
					long id) {
				
				final BaseSpot s = (BaseSpot) parent.getItemAtPosition(position); 
				OfferSwipeActivity.launch(activity, s, -1);
			}
		};
	}
	
	@Override
	public ArrayAdapter<BaseSpot> getAdapter() {
		adapter = new BaseSpotAdapter<BaseSpot>(activity, 
				R.layout.item_basespot, new ArrayList<BaseSpot>());
		
		return adapter;
	}

	@Override
	public Class<BaseSpot> getEntityClass() {
		return BaseSpot.class;
	}
	
	@Override
	public ListLoaderFragment<BaseSpot> getFragment() {
		return new ListLoaderFragment<BaseSpot>() {
			
			@Override
			public void onResume() {
				super.onResume();
				loader.onContentChanged();
			}
		};
	}
	
	@Override
	public View getEmptyView() {
		return activity.getLayoutInflater().inflate(R.layout.no_fav_offers, null);
	}
}
