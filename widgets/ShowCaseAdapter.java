package sg.shopster.android.adapters;

import java.util.List;

import cn.shopster.android.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

/**
 * List widget which shows 1 item for even row and 2 items for odd row.
 * 
 * @author "Alexey Dmitriev"
 * 
 * @param <T> type of elements in this list.
 */
public abstract class ShowCaseAdapter<T> extends ArrayAdapter<T> {
	
	/** inflates views from XML files */
	protected LayoutInflater inflater;
	
	/** list of items to be shown */
	protected List<T> items;
	
	/** whether the last row has single item remaining */
	private boolean hasRemainder;
	
	/** remainder itself */
	private int remainder;

	/** container for item views */
	private static class ViewHolderRoot {
		ViewGroup root;
	}

	/** item view */
	protected static class ViewHolderItem {
		ImageView img;
		ImageView subImg;
		TextView title;
		TextView unread;
	}

	public ShowCaseAdapter(Context context, int textViewResourceId, List<T> items) {
		super(context, textViewResourceId, items);
		this.inflater = LayoutInflater.from(context);
		this.items = items;
	}

	/** Subclasses override to define what happens on click */
	protected abstract View.OnClickListener getOnClickListener(T item);
	
	/** Sets up the view having the in-memory item */
	protected abstract void setupItemView(T item, ViewHolderItem holder, boolean isBig);

	@Override
	public int getCount() {
		remainder = items.size() % 3;
		if (remainder > 0)
			hasRemainder = true;
		else
			hasRemainder = false;

		return items.size() / 3 * 2 + remainder; 
	}

	@Override
	public T getItem(int position) {
		return items.get(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolderRoot holder = null;

		if (convertView == null) {
			holder = new ViewHolderRoot();
			convertView = inflater.inflate(R.layout.item_showcase, null);
			holder.root = (ViewGroup) convertView.findViewById(R.id.item_showcase_container);
			convertView.setTag(holder);
			
		} else {
			holder = (ViewHolderRoot) convertView.getTag();
		}

		int diff = getCount() - position;
		if (hasRemainder && diff < remainder) {
			int realPos = position / 2 * 3 + diff;
			T item = items.get(realPos);
			addSection(holder.root, item);
			
		} else if (position > 0 && position % 2 == 1) { //two items in a row
			int realPos = ((position - 1) / 2 * 3) + 1;
			T item1 = items.get(realPos);
			T item2 = items.get(realPos + 1);
			addDoubleSection(holder.root, item1, item2);
			
		} else {
			int realPos = position / 2 * 3;
			T item = items.get(realPos);
			addSection(holder.root, item);
			
		}

		return convertView;
	}

	/** 
	 * Adds section with single item.
	 *  */
	private void addSection(ViewGroup root, final T item) {
		root.removeAllViews();
		doAddSection(root, R.layout.item_showcase_big, item, true);
	}

	/** 
	 * Adds section with double items. 
	 * */
	private void addDoubleSection(ViewGroup root, T item1, T item2) {
		root.removeAllViews();
		doAddSection(root, R.layout.item_showcase_small, item1, false);

		View v = new View(getContext());
		int left = getContext().getResources().getDisplayMetrics().density);
		v.setLayoutParams(new LayoutParams(left, LayoutParams.WRAP_CONTENT));
		root.addView(v);

		doAddSection(root, R.layout.item_showcase_small, item2, false);
	}

	/** 
	 * Does the actual job to add container view to the list.
	 *  */
	private void doAddSection(ViewGroup root, int resId, T item, boolean isBig) {
		View v = inflater.inflate(resId, null);
		v.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, 
				LayoutParams.WRAP_CONTENT, 1f));

		ViewGroup parent = (ViewGroup) v.findViewById(R.id.item_showcase_container);
		parent.setOnClickListener(getOnClickListener(item));

		ViewHolderItem holder = new ViewHolderItem();
		holder.img = (ImageView) v.findViewById(R.id.item_showcase_img);
		holder.title = (TextView) v.findViewById(R.id.item_showcase_title);
		holder.subImg = (ImageView) v.findViewById(R.id.item_showcase_sub_img);
		holder.unread = (TextView) v.findViewById(R.id.item_showcase_unread);

		setupItemView(item, holder, isBig);

		root.addView(v);
	}
}
