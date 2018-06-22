package de.sbmltab.controller;

import java.io.File;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.TidySBMLWriter;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class SBMLTabController {

  private static final transient Logger LOGGER = LogManager.getLogger(SBMLTabController.class);

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
    try {
      TidySBMLWriter.write(doc, path, name, version);
    } catch (Exception e) {
      LOGGER.error("Unable to write sbml file", e);
    }
  }

  /*
   * @author Julian Wanner
   * Returns absolute Path of a chosen file with JavaFX.
   */
  public static String open(Stage stage) {
    final FileChooser fileChooser = new FileChooser();
    String filePath = "";
    fileChooser.getExtensionFilters().addAll(
      new ExtensionFilter("XML Files", "*.xml"),
      new ExtensionFilter("SBML Files", "*.SBML"));
    fileChooser.setTitle("Choose SBML or XML File.");
    fileChooser.setInitialDirectory(new File(System.getProperty("user.home"))) ;
    File file = fileChooser.showOpenDialog(stage);
    if (file != null) {
      filePath = file.getAbsolutePath();
    }
    return filePath;
  }

  /**
   * Read SBML document from a {@link File}.
   * 
   * @param path
   *            absolute path to {@link SBMLDocument}
   */
  public static SBMLDocument read(String filePath) {
    SBMLDocument doc = null;
    try {
      doc = SBMLReader.read(filePath);
    } catch (Exception e) {
      LOGGER.error("Unable to read sbml file", e);
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
