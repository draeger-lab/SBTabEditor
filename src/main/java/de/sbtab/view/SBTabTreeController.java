package de.sbtab.view;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.SBase;

import de.sbtab.controller.SBTabController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class SBTabTreeController implements Initializable {

	@FXML
	private TreeView<String> treeView;
	
	@FXML
	private Button expandButton;
	
	public Boolean expanded = false;
	
	@FXML
	void doExpandTree(ActionEvent event) {
		expanded ^= true;
		ExpandTree2();
	}

	@Override
	public void initialize(URL location, ResourceBundle value) {
		SBase document = SBTabController.getDoc();
		TreeNode root = document.getRoot();
		TreeItem<String> root2 = new TreeItem<String>(String.valueOf(root));
		treeView.setRoot(root2);

		tree(root, document, root2, false);
	}

	public void tree(TreeNode root, SBase document, TreeItem<String> root2, Boolean expand) {
		try {
			if (root.getChildCount() > 0) {
				for (int i = 0; i < root.getChildCount(); i++) {
					TreeNode node = root.getChildAt(i);
					TreeItem<String> node2 = new TreeItem<String>(String.valueOf(node));
					root2.setExpanded(expand);
					root2.getChildren().add(node2);
					tree(node, document, node2, expand);
				}
			}

		} catch (Exception e) {
			System.out.println(e);
		}

	}
	
	public void ExpandTree2(){
		SBase document = SBTabController.getDoc();
		TreeNode root = document.getRoot();
		TreeItem<String> root2 = new TreeItem<String>(String.valueOf(root));
		root2.setExpanded(expanded);
		treeView.setRoot(root2);

		tree(root, document, root2, expanded);
	}
}
