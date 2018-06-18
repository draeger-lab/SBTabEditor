package de.sbmltab.controller;

import java.io.File;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.sbml.jsbml.SBMLDocument;

public class Document {
	
	private static final Logger LOGGER = LogManager.getLogger(SBMLTabController.class);

	SBMLDocument doc; 
	
	File pathDoc;
	
	SBMLDocument tempDoc;
	
	File pathtempDoc;
	
	int count = 0;
	
	public void edit(String x) {
		changed = true;
		count++;
		
		try {
			int errorCount1 = this.tempDoc.checkConsistency();
			int errorCount2 = this.tempDoc.checkConsistencyOffline();
			
			if (count > 5) {
				SBMLTabController.save(this.doc, pathDoc, this.doc.getName(), 
						Integer.toString(this.doc.getVersion()));
			} else {
				SBMLTabController.save(this.tempDoc, pathtempDoc, this.tempDoc.getName(), 
						Integer.toString(this.tempDoc.getVersion()));
			}
			
		} catch (Exception e) {
			LOGGER.error("Unable to edit sbml file", e);
		}
	}	
	
	boolean changed = false; 
}
