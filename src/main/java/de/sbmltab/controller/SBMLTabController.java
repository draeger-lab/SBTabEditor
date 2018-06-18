package de.sbmltab.controller;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.SBMLWriter;

public class SBMLTabController {

	private static final Logger LOGGER = LogManager.getLogger(SBMLTabController.class);

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
			SBMLWriter.write(doc, path, name, version);
		} catch (Exception e) {
			LOGGER.error("Unable to write sbml file", e);
		}
	}

	/**
	 * Read SBML document from a {@link File}.
	 * 
	 * @param path
	 *            absolute path to {@link SBMLDocument}
	 */
	public static String open() {
		String filePath = "";
		FileNameExtensionFilter xmlFilter = new FileNameExtensionFilter("XML File","xml");
		FileNameExtensionFilter sbmlFilter = new FileNameExtensionFilter("SBML File","SBML");
		final JFileChooser fileChooser = new JFileChooser();
		fileChooser.addChoosableFileFilter(xmlFilter);
		fileChooser.addChoosableFileFilter(sbmlFilter);
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
        	filePath = fileChooser.getSelectedFile().getPath();
        }
        return filePath;
	}	
	public static SBMLDocument read(File path) {
		SBMLDocument doc = null;
		try {
			doc = SBMLReader.read(path);
		} catch (Exception e) {
			LOGGER.error("Unable to read sbml file", e);
		}
		return doc;
	}
}
