package de.sbtab.view;

import java.io.IOException;

import org.sbml.jsbml.SBMLDocument;

import de.sbtab.services.SBTabReactionTable;
import de.sbtab.services.SBTabTableProducer;
import de.sbtab.services.TableType;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;

public class SBTabMainView extends Application implements Runnable{
	
	public SBMLDocument doc;
	private static BorderPane root = new BorderPane();
	private static SBTabTableProducer tableProducer;
	private static final String THE_PROJECT_NAME = "TabMod";
	private static final String THE_VERSION = "1.1"; 
	public boolean fileLoaded;// relevant information if a file is loaded or not.
	
	@Override
    public void run() {
        launch();
    }
	
	public SBTabMainView (SBMLDocument openDoc){
		if (openDoc!=null){
			this.doc=openDoc;
			this.fileLoaded=true;
		}
		else{ this.fileLoaded=false;
		}
	}
	
	public SBTabMainView (){
		this.fileLoaded=false;
	}

	@Override
	public void start(Stage stage) throws Exception {
		if (fileLoaded) {
		root.setTop(FXMLLoader.load(getClass().getResource("SBTabMenu.fxml")));
		root.setLeft(FXMLLoader.load(getClass().getResource("SBTabTree.fxml")));
		}
		else{
			root.setTop(FXMLLoader.load(getClass().getResource("SBTabMenu.fxml")));	
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
	
	
	// TODO: to be removed after redundant static field are eliminated and concurrency in 
	//       handleOpen() fixed
	public void reInit() {
		tableProducer = new SBTabTableProducer(doc);
		root.setCenter(tableProducer.getTableView(TableType.REACTION));
		try {
			root.setLeft(FXMLLoader.load(getClass().getResource("SBTabTree.fxml")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

  public static String getTheVersion() {
    return THE_VERSION;
  }

  public static String getTheProjectName() {
    return THE_PROJECT_NAME;
  }
}