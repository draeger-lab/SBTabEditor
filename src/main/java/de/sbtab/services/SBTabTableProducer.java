package de.sbtab.services;

import org.sbml.jsbml.SBase;

import javafx.scene.control.TableView;

/**
 * Created to generate the desired TableView. 
 * In the future should be connected with TreeView navigation.
 * TODO: corresponding TableView must be saved in order to not generate TableView 
 * 		 repeatedly after each user interaction with TreeView
 * 
 * */
public class SBTabTableProducer {
	private SBase doc;

	public SBTabTableProducer(SBase doc) {
		super();
		this.doc = doc;
	}

	/**
	 * Generates a table according to the specified table type
	 * @return TableView or null
	 * */
	public TableView<?> getTableView(TableType type) {
		switch (type) {
		case REACTION:
			return new SBTabReactionTable(doc).makeTableView();
		// TODO: add cases for another table factories
		default:
			break;
		}
		return null;
	}
}
