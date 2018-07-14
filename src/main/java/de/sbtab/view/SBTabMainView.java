package de.sbtab.view;


import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.control.StatusBar;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.util.ResourceManager;

import de.sbtab.controller.SBTabController;
import de.sbtab.services.SBTabTableProducer;
import de.sbtab.services.TableType;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class SBTabMainView extends Application {
	
	private SBMLDocument doc;
	private SBTabController controller = new SBTabController();
	private FXMLLoader menuLoader = new FXMLLoader();
	private FXMLLoader treeLoader = new FXMLLoader();
	private BorderPane root = new BorderPane();
	private SBTabTableProducer tableProducer;
	private ResourceBundle bundle = ResourceManager.getBundle("de.sbtab.view.SBTabTreeElementNames");
	private static final String THE_PROJECT_NAME = "TabMod";
	private static final String THE_VERSION = "1.1"; 
	

	public SBTabMainView(){
	}

	@Override
	public void start(Stage stage) throws Exception {
		if (isDocumentLoaded()){
			setViewOnFile();
		}
		else {
			List<String> Parameters = this.getParameters().getRaw();
			if (!Parameters.isEmpty()) {
				doc = controller.read(Parameters.get(0));
			}
		if (isDocumentLoaded()){
		    setViewOnFile();
		}
		else {
			System.out.println("invalid command, please enter a path to a valid .xml or .gz file next time!");
			setViewDefault();
		}
		}

		
		Scene scene = new Scene(root, 640, 480);

		// Add icons from resources to the Icon-List of this stage.
		stage.getIcons().add(new Image(getClass().getResourceAsStream("Icon_32.png")));
		stage.getIcons().add(new Image(getClass().getResourceAsStream("Icon_16.png")));
		stage.getIcons().add(new Image(getClass().getResourceAsStream("Icon_256.png")));
		stage.getIcons().add(new Image(getClass().getResourceAsStream("Icon_48.ico")));

		stage.setTitle(THE_PROJECT_NAME +" " + THE_VERSION);
		stage.setScene(scene);
		stage.sizeToScene();
		stage.show();

	}
	
	public void setViewOnFile() throws Exception {
		menuLoader.setLocation(getClass().getResource("SBTabMenu.fxml"));
		treeLoader.setLocation(getClass().getResource("SBTabTree.fxml"));
        menuLoader.setController(new SBTabMenuController(this, controller));
        treeLoader.setController(new SBTabTreeController(controller));
        treeLoader.setResources(bundle);
        MenuBar menuBar = (MenuBar) menuLoader.load();
        Node treeView = (Node)treeLoader.load();
	    root.setTop(menuBar);
	    root.setLeft(treeView);
	    reInit();
	}
	
   public void setViewDefault() throws Exception {
	   menuLoader.setLocation(getClass().getResource("SBTabMenu.fxml"));
       menuLoader.setController(new SBTabMenuController(this, controller));
       MenuBar menuBar = (MenuBar) menuLoader.load();
	    root.setTop(menuBar);
	    assignStatusBar("No file specified.", 0D);
		
	}
	
	
	// TODO: to be removed after redundant static field are eliminated and concurrency in 
	//       handleOpen() fixed
	public void reInit() {
		if (doc!=null){
			tableProducer = new SBTabTableProducer(doc);
			assignStatusBar("Ready.", 0D);
			root.setCenter(tableProducer.getTableView(TableType.REACTION));
		try {
			treeLoader.setLocation(getClass().getResource("SBTabTree.fxml"));
			treeLoader.setController(new SBTabTreeController(controller));
			treeLoader.setResources(bundle);
			root.setLeft(treeLoader.load());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
	public void clearView(String message) {	
		root.setCenter(null);
        root.setLeft(null);
        assignStatusBar(message, 0D);
	}

	public String getTheVersion() {
		return THE_VERSION;
	}

	public String getTheProjectName() {
		return THE_PROJECT_NAME;
	}
	
	public void setDoc(SBMLDocument doc) {
		this.doc = doc;
	}
	public SBMLDocument getDoc() {
		return doc;
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
}