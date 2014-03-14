package sg.shopster.android.frags.builders;

import java.util.ArrayList;
import java.util.List;

import cn.shopster.android.R;
import sg.shopster.android.adapters.HomeOfferAdapter;
import sg.shopster.android.core.ShopsterDao;
import sg.shopster.android.dm.entities.Offer;
import sg.shopster.android.frags.ListLoader;
import sg.shopster.android.frags.ListLoaderFragment;
import android.app.Activity;
import android.support.v4.content.Loader;
import android.widget.ArrayAdapter;

/**
 * This builder constructs a fragment in which a list of offers available
 * in the system is displayed. 
 * 
 * @author "Alexey Dmitriev"
 * 
 */
public class OfferFragmentBuilder extends BaseFragmentBuilder<Offer> {

	public OfferFragmentBuilder(Activity activity) {
		super(activity);
	}

	@Override
	public Loader<List<Offer>> getLoader() {
		return new ListLoader<Offer>(activity) {
			
			@Override
			protected List<Offer> getData() {
				return ShopsterDao.INSTANCE.getLatestOffers();
			}
		};
	}

	@Override
	public ArrayAdapter<Offer> getAdapter() {
		adapter = new HomeOfferAdapter(activity, R.layout.item_showcase, 
				new ArrayList<Offer>());
		
		return adapter;
	}

	@Override
	public Class<Offer> getEntityClass() {
		return Offer.class;
	}

	@Override
	public ListLoaderFragment<Offer> getFragment() {
		return new ListLoaderFragment<Offer>() {
			@Override
			protected void setupList() {
				getListView().setPadding(0, 0, 0, 0);
				
				super.setupList();
			}
		};
	}
}