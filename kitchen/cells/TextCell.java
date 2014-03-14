package net.ccs.android.kitchen.cells;

import android.widget.TableRow;

/**
 * Cell which displays text. Can be spanned across several columns.
 * 
 * @author "Alexey Dmitriev"
 *
 */
public class TextCell extends BaseCell {
	
	/** text to display */
	private String text;
	
	/** how many columns this cell should span */
	private Integer colspan;

	public TextCell(String text) {
		super(false);
		this.text = text;
	}
	
	public TextCell(String text, Integer colspan) {
		this(text);
		this.colspan = colspan;
	}

	@Override
	public String getDisplayedText() {
		return text;
	}
	
	@Override
	public void addToRow(TableRow row) {
		addTextView(row, getDisplayedText(), colspan);
	}
}
