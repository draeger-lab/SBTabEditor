package de.sbmltab.main;

import java.io.File;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.SBase;

import de.sbmltab.controller.SBMLTabController;

public class SBMLTab {

	private static final transient Logger LOGGER = LogManager.getLogger(SBMLTab.class);
	/**
	 * args[0] path to SBML file
	 * 
	 */
	public static void main(String[] args) {
		// TODO: Remove example code
		try {
			// Get SBML element
			String filePath = "";
			filePath = SBMLTabController.open();
			SBase doc = SBMLReader.read(new File(filePath));
			// Here is an example how you can get a list of Reactions
			ListOf<Reaction> listOfReactions = doc.getModel().getListOfReactions();
			// And iterate over it
			for (Reaction reaction : listOfReactions) {
				System.out.println(reaction.getId() + " + " + reaction.getName() + " + " + reaction.getSBOTerm());
			}
			// Example how to use logger
	        LOGGER.debug("This will be printed on debug");
	        LOGGER.info("This will be printed on info");
	        LOGGER.warn("This will be printed on warn");
	        LOGGER.error("This will be printed on error");
	        LOGGER.fatal("This will be printed on fatal");
	       
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
