package de.sbmltab.controller;

import org.sbml.jsbml.Reaction;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ReactionWrapper {
	private Reaction reaction;
    private StringProperty reactionName;
    private StringProperty reactionReactants;
    private StringProperty reactionProducts;
    private StringProperty reactionModifiers;
    private IntegerProperty reactionId;

    public ReactionWrapper(Reaction reaction) {
    	setReaction(reaction);
    	initialize(reaction);
    }
  
    public void initialize(Reaction reaction) {
    	reactionName = new SimpleStringProperty(reaction.getName());
    	reactionReactants = new SimpleStringProperty(reaction.getListOfReactants().toString());
    	reactionProducts = new SimpleStringProperty(reaction.getListOfProducts().toString());
    	reactionModifiers = new SimpleStringProperty(reaction.getListOfModifiers().toString());
    	reactionId = new SimpleIntegerProperty(Integer.parseInt(reaction.getId()));
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

	public StringProperty getReactionReactants() {
		return reactionReactants;
	}

	public StringProperty getReactionProducts() {
		return reactionProducts;
	}

	public StringProperty getReactionModifiers() {
		return reactionModifiers;
	}

	public IntegerProperty getReactionId() {
		return reactionId;
	}
}
