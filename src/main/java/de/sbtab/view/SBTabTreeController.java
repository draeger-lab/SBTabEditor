package de.sbtab.view;

import java.net.URL;
import java.util.Enumeration;
import java.util.ResourceBundle;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.AbstractTreeNode;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBase;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class SBTabTreeController implements Initializable {

	@FXML
	private TreeView<String> treeView;

	SBase document = SBTabMenuController.handleOpen();

	@Override
	public void initialize(URL url, ResourceBundle resourcebundle) {
		// TODO Auto-generated method stub {
		try {

			TreeItem<String> root = new TreeItem<String>(String.valueOf(document.getRoot()));
			root.setExpanded(true);
			
			for (int i = 0; i < document.getChildCount(); i++) {
				Enumeration<TreeNode> children = document.getChildAt(i).children();
				
				while (children.hasMoreElements()) {
					TreeItem<String> node = new TreeItem<String>(String.valueOf(children.nextElement()));
					root.getChildren().add(node);
				}
			}
			// Enumeration<TreeNode> tn = mod.getChildAt(1).children();
			// while(tn.hasMoreElements()){
			// System.out.println(tn.nextElement());
			// }

			treeView.setRoot(root);

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public void recursiveTree(SBase tree) {
		
		
	}
}
