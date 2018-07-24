package de.sbtab.containers;

import org.sbml.jsbml.util.filters.Filter;

import de.sbtab.controller.SBTabController;

import org.sbml.jsbml.SBO;
import org.sbml.jsbml.SBase;

public class SBTabSBOTermFilter implements Filter{
	
//	SBTabController controller;
//	SBase sbase = controller.getDoc();
	private int sboTerm;
	
	
	public SBTabSBOTermFilter(int sboterm){
		this.sboTerm = sboterm;
	}
	
	@Override
	public boolean accepts(Object o) {
		//if(sbase.getSBOTermID() != null){
			return SBO.isChildOf(sboTerm, SBO.getCatalyst());
		//}
		//return false;
	}
}
