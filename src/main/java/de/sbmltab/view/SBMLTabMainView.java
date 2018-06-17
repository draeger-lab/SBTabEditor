package de.sbmltab.view;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;


public class SBMLTabMainView extends Application{
	

	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("TabModView.fxml"));
	    
        Scene scene = new Scene(root, 800, 600);
        stage.getIcons().add (new Image("File:/Icons/Icon_small.png"));
        stage.setTitle("TabMod v1.0");
        stage.setScene(scene);
        stage.show();
		
	
	}

}
