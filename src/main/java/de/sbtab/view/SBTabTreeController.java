package de.sbtab.view;

import java.net.URL;
import java.util.ResourceBundle;
import javax.swing.tree.TreeNode;
import org.sbml.jsbml.SBase;
import de.sbtab.controller.SBTabController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class SBTabTreeController implements Initializable {

	@FXML
	private TreeView<String> treeView;

	public void recursiveTree(SBase tree) {

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		SBase document = SBTabController.getDoc();
		TreeNode root = document.getRoot();
		TreeItem<String> root2 = new TreeItem<String>(String.valueOf(root));
		treeView.setRoot(root2);
			
		tree(root, document, root2);		
	}
	
	private void tree(TreeNode root, SBase document, TreeItem<String> root2) {
		try {
			if(root.getChildCount() > 0) {				
				for (int i = 0; i < root.getChildCount(); i++) {
					TreeNode x = root.getChildAt(i);
					TreeItem<String> x2 = new TreeItem<String>(String.valueOf(x));
					root2.getChildren().add(x2);
					tree(x, document, x2);
				}
			}
			
//			for (int i = 0; i < document.getChildCount(); i++) {
//				Enumeration<TreeNode> children = document.getChildAt(i).children();
//				while (children.hasMoreElements()) {
//					TreeItem<String> node = new TreeItem<String>(String.valueOf(children.nextElement()));
//					root.getChildren().add(node);
//				}
//			}

		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
}
