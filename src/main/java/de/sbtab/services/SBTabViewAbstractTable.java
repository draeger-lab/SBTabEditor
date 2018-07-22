package de.sbtab.services;

import java.util.function.BiConsumer;
import java.util.function.Function;

import org.sbml.jsbml.SBase;

import de.sbtab.utils.SBTabMenuBar;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.*;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;

/**
 * This abstract class contains the basic methods for generating SBTab tables.
 * Should be extended when implementing another SBTabTableFactory
 * @author zakharc
 * 
 */
public abstract class SBTabViewAbstractTable {

	private SBase doc;

	public SBTabViewAbstractTable(SBase doc) {
		super();
		this.doc = doc;
	}

	/**
	 * Generates column entity for TableView
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected <S, T> TableColumn<S, T> defineColumn(String text, Function<S, ObservableValue<T>> property, 
			BiConsumer<S, StringProperty> setMethod) {
		SBTabMenuBar popUp = new SBTabMenuBar();
		TableColumn<S, T> col = new TableColumn<S, T>(text);
		col.setCellValueFactory(cellData -> property.apply(cellData.getValue()));
		col.setEditable(true);
		col.setCellFactory(TextFieldTableCell.forTableColumn((StringConverter) new DefaultStringConverter()));
		col.setOnEditCommit(e -> editCell(setMethod, e));
		col.setContextMenu(popUp
				// TODO: add functionality for every item
				.addMenuBarItem("add column", e -> {})
				.addMenuBarItem("delete column", e -> {})
				.addMenuBarItem("show column", e -> {})
				.addMenuBarItem("hide column", e -> col.setVisible(false))
				.getContext());
		col.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> showMenuBar(popUp, e));
		return col;
	}

	/**
	 * Defines an action if event on table row fired
	 * @param setMethod function to fire on event
	 * @param e event on table row 
	 * */
	private <S, T> void editCell(BiConsumer<S, StringProperty> setMethod, CellEditEvent<S, T> e) {
		S position = ((S) e.getTableView().getItems().get(e.getTablePosition().getRow()));
		StringProperty changedValue = new SimpleStringProperty((String) e.getNewValue());
		setMethod.accept(position, changedValue);
	}
	
	/**
	 * Show menu bar on some event
	 * @param menuBar SBTabMenuBar to show
	 * @param e event to show menu bar on
	 * 
	 * */
	private void showMenuBar(SBTabMenuBar menuBar, MouseEvent e) {
		if (e.getButton() == MouseButton.SECONDARY) menuBar.showDefault(e);
	}
	
	public SBase getDoc() {
		return doc;
	}
}
