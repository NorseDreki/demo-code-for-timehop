package sg.shopster.android.nav.states;

import cn.shopster.android.R;
import sg.shopster.android.dm.entities.News;
import sg.shopster.android.dm.entities.Offer;
import sg.shopster.android.frags.builders.FavOfferFragmentBuilder;
import sg.shopster.android.frags.builders.FragmentDirector;
import sg.shopster.android.frags.builders.NewsFragmentBuilder;
import sg.shopster.android.frags.builders.OfferFragmentBuilder;
import sg.shopster.android.nav.TabListener;
import android.support.v4.app.Fragment;

import com.actionbarsherlock.app.SherlockFragmentActivity;

/**
 * Builds up Home screen.
 * 
 * @author "Alexey Dmitriev"
 */
public class HomeState extends BaseNavState {
	
	/** */
	private Fragment offerFragment;
	
	/** */
	private Fragment newsFragment;
	
	/** favourites offer*/
	private Fragment favOfferFragment;
	
	/** tab change listener for favourite offers */
	private TabListener favOfferTabListener;
	
	/** tab change listener for all offers */
	private TabListener offerTabListener;
	
	/** tab change listener for news */
	private TabListener newsTabListener;

	public HomeState(final SherlockFragmentActivity activity) {
		super(activity);

		newsFragment = new FragmentDirector<News>(new NewsFragmentBuilder(activity))
				.buildFragment();
		
		offerFragment = new FragmentDirector<Offer>(new OfferFragmentBuilder(activity))
				.buildFragment();
		
		favOfferFragment = new FragmentDirector<Offer>(new FavOfferFragmentBuilder(activity))
				.buildFragment();
		
		offerTabListener = new TabListener(offerFragment);
		newsTabListener = new TabListener(newsFragment);
		favOfferTabListener = new TabListener(favOfferFragment);
	}

	@Override
	public void activate() {
		super.activate();
		
		addTab(R.string.Home_topBar_offers, offerTabListener, offerFragment);
		addTab(R.string.Home_topBar_favourites, favOfferTabListener, favOfferFragment);
		addTab(R.string.Home_topBar_news, newsTabListener, newsFragment);
	}
}