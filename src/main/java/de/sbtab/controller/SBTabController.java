package de.sbtab.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.Objects;
import java.util.zip.GZIPInputStream;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.TidySBMLWriter;

import javafx.concurrent.Task;

import java.util.prefs.Preferences;

public class SBTabController {

  private static final transient Logger LOGGER = LogManager.getLogger(SBTabController.class);
  private static SBMLDocument doc;
  private static String filePath = null;
//declare my variable at the top of my Java class
private static Preferences prefs;


  public static String getFilePath() {
    return filePath;
  }

  public static SBMLDocument getDoc() {
    return doc;
  }

  /**
   * Set Preferences for the programm, at the moment only the file path is saved.
   */
  public static void setPreferences(){
  //create a Preferences instance (somewhere later in the code)
    prefs = Preferences.userNodeForPackage(SBTabController.class);    
    if(filePath != null){
      String folderPath = (new File(filePath)).getParent();
      prefs.put("last_output_dir", folderPath);    
      }
    else{
      prefs.put("last_output_dir", System.getProperty("user.home"));
    }
  }

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
   * @param absolute path to {@link SBMLDocument}
   * @return {@link SBMLDocument}
   */
  public static SBMLDocument read(String filePath) {
    Task<Void> task = new Task<Void>() {
      @Override
      public Void call() {
        try {
          SBTabController.filePath = filePath;
          File theSBMLFile = new File(filePath);
          boolean isFile = theSBMLFile.isFile();
          System.out.println(getFileExtension(theSBMLFile));
          if(isFile){
            if(Objects.equals(getFileExtension(theSBMLFile), ".xml")){
              doc = SBMLReader.read(theSBMLFile);
            }
            if(Objects.equals(getFileExtension(theSBMLFile), ".gz")){
              doc = SBMLReader.read(new GZIPInputStream(new FileInputStream(filePath)));
            }
            setPreferences();
          }
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
  /**
   * Validator of SBML-Files
   * @param doc is the input SBML-File
   * @return boolean true for valid Document else false plus Logger errors
   */
  public static boolean validate(SBMLDocument doc) {
    // the number of Errors of a SBMLFile
    int numErrors = doc.checkConsistencyOffline();
    if(numErrors == 0) {
      return true;
    }else {
      //get each error and show it
      for(int i = 0; i < numErrors; i++) {
        LOGGER.error(doc.getError(i));
      }
      return false;
    }
  }
  /**
   * Number of Errors in Document
   * @param doc
   * @return Number of Errors
   */
  public static int numErrors(SBMLDocument doc) {
    return doc.checkConsistencyOffline();
  }
  /**
   * Testmethod to get a String-Type Error Code
   * @param doc
   * @return String-Error
   */
  public static String errorToString(SBMLDocument doc) {
    int numErrors = doc.checkConsistencyOffline();
    String StringError = null;
    StringError = doc.getError(0).toString();
    return StringError;
  }



  /**
   * @author Julian Wanner
   * @param File
   * @return File Extension
   */
  private static String getFileExtension(File file) {
    String theFileExtension = "";
    try {
      if (file != null && file.exists()) {
        String theFileName = file.getName();
        theFileExtension = theFileName.substring(theFileName.lastIndexOf("."));
      }
    } catch (Exception e) {
      theFileExtension = "";
    }

    return theFileExtension;

  }
}
