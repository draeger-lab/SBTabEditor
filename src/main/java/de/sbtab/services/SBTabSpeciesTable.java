package de.sbtab.services;

import org.sbml.jsbml.Species;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.SBase;

import de.sbtab.containers.SBTabSpeciesWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

/**
 * Generates TableView of JSBML Species by using <p>SBTabSpeciesWrapper</p>
 * */
public class SBTabSpeciesTable extends SBTabViewAbstractTable implements SBTabTableFactory<Species, SBTabSpeciesWrapper> {

	public SBTabSpeciesTable(SBase doc) {
		super(doc);
	}

	@Override
	public TableView<SBTabSpeciesWrapper> makeTableView() {
		if (getDoc() != null) {
			return makeTableView(getDoc().getModel().getListOfSpecies());
		} else {
			return null;
		}
	}

	@Override
	public TableView<SBTabSpeciesWrapper> makeTableView(ListOf<Species> list) {
		final ObservableList<SBTabSpeciesWrapper> data = FXCollections.observableArrayList();
		if (getDoc() != null) {
			ListOf<Species> listOfSpecies = getDoc().getModel().getListOfSpecies();
			for (Species species : listOfSpecies) {
				data.add(new SBTabSpeciesWrapper(species));
			}
		} else {
			return null;
		}
		return generateTableColumns(data);
	}

	/**
	 * Rearrange data to appropriate columns
	 * */
	private TableView<SBTabSpeciesWrapper> generateTableColumns(final ObservableList<SBTabSpeciesWrapper> data) {
		TableView<SBTabSpeciesWrapper> tableView = new TableView<SBTabSpeciesWrapper>();
		// TODO: figure out what fields do we need to work with
		tableView.getColumns().add(defineColumn("Name", SBTabSpeciesWrapper::getSpeciesName, SBTabSpeciesWrapper::setSpeciesName));	
		tableView.getColumns().add(defineColumn("Id", SBTabSpeciesWrapper::getSpeciesId, SBTabSpeciesWrapper::setSpeciesId));	
		tableView.getColumns().add(defineColumn("Compartment", SBTabSpeciesWrapper::getSpeciesCompartment, SBTabSpeciesWrapper::setSpeciesCompartment));
		tableView.getItems().setAll(data);
		tableView.setEditable(true);

		return tableView;
	}
}
