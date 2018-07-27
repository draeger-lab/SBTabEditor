package de.sbtab.containers;

import org.sbml.jsbml.KineticLaw;
import org.sbml.jsbml.Reaction;

import de.sbtab.controller.SBTabElement;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SBTabReactionWrapper implements SBTabElement {
	private Reaction reaction;
	private StringProperty reactionName;
	private StringProperty reactionId;
	private StringProperty reactionSBOTerm;

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
	}

	@Override
	public void savaData() {
		reaction.setName(reactionName.getValueSafe());
		reaction.setId(reactionId.getValueSafe());
		reaction.setSBOTerm(reactionSBOTerm.getValueSafe());
	}
	
	public void saveData() {
		reaction.setName(reactionName.getValueSafe());
		reaction.setId(reactionId.getValueSafe());
		reaction.setSBOTerm(reactionSBOTerm.getValueSafe());
	}

	private void setReaction(Reaction reaction) {
		this.reaction = reaction;
	}

	public Reaction getReaction() {
		return reaction;
	}

	public void setReactionName(StringProperty reactionName) {
		this.reactionName = reactionName;
		saveData();
	}

	public StringProperty getReactionName() {
		return reactionName;
	}

	public void setReactionId(StringProperty reactionId) {
		this.reactionId = reactionId;
		saveData();
	}

	public StringProperty getReactionId() {
		return reactionId;
	}

	public void setSBOTerm(StringProperty reactionSBOTerm) {
		this.reactionSBOTerm = reactionSBOTerm;
		saveData();
	}

	public StringProperty getSBOTerm() {
		return reactionSBOTerm;
	}
}
