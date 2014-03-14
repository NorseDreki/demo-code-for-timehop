package sg.shopster.android.core;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;

import sg.shopster.android.dialogs.BaseButtonsDialog;
import sg.shopster.android.dialogs.DialogButtonsListener;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.support.v4.app.FragmentTransaction;

/**
 * Helper class to show bubbles. All registered bubbles are waiting in queue.
 * Processing is done sequentially, and only single bubble can be on screen
 * at a time.
 * 
 * @author "Alexey Dmitriev"
 * 
 */
public enum BubbleManager {
	INSTANCE;
	
	/** queue for awaiting bubbles */
	private Queue<BubbleTag> queue;
	
	/** previously registered listener for bubble actions */
	private DialogButtonsListener oldListener;
	
	/** currently registered listener for bubble actions */
	private DialogButtonsListener listener;
	
	/** whether any bubble is on screen */
	private AtomicBoolean isShowing;
	
	/** plays accompanying sound when bubble goes to screen */
	private MediaPlayer player;
	
	private BubbleManager() {
		queue = new LinkedList<BubbleTag>();
		listener = new BubbleDismissListener();
		isShowing = new AtomicBoolean();
		
		player = new MediaPlayer();
		
	    try {
	    	AssetFileDescriptor afd = ShopsterDao.INSTANCE.getBubbleFd();
			player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), 
					afd.getLength());
			
		    player.prepare();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Plays bubble sound.
	 */
	private void playSound() {
		player.start();
	}
	
	/**
	 * Adds the bubble to the queue. When all previous bubbles are processed,
	 * this one goes on screen.
	 * 
	 * @param bubble bubble to be shown on screen
	 * @param ft transaction on behalf of which the bubble is shown
	 */
	public synchronized void post(BaseButtonsDialog bubble, FragmentTransaction ft) {
		oldListener = bubble.getDialogButtonsListener();
		bubble.setDialogButtonsListener(listener);
		
		BubbleTag tag = new BubbleTag();
		tag.dialog = bubble;
		tag.ft = ft;
		queue.add(tag);
		
		showNextBubbleIfAppropriate();
	}
	
	/**
	 * If no bubble is currently on screen, shows the bubble on top of the queue.
	 * Otherwise, does nothing.
	 */
	private void showNextBubbleIfAppropriate() {
		if (!isShowing.get()) {
			BubbleTag tag = queue.poll();
			if (tag != null) {
				isShowing.set(true);
				playSound();
				tag.dialog.show(tag.ft, tag.dialog.getClass().getSimpleName());
			}
		}
	}
	
	/**
	 * Helper class to group bubble's window and transaction on behalf of 
	 * which it is shown.
	 */
	private static class BubbleTag {
		BaseButtonsDialog dialog;
		FragmentTransaction ft;
	}
	
	/**
	 * Injects a helper listener to catch an event of dismissing the bubble
	 * so that this manager could show the next one.
	 */
	private class BubbleDismissListener implements DialogButtonsListener {
		@Override
		public void onPositiveClick() {
			if (oldListener != null)
				oldListener.onPositiveClick();
		}

		@Override
		public void onNegativeClick() {
			if (oldListener != null)
				oldListener.onNegativeClick();
			
			isShowing.set(false);
			showNextBubbleIfAppropriate();
		}
	}
}
