package de.sbtab.containers;

import org.sbml.jsbml.util.filters.Filter;
import org.sbml.jsbml.SBO;
import org.sbml.jsbml.SBase;

public class SBTabSBOTermFilter implements Filter{
	
	private int sboTerm;
	
	public SBTabSBOTermFilter(int sboterm){
		this.sboTerm = sboterm;
	}
	
	@Override
	public boolean accepts(Object o) {
		//if (sboTerm instanceof SBase)
		if(o instanceof SBase){
			return SBO.isChildOf(sboTerm, SBO.getCatalyst());
		}
		return false;
	}
}
