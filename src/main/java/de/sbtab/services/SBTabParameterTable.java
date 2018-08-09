package de.sbtab.services;

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.SBase;

import de.sbtab.containers.SBTabParameterWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

/**
 * Generates TableView of JSBML Parameter by using {@code SBTabParameterWrapper}
 */
public class SBTabParameterTable extends SBTabViewAbstractTable
		implements SBTabTableFactory<Parameter, SBTabParameterWrapper> {

	public SBTabParameterTable(SBase doc) {
		super(doc);
	}

	@Override
	public TableView<SBTabParameterWrapper> makeTableView() {
		if (getDoc() != null) {
			return makeTableView(getDoc().getModel().getListOfParameters());
		} else {
			return null;
		}
	}

	@Override
	public TableView<SBTabParameterWrapper> makeTableView(ListOf<Parameter> list) {
		final ObservableList<SBTabParameterWrapper> data = FXCollections.observableArrayList();
		if (getDoc() != null) {
			ListOf<Parameter> listOfParameter = getDoc().getModel().getListOfParameters();
			for (Parameter parameter : listOfParameter) {
				data.add(new SBTabParameterWrapper(parameter));
			}
		} else {
			return null;
		}
		return generateTableColumns(data);
	}

	/**
	 * Rearrange data to appropriate columns
	 */
	private TableView<SBTabParameterWrapper> generateTableColumns(final ObservableList<SBTabParameterWrapper> data) {
		TableView<SBTabParameterWrapper> tableView = new TableView<SBTabParameterWrapper>();
		// TODO: figure out what fields do we need to work with
		tableView.getColumns().add(defineColumn("Name", SBTabParameterWrapper::getParameterName, SBTabParameterWrapper::setParameterName));
		tableView.getColumns().add(defineColumn("Id", SBTabParameterWrapper::getParameterId, SBTabParameterWrapper::setParameterId));
		tableView.getColumns().add(defineColumn("SBO Term", SBTabParameterWrapper::getParameterSBOTerm, SBTabParameterWrapper::setParameterSBOTerm));
		tableView.getItems().setAll(data);
		tableView.setEditable(true);

		return tableView;
	}
}
