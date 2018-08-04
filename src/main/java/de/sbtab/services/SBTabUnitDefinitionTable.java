package de.sbtab.services;

import org.sbml.jsbml.UnitDefinition;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.SBase;

import de.sbtab.containers.SBTabUnitDefinitionWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

/**
 * Generates TableView of JSBML UnitDefinition by using {@code SBTabUnitDefinitionWrapper}
 * */
public class SBTabUnitDefinitionTable extends SBTabViewAbstractTable implements SBTabTableFactory<UnitDefinition, SBTabUnitDefinitionWrapper> {

	public SBTabUnitDefinitionTable(SBase doc) {
		super(doc);
	}

	@Override
	public TableView<SBTabUnitDefinitionWrapper> makeTableView() {
		if (getDoc() != null) {
			return makeTableView(getDoc().getModel().getListOfUnitDefinitions());
		} else {
			return null;
		}
	}

	@Override
	public TableView<SBTabUnitDefinitionWrapper> makeTableView(ListOf<UnitDefinition> list) {
		final ObservableList<SBTabUnitDefinitionWrapper> data = FXCollections.observableArrayList();
		if (getDoc() != null) {
			ListOf<UnitDefinition> listOfUnitDefinition = getDoc().getModel().getListOfUnitDefinitions();
			for (UnitDefinition UnitDefinition : listOfUnitDefinition) {
				data.add(new SBTabUnitDefinitionWrapper(UnitDefinition));
			}
		} else {
			return null;
		}
		return generateTableColumns(data);
	}

	/**
	 * Rearrange data to appropriate columns
	 * */
	private TableView<SBTabUnitDefinitionWrapper> generateTableColumns(final ObservableList<SBTabUnitDefinitionWrapper> data) {
		TableView<SBTabUnitDefinitionWrapper> tableView = new TableView<SBTabUnitDefinitionWrapper>();
		// TODO: figure out what fields do we need to work with
		tableView.getColumns().add(defineColumn("Name", SBTabUnitDefinitionWrapper::getUnitDefinitionName, SBTabUnitDefinitionWrapper::setUnitDefinitionName));	
		tableView.getColumns().add(defineColumn("Id", SBTabUnitDefinitionWrapper::getUnitDefinitionId, SBTabUnitDefinitionWrapper::setUnitDefinitionId));	
		tableView.getItems().setAll(data);
		tableView.setEditable(true);

		return tableView;
	}
}
