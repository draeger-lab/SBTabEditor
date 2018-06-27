package de.sbtab.controller;

import org.sbml.jsbml.Reaction;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SBTabReactionWrapper {
	private Reaction reaction;
    private StringProperty reactionName;
    private StringProperty reactionId;
    private StringProperty reactionSBOTerm;

    public SBTabReactionWrapper(Reaction reaction) {
    	setReaction(reaction);
    	initialize(reaction);
    }
  
    public void initialize(Reaction reaction) {
    	// TODO: figure out what fields do we need to work with
    	reactionName = new SimpleStringProperty(reaction.getName());
    	reactionId = new SimpleStringProperty(reaction.getId());
    	reactionSBOTerm = new SimpleStringProperty(reaction.getSBOTermID());
    }

    private void setReaction(Reaction reaction) {
		this.reaction = reaction;
	}

	public Reaction getReaction() {
		return reaction;
	}

	public StringProperty getReactionName() {
		return reactionName;
	}

	public StringProperty getReactionId() {
		return reactionId;
	}

	public StringProperty getSBOTerm() {
		return reactionSBOTerm;
	}
}
