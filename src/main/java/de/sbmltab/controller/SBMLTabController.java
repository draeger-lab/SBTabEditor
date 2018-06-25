package de.sbmltab.controller;

import java.io.File;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.TidySBMLWriter;

import javafx.concurrent.Task;

public class SBMLTabController {

  private static final transient Logger LOGGER = LogManager.getLogger(SBMLTabController.class);
  private static SBMLDocument doc;
  /**
   * Save SBML document to a {@link File}.
   * 
   * @param doc
   *            the {@link SBMLDocument} to be written
   * @param path
   *            absolute path to {@link SBMLDocument}
   * @param name
   *            name of {@link SBMLDocument}
   * @param version
   *            version of {@link SBMLDocument}
   * 
   */
  public static void save(SBMLDocument doc, File path, String name, String version) {
    Task<Void> task = new Task<Void>() {
      @Override
      public Void call() {
        try {
          TidySBMLWriter.write(doc, path, name, version);
        } catch (Exception e) {
          LOGGER.error("Unable to write sbml file", e);
        }
        return null;
      }
    };
    Thread th = new Thread(task);
    th.setDaemon(true);
    th.start();
  }


  /**
   * Read SBML document from a {@link File}.
   * 
   * @param path
   *            absolute path to {@link SBMLDocument}
   * @return 
   */
  public static SBMLDocument read(String filePath) {
    Task<Void> task = new Task<Void>() {
      @Override
      public Void call() {
        try {
          doc = SBMLReader.read(new File(filePath));
        } catch (Exception e) {
          e.printStackTrace();
        }
        return null;
      }
    };
    Thread th = new Thread(task);
    th.setDaemon(true);
    th.start();
    try {
    	th.join();
    } catch (InterruptedException e) {
    	e.printStackTrace();
    }
    return doc;
  }
  public static boolean validate(SBMLDocument doc) {
    if(doc.checkConsistencyOffline() == 0) {
      return true;
    }else {
      for(int i = 0; i < doc.checkConsistencyOffline(); i++) {
        LOGGER.error(doc.getError(i));
      }
      return false;
    }
  }
}
