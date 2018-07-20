package de.sbtab.services;

import java.util.HashMap;

import org.sbml.jsbml.SBase;

import javafx.scene.control.TableView;

/**
 * Created to generate the desired TableView. 
 * In the future should be connected with TreeView navigation.
 * 
 * */
public class SBTabTableProducer {
	private SBase doc;
	private HashMap<TableType, TableView<?>> tablePull = new HashMap<>();

	public SBTabTableProducer(SBase doc) {
		super();
		this.doc = doc;
	}

	/**
	 * Generates a table according to the specified table type
	 * @return TableView or null
	 * */
	public TableView<?> getTableView(TableType type) {
		if (tablePull.containsKey(type)) {
			return tablePull.get(type);
		} else {
			switch (type) {
			case REACTIONS:
				tablePull.put(TableType.REACTIONS, new SBTabReactionTable(doc).makeTableView());
				return tablePull.get(type);
			case COMPARTMENTS:
				tablePull.put(TableType.COMPARTMENTS, new SBTabCompartmentTable(doc).makeTableView());
				return tablePull.get(type);
			case SPECIES:
				tablePull.put(TableType.SPECIES, new SBTabSpeciesTable(doc).makeTableView());
				return tablePull.get(type);
			case UNIT_DEFINITIONS:
				tablePull.put(TableType.UNIT_DEFINITIONS, new SBTabUnitDefinitionTable(doc).makeTableView());
				return tablePull.get(type);
			case PARAMETERS:
				tablePull.put(TableType.PARAMETERS, new SBTabParameterTable(doc).makeTableView());
				return tablePull.get(type);
			// TODO: add cases for another table factories
			default:
				break;
			}
			return null;
		}
	}
}
