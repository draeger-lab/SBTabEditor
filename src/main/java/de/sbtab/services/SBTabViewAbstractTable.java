package de.sbtab.services;

import java.lang.reflect.Method;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import org.sbml.jsbml.SBase;

import de.sbtab.containers.SBTabReactionWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.WritableValue;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.ContextMenu;
import javafx.scene.input.*;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;

/**
 * This abstract class contains the basic methods for generating SBTab tables.
 * Should be subclassed when implementing another SBTabTableFactory
 */
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
	 */
	protected <S, T> TableColumn<S, T> defineColumn(String text, Function<S, ObservableValue<T>> property, 
			BiConsumer<S, StringProperty> setMethod) {
		TableColumn<S, T> col = new TableColumn<S, T>(text);
		col.setCellValueFactory(cellData -> property.apply(cellData.getValue()));
		col.setEditable(true);

		StringConverter converter = new DefaultStringConverter();
		col.setCellFactory(TextFieldTableCell.forTableColumn(converter));

		EventHandler<CellEditEvent<S, T>> x = new EventHandler<CellEditEvent<S, T>>() {
			@Override
			public void handle(CellEditEvent<S, T> t) {
				S position = ((S) t.getTableView().getItems().get(t.getTablePosition().getRow()));
				StringProperty changedValue = new SimpleStringProperty((String) t.getNewValue());
				setMethod.accept(position, changedValue);
			}
		};
		col.setOnEditCommit(x);
		
		//NODES
		TextField field = new TextField("Example");
		
		Text txtNode = new Text("Example");
		txtNode.setX(80);
		txtNode.setY(140);
		
		//Context Menu
		ContextMenu context = new ContextMenu();
		
		//Menu Items
		MenuItem item1 = new MenuItem("add column");
		MenuItem item2 = new MenuItem("delete column");
		MenuItem item3 = new MenuItem("show column");
		MenuItem item4 = new MenuItem("hide column");
		
		//add Item to Context Menu
		context.getItems().addAll(item1, item2, item3, item4);
		
		//Set Context Menu to Control
		col.setContextMenu(context);
		
		col.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent t) {
				if(t.getButton() == MouseButton.SECONDARY) {
					context.show(txtNode, t.getScreenX(), t.getScreenY());
					
				}
			}
		});

		return col;
	}
}
