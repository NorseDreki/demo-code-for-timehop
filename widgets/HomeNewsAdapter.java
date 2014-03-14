package sg.shopster.android.adapters;

import java.util.List;

import cn.shopster.android.R;
import sg.shopster.android.activities.NewsInfoActivity;
import sg.shopster.android.dm.entities.News;
import sg.shopster.android.monitors.Monitors;
import sg.shopster.android.net.ImageUrls;
import android.content.Context;
import android.view.View;

/**
 * This list widget shows news.
 * 
 * List widget which shows 1 item for even rows and 2 items for odd rows.
 * 
 * @author "Alexey Dmitriev"
 * 
 */
public class HomeNewsAdapter extends ShowCaseAdapter<News> {

	public HomeNewsAdapter(Context context, int textViewResourceId, List<News> items) {
		super(context, textViewResourceId, items);
	}

	@Override
	protected View.OnClickListener getOnClickListener(final News item) {
		return new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				NewsInfoActivity.launch(getContext(), item);				
			}
		};
	}

	@Override
	protected void setupItemView(News item, ViewHolderItem holder, boolean isBig) {
		holder.title.setText(item.getTitle());
		holder.subImg.setVisibility(View.GONE);

		boolean read = Monitors.INSTANCE.getReadNewsMonitor().isUsed(item.getId(), null);
		holder.unread.setVisibility(read ? View.GONE : View.VISIBLE);

		if (isBig) {
			ImageUrls.displayImage(item, holder.img, R.drawable.default_news_m);
		} else {
			ImageUrls.displayImage(item, holder.img, R.drawable.default_news_s);
		}
	}
}