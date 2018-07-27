package de.sbtab.controller;

/**
 * Defines wrapper element for JSBML data node
 * */
public interface SBTabElement{
	/**
	 * Used to rearrange data to corresponding observable values in wrapper
	 * */
	void initialize();
	
	/**
	 * Used to save data to corresponding {@code JSBML} Element
	 * */
	void savaData();
}
