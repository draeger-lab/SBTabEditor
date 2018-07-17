package de.sbtab.containers;

import org.sbml.jsbml.Reaction;

import de.sbtab.controller.SBTabElement;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SBTabReactionWrapper implements SBTabElement {
	private Reaction reaction;
	private StringProperty reactionName;
	private StringProperty reactionId;
	private StringProperty reactionSBOTerm;
	private StringProperty reactionCompartment;
	//private StringProperty reactionKineticLaw;
	

	public SBTabReactionWrapper(Reaction reaction) {
		if (reaction != null) {
			setReaction(reaction);
			initialize();
		}
	}

	public void initialize() {
		// TODO: figure out what fields do we need to work with
		reactionName = new SimpleStringProperty(reaction.getName());
		reactionId = new SimpleStringProperty(reaction.getId());
		reactionSBOTerm = new SimpleStringProperty(reaction.getSBOTermID());
		reactionCompartment = new SimpleStringProperty(reaction.getCompartment());
		//reactionKineticLaw = new SimpleStringProperty(reaction.getKineticLaw(), null);
	}

	private void setReaction(Reaction reaction) {
		this.reaction = reaction;
	}

	public Reaction getReaction() {
		return reaction;
	}

	public void setReactionName(StringProperty reactionName) {
		this.reactionName = reactionName;
	}

	public StringProperty getReactionName() {
		return reactionName;
	}

	public void setReactionId(StringProperty reactionId) {
		this.reactionId = reactionId;
	}

	public StringProperty getReactionId() {
		return reactionId;
	}

	public void setSBOTerm(StringProperty reactionSBOTerm) {
		this.reactionSBOTerm = reactionSBOTerm;
	}

	public StringProperty getSBOTerm() {
		return reactionSBOTerm;
	}

	public void setCompartment(StringProperty reactionCompartment) {
		this.reactionCompartment = reactionCompartment;
	}

	public StringProperty getCompartment() {
		return reactionCompartment;
	}
	
//	public void setKineticLaw(StringProperty reactionKineticLaw) {
//		this.reactionKineticLaw = reactionKineticLaw;
//	}
//
//	public StringProperty getKineticLaw() {
//		return reactionKineticLaw;
//	}
}
