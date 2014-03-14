package sg.shopster.android.frags.builders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.shopster.android.R;
import sg.shopster.android.activities.RewardInfoActivity;
import sg.shopster.android.adapters.RewardAdapter;
import sg.shopster.android.core.ShopsterDao;
import sg.shopster.android.core.UserManager;
import sg.shopster.android.dm.entities.Level;
import sg.shopster.android.dm.entities.Reward;
import sg.shopster.android.dm.entities.User;
import sg.shopster.android.frags.GridFragment;
import sg.shopster.android.frags.ListLoader;
import sg.shopster.android.frags.ListLoaderFragment;
import android.app.Activity;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;

/**
 * This builder constructs a fragment in which a grid of rewards
 * applicable to the current user is displayed. It also links clicking
 * on a reward with opening a screen with information on that reward
 * 
 * @author "Alexey Dmitriev"
 * 
 */
public class RewardFragmentBuilder extends BaseFragmentBuilder<Reward> {

	public RewardFragmentBuilder(Activity activity) {
		super(activity);
	}

	@Override
	public Loader<List<Reward>> getLoader() {
		return new ListLoader<Reward>(activity) {
			
			@Override
			protected List<Reward> getData() {
				User user = UserManager.INSTANCE.getCurrentUser();
				List<Reward> result = ShopsterDao.INSTANCE.getEntries(user, Reward.class);

				return result;
			}
		};
	}

	@Override
	public OnItemClickListener getOnItemClickListener() {
		return new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				
				final Reward r = (Reward) parent.getItemAtPosition(position); 
				RewardInfoActivity.launch(activity, r);
			}
		};
	}

	@Override
	public ArrayAdapter<Reward> getAdapter() {
		adapter = new RewardAdapter(activity, R.layout.item_reward, 
				new ArrayList<Reward>());
		
		return adapter;
	}

	@Override
	public Class<Reward> getEntityClass() {
		return Reward.class;
	}

	@Override
	public ListLoaderFragment<Reward> getFragment() {
		return new GridFragment<Reward>();
	}
}