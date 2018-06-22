package de.sbmltab.controller;

import java.io.File;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.sbml.jsbml.SBMLDocument;

public class Document<T> {
	
	private static final transient Logger LOGGER = LogManager.getLogger(SBMLTabController.class);
	
	private T tempDoc;
	
	private File pathtempDoc;
	
	boolean changed = false; 
	
	/**
	 * Constructor for Document
	 *
	 * @param tempDoc
	 * 
	 * @param pathtempDoc
	 */
	public Document(T tempDoc, File pathtempDoc){
		this.tempDoc = tempDoc;
		this.pathtempDoc = pathtempDoc;
	}
	
	/**
	 * Edit a temporary document 
	 * 
	 * @param x
	 */
	public void edit(String x) {
		try {
			changed = true;
			
		} catch (Exception e) {
			LOGGER.error("Unable to edit sbml file", e);
		}
	}	
}
