package de.sbmltab.view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;


public class TabModViewController implements Initializable {
	@FXML 
	private TreeView<String> treeView;





@Override
public void initialize(URL arg0, ResourceBundle arg1) {
	// TODO Auto-generated method stub
	TreeItem<String> root = new TreeItem<String>("Root Node");
    root.setExpanded(true);
    
    TreeItem<String> NodeA = new TreeItem<String>("NodeA");
    TreeItem<String> NodeB = new TreeItem<String>("NodeB");
    TreeItem<String> NodeC = new TreeItem<String>("NodeC");
    root.getChildren().add(NodeA);
    root.getChildren().add(NodeB);
    root.getChildren().add(NodeC);
    treeView.setRoot(root);
}
}