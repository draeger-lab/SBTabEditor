package de.sbtab.services;

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBase;

import de.sbtab.containers.SBTabReactionWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

/**
 * Generates TableView of JSBML Reaction by using
 * <p>
 * SBTabReactionWrapper
 * <p/>
 */
public class SBTabReactionTable extends SBTabViewAbstractTable
		implements SBTabTableFactory<Reaction, SBTabReactionWrapper> {

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
	 */
	private TableView<SBTabReactionWrapper> generateTableColumns(final ObservableList<SBTabReactionWrapper> data) {
		TableView<SBTabReactionWrapper> tableView = new TableView<SBTabReactionWrapper>();
		// TODO: figure out what fields do we need to work with
		tableView.getColumns().add(defineColumn("Name", SBTabReactionWrapper::getReactionName, SBTabReactionWrapper::setReactionName));
		tableView.getColumns().add(defineColumn("Id", SBTabReactionWrapper::getReactionId, SBTabReactionWrapper::setReactionId));
		tableView.getColumns().add(defineColumn("SBO Term", SBTabReactionWrapper::getSBOTerm, SBTabReactionWrapper::setSBOTerm));
		tableView.getColumns().add(defineColumn("Compartment", SBTabReactionWrapper::getCompartment, SBTabReactionWrapper::setCompartment));
		//tableView.getColumns().add(defineColumn("KineticLaw", SBTabReactionWrapper::getKineticLaw, SBTabReactionWrapper::setKineticLaw));
		tableView.getItems().setAll(data);
		tableView.setEditable(true);

		return tableView;
	}
}
