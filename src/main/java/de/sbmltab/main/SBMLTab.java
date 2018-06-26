package de.sbmltab.main;

import java.io.File;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.SBase;

import de.sbmltab.controller.SBMLTabController;
import de.sbmltab.view.SBMLTabMainView;
import javafx.application.Application;

public class SBMLTab {

  private static final transient Logger LOGGER = LogManager.getLogger(SBMLTab.class);
  /**
   * args[0] path to SBML file
   * 
   */
  public static void main(String[] args) {
    Application.launch(SBMLTabMainView.class,args);

  }
}
