package de.sbmltab.main;

import java.io.File;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.SBase;

public class SBMLTab {
	/**
	 * args[0] path to SBML file
	 * 
	 */
	public static void main(String[] args) {
		// TODO: Remove example code
		try {
			// Get SBML element
			SBase doc = SBMLReader.read(new File("/home/zakharchuk/Dropbox/SS18/TP/e_coli_core.xml"));
			// Here is an example how you can get a list of Reactions
			ListOf<Reaction> listOfReactions = doc.getModel().getListOfReactions();
			// And iterate over it
			for (Reaction reaction : listOfReactions) {
				System.out.println(reaction.getId() + " + " + reaction.getName() + " + " + reaction.getSBOTerm());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
