package de.sbmltab.view;

import java.net.URL;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.ResourceBundle;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBase;

import de.sbmltab.controller.SBMLTabController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class TreeController implements Initializable {

	@FXML
	private TreeView<String> treeView;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub {
		try {

			SBMLDocument document = MenuController.handleOpen();
			TreeItem<String> root = new TreeItem<String>("Root Node");
			root.setExpanded(true);
			// Enumeration<TreeNode> children = document.children() ;
			// System.out.println(children);
			Model mod = (Model) document.getChildAt(0);

			for (int i = 0; i < mod.getChildCount(); i++) {
				TreeItem<String> node = new TreeItem<String>(String.valueOf(mod.getChildAt(i)));
				root.getChildren().add(node);
			}
			
//			Enumeration<TreeNode> tn = mod.getChildAt(1).children();
//		    while(tn.hasMoreElements()){
//		    	System.out.println(tn.nextElement());
//		    }
			
			treeView.setRoot(root);
			

		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
