package de.sbtab.view;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import org.controlsfx.control.StatusBar;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.util.ResourceManager;

import de.sbtab.controller.SBTabController;
import de.sbtab.controller.SBTabDocument;
import de.sbtab.services.SBTabTableProducer;
import de.sbtab.utils.SBTabTableHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Method that extends javafx Application and can be ran calling launch().
 * Can also be constructed and be started on a new stage using start().
 * Contains Methods modifying the stage and loading aspects of the GUI.
 * 
 *
 */
public class SBTabMainView extends Application {
	
	private static final double DEFAULT_WIDTH = 800;
	private static final double DEFAULT_HEIGHT = 600;
	private static final String WINDOW_WIDTH = "Window_Width";
	private static final String WINDOW_HEIGHT = "Window_Height";
	private static final String THE_PROJECT_NAME = "TabMod";
	private static final String THE_VERSION = "1.6";
	
	
	private SBTabController controller = new SBTabController();
	private SBTabDocument<SBMLDocument> doc;
	private FXMLLoader menuLoader = new FXMLLoader();
	private FXMLLoader treeLoader = new FXMLLoader();
	private BorderPane root = new BorderPane();
	private SBTabTableProducer tableProducer;
	private ResourceBundle bundle = ResourceManager.getBundle("de.sbtab.view.SBTabTreeElementNames");
	private SBTabTableHandler tableHandler;
	private Stage thisStage;

	public SBTabMainView() {
	}

	@Override
	public void start(Stage stage) throws Exception {
		thisStage = stage;
		if (isDocumentLoaded()) {
			setViewOnFile();
		} 
		else {	
			List<String> Parameters = this.getParameters().getRaw();
			
			if (!Parameters.isEmpty()) {
				doc = controller.read(Parameters.get(0));
				
			    if (isDocumentLoaded()) {
				setViewOnFile();
			    }
			    else {
				System.out.println("invalid command, please enter a path to a valid .xml or .gz file next time!");
				setViewDefault();
			    }
			}
			else {
				setViewDefault();	
			}
		}
		
		Scene scene = new Scene(root, DEFAULT_WIDTH, DEFAULT_HEIGHT);

		// Add icons from resources to the Icon-List of this stage.
		stage.getIcons().add(new Image(getClass().getResourceAsStream("Icon_32.png")));
		stage.getIcons().add(new Image(getClass().getResourceAsStream("Icon_16.png")));
		stage.getIcons().add(new Image(getClass().getResourceAsStream("Icon_256.png")));
		stage.getIcons().add(new Image(getClass().getResourceAsStream("Icon_48.ico")));

		if (isDocumentLoaded()) {
		    stage.setTitle(THE_PROJECT_NAME + " " + THE_VERSION + " - " + doc.getFile().getName());
		}
		else {
			stage.setTitle(THE_PROJECT_NAME + " " + THE_VERSION);	
		}
		stage.setScene(scene);
		stage.sizeToScene();
		stage.show();
		saveWindowSize(stage);
	}

	private void saveWindowSize(Stage stage) {
		Preferences preferences = Preferences.userNodeForPackage(SBTabController.class);
        double width = preferences.getDouble(WINDOW_WIDTH, DEFAULT_WIDTH);
        double height = preferences.getDouble(WINDOW_HEIGHT, DEFAULT_HEIGHT);
        stage.setWidth(width);
        stage.setHeight(height);
		stage.setOnCloseRequest((final WindowEvent event) -> {
            preferences.putDouble(WINDOW_WIDTH, stage.getWidth());
            preferences.putDouble(WINDOW_HEIGHT, stage.getHeight());
        });
	}
	/**
	 * Loads aspects of the GUI that are necessary to view a file
	 * @throws Exception
	 */

