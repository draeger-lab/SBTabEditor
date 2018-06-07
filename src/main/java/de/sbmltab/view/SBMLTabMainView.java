package de.sbmltab.view;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
 
public class SBMLTabMainView extends Application {
 
	// TODO: Remove example code
    public static void main(String[] args) {
        launch(args);
    }
 
    @Override
    public void start(Stage stage) {
    	// Here is an example how you can create simple window with jfx
        Scene scene = new Scene(new Group());
        stage.setTitle("SBMLTab v.0.1");
        stage.setWidth(1024);
        stage.setHeight(768);
        stage.setScene(scene);
        stage.show();
    }
}
