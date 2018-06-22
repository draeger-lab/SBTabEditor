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

  @Override
  public void start(Stage stage) throws Exception {


    BorderPane root = new BorderPane();
    root.setTop(FXMLLoader.load(getClass().getResource("TabModMenu.fxml")));
    root.setCenter(FXMLLoader.load(getClass().getResource("TabModView.fxml")));
    root.setLeft(FXMLLoader.load(getClass().getResource("TabModTree.fxml")));

    Scene scene = new Scene(root, 640, 480);

    //Add icons from resources to the Icon-List of this stage.
    stage.getIcons().add(new Image(getClass().getResourceAsStream("Icon_32.png")));
    stage.getIcons().add(new Image(getClass().getResourceAsStream("Icon_16.png")));
    stage.getIcons().add(new Image(getClass().getResourceAsStream("Icon_256.png")));
    stage.getIcons().add(new Image(getClass().getResourceAsStream("Icon_48.ico")));

    stage.setTitle("TabMod 1.0");
    stage.setScene(scene);
    stage.sizeToScene();
    stage.show();

  }

}
