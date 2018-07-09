package de.sbtab.services;

import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.SBase;

import de.sbtab.containers.SBTabCompartmentWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

/**
 * Generates TableView of JSBML Compartment by using <p>SBTabCompartmentWrapper</p>
 * */
public class SBTabCompartmentTable extends SBTabViewAbstractTable implements SBTabTableFactory<Compartment, SBTabCompartmentWrapper> {

	public SBTabCompartmentTable(SBase doc) {
		super(doc);
	}

	@Override
	public TableView<SBTabCompartmentWrapper> makeTableView() {
		if (getDoc() != null) {
			return makeTableView(getDoc().getModel().getListOfCompartments());
		} else {
			return null;
		}
	}

	@Override
	public TableView<SBTabCompartmentWrapper> makeTableView(ListOf<Compartment> list) {
		final ObservableList<SBTabCompartmentWrapper> data = FXCollections.observableArrayList();
		if (getDoc() != null) {
			ListOf<Compartment> listOfCompartments = getDoc().getModel().getListOfCompartments();
			for (Compartment compartment : listOfCompartments) {
				data.add(new SBTabCompartmentWrapper(compartment));
			}
		} else {
			return null;
		}
		return generateTableColumns(data);
	}

	/**
	 * Rearrange data to appropriate columns
	 * */
	private TableView<SBTabCompartmentWrapper> generateTableColumns(final ObservableList<SBTabCompartmentWrapper> data) {
		TableView<SBTabCompartmentWrapper> tableView = new TableView<SBTabCompartmentWrapper>();
		// TODO: figure out what fields do we need to work with
		tableView.getColumns().add(defineColumn("Name", SBTabCompartmentWrapper::getCompartmentName));	
		tableView.getColumns().add(defineColumn("Id", SBTabCompartmentWrapper::getCompartmentId));	
		tableView.getColumns().add(defineColumn("SBO Term", SBTabCompartmentWrapper::getCompartmentSBOTerm));
		tableView.getItems().setAll(data);
		tableView.setEditable(true);

		return tableView;
	}
}
