package de.sbtab.containers;

import org.sbml.jsbml.UnitDefinition;

import de.sbtab.controller.SBTabElement;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SBTabUnitDefinitionWrapper implements SBTabElement {
	private UnitDefinition unitDefinition;
	private StringProperty unitDefinitionName;
	private StringProperty unitDefinitionId;
	private StringProperty unitDefinitionSBOTerm;
	
	public SBTabUnitDefinitionWrapper(UnitDefinition unitDefinition) {
		if (unitDefinition != null) {
			setUnitDefinition(unitDefinition);
			initialize();
		}
	}

	public void initialize() {
		// TODO: figure out what fields do we need to work with
		unitDefinitionName = new SimpleStringProperty(unitDefinition.getName());		
		unitDefinitionId = new SimpleStringProperty(unitDefinition.getId());
	}

	@Override
	public void savaData() {
		unitDefinition.setName(unitDefinitionName.getValueSafe());
		unitDefinition.setId(unitDefinitionId.getValueSafe());
	}

	public UnitDefinition getUnitDefinition() {
		return unitDefinition;
	}

	public void setUnitDefinition(UnitDefinition unitDefinition) {
		this.unitDefinition = unitDefinition;
	}

	public StringProperty getUnitDefinitionName() {
		return unitDefinitionName;
	}

	public void setUnitDefinitionName(StringProperty unitDefinitionName) {
		this.unitDefinitionName = unitDefinitionName;
	}

	public StringProperty getUnitDefinitionId() {
		return unitDefinitionId;
	}

	public void setUnitDefinitionId(StringProperty unitDefinitionId) {
		this.unitDefinitionId = unitDefinitionId;
	}
}
