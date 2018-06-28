package de.sbtab.services;

import java.util.function.Function;

import org.sbml.jsbml.SBase;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;

/**
 * This abstract class contains the basic methods for generating SBTab tables.
 * Should be subclassed when implementing another SBTabTableFactory
 * */
public abstract class SBTabViewAbstractTable {

	private SBase doc;

	public SBase getDoc() {
		return doc;
	}

	public SBTabViewAbstractTable(SBase doc) {
		super();
		this.doc = doc;
	}

	/**
	 * Generates column entity for TableView
	 * */
	protected <S, T> TableColumn<S, T> defineColumn(String text, Function<S, ObservableValue<T>> property) {
		TableColumn<S, T> col = new TableColumn<>(text);
		col.setCellValueFactory(cellData -> property.apply(cellData.getValue()));
		return col;
	}
}
