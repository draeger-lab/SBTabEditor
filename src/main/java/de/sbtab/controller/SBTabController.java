package de.sbtab.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Objects;
import java.util.Properties;
import java.util.zip.GZIPInputStream;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.TidySBMLWriter;

import javafx.concurrent.Task;


public class SBTabController {

  private static final transient Logger LOGGER = LogManager.getLogger(SBTabController.class);
  private SBMLDocument doc;
  private String filePath = null;

  public String getFilePath() {
    return filePath;
  }

  public SBMLDocument getDoc() {
    return doc;
  }

  /**
   * Set Properties for the program, at the moment only the file path is saved.
   */
  public void setProperties(){
    Writer writer = null;
    try
    {
      writer = new FileWriter( ".properties" );
      Properties theProperties = new Properties( System.getProperties() );
      if(filePath != null){
        theProperties.setProperty( "FilePath", new File(filePath).getParent());
      }
      else{
        theProperties.setProperty( "FilePath", System.getProperty("user.home"));
      }
      theProperties.store( writer, "Properties of STabEditor" );
    }
    catch ( IOException e )
    {
      e.printStackTrace();
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
  public void save(SBMLDocument doc, File path, String name, String version) {
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
	public SBMLDocument read(String filePath) {
		this.filePath = filePath;
		File theSBMLFile = new File(filePath);
		boolean isFile = theSBMLFile.isFile();
		System.out.println(getFileExtension(theSBMLFile));
		if (Objects.equals(getFileExtension(theSBMLFile), ".xml")) {
			try {
				if (isFile) {
					doc = SBMLReader.read(theSBMLFile);
				}
				if (Objects.equals(getFileExtension(theSBMLFile), ".gz")) {
					doc = SBMLReader.read(new GZIPInputStream(new FileInputStream(filePath)));
				}
				setProperties();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return doc;
	  }
  /**
   * Validator of SBML-Files
   * @param doc is the input SBML-File
   * @return boolean true for valid Document else false plus Logger errors
   */
  public boolean validate(SBMLDocument doc) {
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
  public String errorToString(SBMLDocument doc) {
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
  private String getFileExtension(File file) {
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
