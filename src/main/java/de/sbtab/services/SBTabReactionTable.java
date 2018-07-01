package de.sbtab.services;

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBase;

import de.sbtab.controller.SBTabReactionWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn.CellEditEvent;

/**
 * Generates TableView of JSBML Reaction by using <p>SBTabReactionWrapper<p>
 * */
public class SBTabReactionTable extends SBTabViewAbstractTable implements SBTabTableFactory<Reaction, SBTabReactionWrapper> {

	public SBTabReactionTable(SBase doc) {
		super(doc);
	}

	@Override
	public TableView<SBTabReactionWrapper> makeTableView() {
		if (getDoc() != null) {
			return makeTableView(getDoc().getModel().getListOfReactions());
		} else {
			return null;
		}
	}

	@Override
	public TableView<SBTabReactionWrapper> makeTableView(ListOf<Reaction> list) {
		final ObservableList<SBTabReactionWrapper> data = FXCollections.observableArrayList();
		if (getDoc() != null) {
			ListOf<Reaction> listOfReactions = getDoc().getModel().getListOfReactions();
			for (Reaction reaction : listOfReactions) {
				data.add(new SBTabReactionWrapper(reaction));
			}
		} else {
			return null;
		}
		return generateTableColumns(data);
	}

	/**
	 * Rearrange data to appropriate columns
	 * */
	private TableView<SBTabReactionWrapper> generateTableColumns(final ObservableList<SBTabReactionWrapper> data) {
		TableView<SBTabReactionWrapper> tableView = new TableView<SBTabReactionWrapper>();
		// TODO: figure out what fields do we need to work with
		TableColumn reactionName = defineColumn("Name", SBTabReactionWrapper::getReactionName);
		tableView.getColumns().add(reactionName);		
		TableColumn reactionId = defineColumn("Id", SBTabReactionWrapper::getReactionId);
		tableView.getColumns().add(reactionId);
		TableColumn reactionSBOTerm = defineColumn("SBO Term", SBTabReactionWrapper::getSBOTerm);
		tableView.getColumns().add(reactionSBOTerm);
		tableView.getItems().setAll(data);
		tableView.setEditable(true);

		reactionName.setCellFactory(TextFieldTableCell.forTableColumn());
        reactionName.setOnEditCommit(
            new EventHandler<CellEditEvent<SBTabReactionWrapper, String>>() {
                @Override
                public void handle(CellEditEvent<SBTabReactionWrapper, String> t) {
                    SBTabReactionWrapper y = ((SBTabReactionWrapper) t.getTableView().getItems().get(
                            t.getTablePosition().getRow()));
            		StringProperty changedValue = new SimpleStringProperty(t.getNewValue());
                    y.setReactionName(changedValue);
                }
            }
		 );
        
        reactionId.setCellFactory(TextFieldTableCell.forTableColumn());
        reactionId.setOnEditCommit(
            new EventHandler<CellEditEvent<SBTabReactionWrapper, String>>() {
                @Override
                public void handle(CellEditEvent<SBTabReactionWrapper, String> t) {
                    SBTabReactionWrapper y = ((SBTabReactionWrapper) t.getTableView().getItems().get(
                            t.getTablePosition().getRow()));
            		StringProperty changedValue = new SimpleStringProperty(t.getNewValue());
                    y.setReactionId(changedValue);
                }
            }
		 );
        
        reactionSBOTerm.setCellFactory(TextFieldTableCell.forTableColumn());
        reactionSBOTerm.setOnEditCommit(
            new EventHandler<CellEditEvent<SBTabReactionWrapper, String>>() {
                @Override
                public void handle(CellEditEvent<SBTabReactionWrapper, String> t) {
                    SBTabReactionWrapper y = ((SBTabReactionWrapper) t.getTableView().getItems().get(
                            t.getTablePosition().getRow()));
            		StringProperty changedValue = new SimpleStringProperty(t.getNewValue());
                    y.setSBOTerm(changedValue);
                }
            }
		 );

		return tableView;
	}
}
