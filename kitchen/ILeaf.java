package net.ccs.android.kitchen;

/**
 * This defines leaf of a tree.
 * In Kitchen, a tree could be:
 * - a meta cell (leaf) which contains several other cells;
 * - a row which contains cells or meta cells;
 * - a table which contains rows or meta rows;
 * 
 * @author "Alexey Dmitriev"
 * 
 */
public interface ILeaf {
	
	/**
	 * Enables or disables this leaf.
	 * 
	 * @param enable whether this leaf is enabled
	 */
	public void enable(boolean enabled);
	
	/**
	 * Invalidates this leaf; the leaf has to update its data.
	 */
	public void invalidate();
	
	/**
	 * The leaf should save its data.
	 */
	public void save();
	
	/**
	 * The leaf is about to be destroyed.
	 */
	public void destroy();
	
	/**
	 * The leaf should restore its previous state.
	 */
	public void undo();

}
