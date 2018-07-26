package de.sbtab.containers;

import org.sbml.jsbml.Species;

import de.sbtab.controller.SBTabElement;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SBTabSpeciesWrapper implements SBTabElement {
	private Species species;
	private StringProperty speciesName;
	private StringProperty speciesId;
	private StringProperty speciesCompartment;
	
	public SBTabSpeciesWrapper(Species species) {
		if (species != null) {
			setSpecies(species);
			initialize();
		}
	}

	public void initialize() {
		// TODO: figure out what fields do we need to work with
		speciesName = new SimpleStringProperty(species.getName());		
		speciesId = new SimpleStringProperty(species.getId());
		speciesCompartment = new SimpleStringProperty(species.getCompartment());
		
	}
	

	public Species getSpecies() {
		return species;
	}

	public void setSpecies(Species species) {
		this.species = species;
	}

	public StringProperty getSpeciesName() {
		return speciesName;
	}

	public void setSpeciesName(StringProperty speciesName) {
		this.speciesName = speciesName;
	}

	public StringProperty getSpeciesId() {
		return speciesId;
	}

	public void setSpeciesId(StringProperty speciesId) {
		this.speciesId = speciesId;
	}

	public StringProperty getSpeciesCompartment() {
		return speciesCompartment;
	}

	public void setSpeciesCompartment(StringProperty speciesCompartment) {
		this.speciesCompartment = speciesCompartment;
	}
}
