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

	
//	public String getResource(ResourceBundle resources, String value) {
//		resources = ResourceBundle.getBundle("");
//		return resources.getString(value);
//		}
		

	@Override
	public void initialize(URL location, ResourceBundle value) {
		SBase document = SBTabController.getDoc();
		TreeNode root = document.getRoot();
		TreeItem<String> root2 = new TreeItem<String>(String.valueOf(root));
		treeView.setRoot(root2);

		tree(root, document, root2);
	}

	private void tree(TreeNode root, SBase document, TreeItem<String> root2) {
		try {
			if (root.getChildCount() > 0) {
				for (int i = 0; i < root.getChildCount(); i++) {
					TreeNode node = root.getChildAt(i);
					TreeItem<String> node2 = new TreeItem<String>(String.valueOf(node));
					root2.getChildren().add(node2);
					tree(node, document, node2);
				}
			}

			// for (int i = 0; i < document.getChildCount(); i++) {
			// Enumeration<TreeNode> children = document.getChildAt(i).children();
			// while (children.hasMoreElements()) {
			// TreeItem<String> node = new TreeItem<String>(String.valueOf(children.nextElement()));
			// root.getChildren().add(node);
			// }
			// }

		} catch (Exception e) {
			System.out.println(e);
		}

	}
}
