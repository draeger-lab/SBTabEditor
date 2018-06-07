package de.sbmltab.view;

public interface SBMLTabTableView {

	/**
	 * Updates corresponding view
	 */
	public void update();

	/**
	 * Signals that corresponding view will not be used anymore and any references
	 * can be removed.
	 */
	public void dispose();
}
