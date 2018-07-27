package de.sbtab.containers;

import org.sbml.jsbml.Parameter;

import de.sbtab.controller.SBTabElement;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SBTabParameterWrapper implements SBTabElement {
	private Parameter parameter;
	private StringProperty parameterName;
	private StringProperty parameterId;
	private StringProperty parameterSBOTerm;
	
	public SBTabParameterWrapper(Parameter parameter) {
		if (parameter != null) {
			setParameter(parameter);
			initialize();
		}
	}

	public void initialize() {
		// TODO: figure out what fields do we need to work with
		parameterName = new SimpleStringProperty(parameter.getName());		
		parameterId = new SimpleStringProperty(parameter.getId());
		parameterSBOTerm = new SimpleStringProperty(parameter.getSBOTermID());
	}

	@Override
	public void savaData() {
		parameter.setName("");
	}

	public Parameter getParameter() {
		return parameter;
	}

	public void setParameter(Parameter parameter) {
		this.parameter = parameter;
	}

	public StringProperty getParameterName() {
		return parameterName;
	}

	public void setParameterName(StringProperty ParameterName) {
		this.parameterName = ParameterName;
	}

	public StringProperty getParameterId() {
		return parameterId;
	}

	public void setParameterId(StringProperty parameterId) {
		this.parameterId = parameterId;
	}

	public StringProperty getParameterSBOTerm() {
		return parameterSBOTerm;
	}

	public void setParameterSBOTerm(StringProperty parameterSBOTerm) {
		this.parameterSBOTerm = parameterSBOTerm;
	}
}
