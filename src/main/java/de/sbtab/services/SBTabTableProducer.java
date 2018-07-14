package de.sbtab.services;

import java.util.HashMap;

import org.sbml.jsbml.SBase;

import de.sbtab.containers.SBTabCompartmentWrapper;
import de.sbtab.containers.SBTabReactionWrapper;
import de.sbtab.containers.SBTabSpeciesWrapper;
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
			case REACTION:
				tablePull.put(TableType.REACTION, new SBTabReactionTable(doc).makeTableView());
				return tablePull.get(type);
			case COMPARTEMENT:
				tablePull.put(TableType.COMPARTEMENT, new SBTabCompartmentTable(doc).makeTableView());
				return tablePull.get(type);
			case SPECIE:
				tablePull.put(TableType.SPECIE, new SBTabSpeciesTable(doc).makeTableView());
				return tablePull.get(type);
			// TODO: add cases for another table factories
			default:
				break;
			}
			return null;
		}
	}
}
