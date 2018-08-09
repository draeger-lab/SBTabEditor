package de.sbtab.containers;

import org.sbml.jsbml.Compartment;

import de.sbtab.controller.SBTabElement;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SBTabCompartmentWrapper implements SBTabElement {
	private Compartment compartment;
	private StringProperty compartmentName;
	private StringProperty compartmentId;
	private StringProperty compartmentSBOTerm;
	
	public SBTabCompartmentWrapper(Compartment compartment) {
		if (compartment != null) {
			setCompartment(compartment);
			initialize();
		}
	}

	public void initialize() {
		// TODO: figure out what fields do we need to work with
		compartmentName = new SimpleStringProperty(compartment.getName());		
		compartmentId = new SimpleStringProperty(compartment.getId());
		compartmentSBOTerm = new SimpleStringProperty(compartment.getSBOTermID());
	}

	@Override
	public void saveData() {
		compartment.setName(compartmentName.getValueSafe());
		compartment.setId(compartmentId.getValueSafe());
		compartment.setSBOTerm(compartmentSBOTerm.getValueSafe());
	}

	public Compartment getCompartment() {
		return compartment;
	}

	public void setCompartment(Compartment compartment) {
		this.compartment = compartment;
	}

	public StringProperty getCompartmentName() {
		return compartmentName;
	}

	public void setCompartmentName(StringProperty compartmentName) {
		this.compartmentName = compartmentName;
		saveData();
	}

	public StringProperty getCompartmentId() {
		return compartmentId;
	}

	public void setCompartmentId(StringProperty compartmentId) {
		this.compartmentId = compartmentId;
		saveData();
	}

	public StringProperty getCompartmentSBOTerm() {
		return compartmentSBOTerm;
	}

	public void setCompartmentSBOTerm(StringProperty compartmentSBOTerm) {
		this.compartmentSBOTerm = compartmentSBOTerm;
		saveData();
	}
}
