package sg.shopster.android.monitors;

/**
 * Binary state monitor. Monitors whether an object of specific type
 * was used or not. Also queries current state of the object.
 * 
 * @author "Alexey Dmitriev"
 *
 */
public interface IObjectMonitor {
	
	/**
	 * Whether an item is used and is present in registry.
	 * 
	 * @param id
	 * @param type TODO
	 * @return
	 */
	public boolean isUsed(int id, String type);
	
	/**
	 * Mark item as used.
	 * 
	 * @param id
	 * @param type TODO
	 */
	public void use(int id, String type);
	
	/**
	 * Mark item as unused.
	 * 
	 * @param id
	 * @param type TODO
	 */
	public void unuse(int id, String type);

}