	public void setViewOnFile() throws Exception {
		menuLoader.setLocation(getClass().getResource("SBTabMenu.fxml"));
		treeLoader.setLocation(getClass().getResource("SBTabTree.fxml"));
		menuLoader.setController(new SBTabMenuController(this, controller));
		treeLoader.setController(new SBTabTreeController(controller, tableHandler, this));
		treeLoader.setResources(bundle);
		MenuBar menuBar = (MenuBar) menuLoader.load();
		root.setTop(menuBar);
		reInit();
	}
	
	/**
	 * Loads default aspects of the GUI (locked menu bar)
	 * @throws Exception
	 */
	public void setViewDefault() throws Exception {
		menuLoader.setLocation(getClass().getResource("SBTabMenu.fxml"));
		menuLoader.setController(new SBTabMenuController(this, controller));
		MenuBar menuBar = (MenuBar) menuLoader.load();
		root.setTop(menuBar);
		assignStatusBar("No file specified.", 0D);

	}

	/**
	 * Loads missing aspects of the default view once a file was loaded.
	 */
	public void reInit() {
		if (doc != null) {
			tableProducer = new SBTabTableProducer(doc.getTempDoc());
			assignStatusBar("Ready.", 0D);
			try {
				treeLoader = new FXMLLoader();
				treeLoader.setLocation(getClass().getResource("SBTabTree.fxml"));
				tableHandler = new SBTabTableHandler(tableProducer, root);
				treeLoader.setController(new SBTabTreeController(controller, tableHandler, this));
				treeLoader.setResources(bundle);
				root.setLeft(treeLoader.load());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * Resets view to default.
	 * @param message Status Bar Message
	 */

	public void clearView(String message) {
		root.setLeft(null);
		root.setCenter(null);
		assignStatusBar(message, 0D);
	}

	public String getTheVersion() {
		return THE_VERSION;
	}

	public String getTheProjectName() {
		return THE_PROJECT_NAME;
	}
	/**
	 * Sets a new document constructed from a given SBMLDocument as the current document
	 * @param doc
	 */

	public void setDoc(SBMLDocument doc) {
		this.doc= new SBTabDocument<SBMLDocument>(doc,null);
		this.doc.setTempDoc(doc);
		this.controller.setDoc(doc);
	}
	/**
	 * Sets the current documents file property to a given file
	 * @param file
	 */
	public void setFile(File file) {
		this.doc.setFile(file);
	}
	
	/**
	 * Sets the current documents TempDoc property to a given document
	 * @param SBMLDocument
	 */
	public void setDocument (SBTabDocument<SBMLDocument> document) {
		doc= document;
		this.controller.setDocument(document);
	}
	/**
	 * Returns the TempDoc property of doc
	 */
	public SBMLDocument getDoc() {
		return doc.getTempDoc();
	}
	
	public SBTabTableProducer getTableProducer(){
		return tableProducer;
	}

	public void assignStatusBar(String message, Double progressState) {
		StatusBar sb = new StatusBar();
		sb.setText(message);
		sb.setProgress(progressState);
		this.root.setBottom(sb);
	}
	
	/**
	 * Information whether a file is currently loaded.
	 * 
	 * @return
	 */

	public boolean isDocumentLoaded() {
		return doc != null;
	}

	public SBTabController getController() {
		return controller;
	}

	public Stage getStage() {	
		return thisStage;
	}
	
	public BorderPane getRoot(){
		return root;
	}
	
	public SBTabDocument<SBMLDocument> getDocument() {
		return doc;
	}
	
	/**
	 * Updates the stage title to display file name.
	 */
	public void updateTitle() {
		if (isDocumentLoaded()) {
			if (doc.getFile()!=null) {
		    thisStage.setTitle(THE_PROJECT_NAME + " " + THE_VERSION + " - " + doc.getFile().getName());
		} else {
			thisStage.setTitle(THE_PROJECT_NAME + " " + THE_VERSION + " - " + "new file");
		}
		}
		else {
			thisStage.setTitle(THE_PROJECT_NAME + " " + THE_VERSION);	
		}
	}
}