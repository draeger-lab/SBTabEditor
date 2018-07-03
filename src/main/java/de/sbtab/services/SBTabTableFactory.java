package de.sbtab.services;

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.SBase;

import de.sbtab.controller.SBTabElement;
import javafx.scene.control.TableView;

/**
 * Represents common interface for every SBTabTableFactory
 * */
public interface SBTabTableFactory<T extends SBase, S extends SBTabElement> {
	
	/**
	 * Generates a TableView of SBTabElement's with predefined ListOf<T> in
	 * corresponding factory
	 * 
	 * @return TableView<S> or null
	 * 
	 * */
	public TableView<S> makeTableView();

	/**
	 * Generates a TableView of SBTabElement's with the conversion of each
	 * corresponding element of type T to SBTabElement
	 * 
	 * @return TableView<S> or null
	 * 
	 * */
	public TableView<S> makeTableView(ListOf<T> list);

}
