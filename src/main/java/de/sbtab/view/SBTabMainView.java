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

public class SBTabMainView extends Application {
	
	private static final double DEFAULT_WIDTH = 800;
	private static final double DEFAULT_HEIGHT = 600;
	private static final String WINDOW_WIDTH = "Window_Width";
	private static final String WINDOW_HEIGHT = "Window_Height";
	private static final String THE_PROJECT_NAME = "TabMod";
	private static final String THE_VERSION = "1.4";
	
	
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

	public void setViewDefault() throws Exception {
		menuLoader.setLocation(getClass().getResource("SBTabMenu.fxml"));
		menuLoader.setController(new SBTabMenuController(this, controller));
		MenuBar menuBar = (MenuBar) menuLoader.load();
		root.setTop(menuBar);
		assignStatusBar("No file specified.", 0D);

	}

	// TODO: to be removed after redundant static field are eliminated and
	// concurrency in
	// handleOpen() fixed
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

	public void setDoc(SBMLDocument doc) {
		this.doc= new SBTabDocument<SBMLDocument>(doc,null);
		this.doc.setTempDoc(doc);
		this.controller.setDoc(doc);
	}
	
	public void setFile(File file) {
		this.doc.setFile(file);
	}
	
	public void setDocument (SBTabDocument<SBMLDocument> document) {
		doc= document;
		this.controller.setDocument(document);
	}

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

	public SBTabDocument<SBMLDocument> getDocument() {
		return doc;
	}
}