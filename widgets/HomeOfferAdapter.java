package sg.shopster.android.adapters;

import java.util.List;

import cn.shopster.android.R;
import sg.shopster.android.activities.OfferSwipeActivity;
import sg.shopster.android.core.ShopsterDao;
import sg.shopster.android.dm.entities.Mall;
import sg.shopster.android.dm.entities.Offer;
import sg.shopster.android.dm.entities.Store;
import sg.shopster.android.helpers.DisplayImageHelper;
import sg.shopster.android.net.ImageUrls;
import android.content.Context;
import android.os.Parcelable;
import android.view.View;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * This list widget shows offers.
 * 
 * List widget which shows 1 item for even rows and 2 items for odd rows.
 * 
 * @author "Alexey Dmitriev"
 * 
 */
public class HomeOfferAdapter extends ShowCaseAdapter<Offer> {

	public HomeOfferAdapter(Context context, int textViewResourceId, List<Offer> items) {
		super(context, textViewResourceId, items);

		/* -- code stripped -- */
	}

	@Override
	protected View.OnClickListener getOnClickListener(final Offer item) {
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				/* -- code stripped -- */
				
			}
		};
	}

	@Override
	protected void setupItemView(Offer item, ViewHolderItem holder, boolean isBig) {
		String url = null;
		holder.title.setText(item.getTitle());		

		/* -- code stripped -- */
	}
}