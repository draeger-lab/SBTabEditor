package de.sbmltab.view;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.control.MenuBar;

import javafx.scene.layout.BorderPane;

public class SBMLTabMainView extends Application {
	

	public void start(Stage stage) throws Exception {

        MenuBar menuBar = MenuController.generateMenuBar();
        BorderPane root = new BorderPane();
        root.setTop(menuBar);
		//Parent root = FXMLLoader.load(getClass().getResource("TabModView.fxml"));
	    root.setTop(menuBar);
	    root.setCenter(FXMLLoader.load(getClass().getResource("TabModView.fxml")));
        Scene scene = new Scene(root, 800, 600);
        
        stage.getIcons().add (new Image("File:/Icons/Icon_small.png"));
        stage.setTitle("TabMod v1.0");
        stage.setScene(scene);
        stage.show();
		
	
	}


}
