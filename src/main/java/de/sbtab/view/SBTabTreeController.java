package de.sbtab.view;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.SBase;
import org.sbml.jsbml.util.ResourceManager;

import de.sbtab.controller.SBTabController;
import javafx.application.Platform;
import javafx.concurrent.Task;
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
	
	private ResourceBundle bundle;
	
	public Boolean expanded = false;
	
	SBTabMainView mainView;
	
	public SBTabTreeController(SBTabMainView mainView) {
		this.mainView = mainView;
	} 
	
	@FXML
	void doExpandTree(ActionEvent event) {		
		 Task<Void> task = new Task<Void>() {
		     @Override protected Void call() throws Exception {	         
		             Platform.runLater(new Runnable() {
		                 @Override public void run() {
		                	 expanded ^= true;
		                	 ExpandTree2();
		                 }
		             });		         
		         return null;
		     }
		 };
		
		Thread th = new Thread(task);
		th.setDaemon(true);
		th.start();	
	}

	@Override
	public void initialize(URL location, ResourceBundle value) {
		bundle = ResourceManager.getBundle("de.sbtab.view.SBTabTreeElementNames");
		SBase document = SBTabController.getDoc();
		TreeNode root = document.getRoot();
		TreeItem<String> rootAsItem = new TreeItem<String>(String.valueOf(root));
		treeView.setRoot(rootAsItem);

		tree(root, document, rootAsItem);
	}

	/**
	 * recursive tree (depends on variable expanded)
	 * 
	 * @param root
	 * @param document
	 * @param rootAsItem
	 */
	private void tree(TreeNode root, SBase document, TreeItem<String> rootAsItem) {
		try {
			
			if (root.getChildCount() > 0) {
				
				for (int i = 0; i < root.getChildCount(); i++) {
					TreeNode node = root.getChildAt(i);
					TreeItem<String> nodeAsItem = new TreeItem<String>(readableName(node.toString()));		
					rootAsItem.setExpanded(expanded);
					rootAsItem.getChildren().add(nodeAsItem);
					tree(node, document, nodeAsItem);
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * returns a readable name for a tree element
	 * 
	 * @param name
	 * @return treeElementName
	 */
	private String readableName(String name){
		String treeElementName = name;
		
		if(bundle.containsKey(name)){
			treeElementName = bundle.getString(name);
		} else if(name.contains(" ")) {
			String names[] = name.split(" ");
			name = names[0];
			if(bundle.containsKey(name)){
				treeElementName = bundle.getString(name);
			} 
		} 
		return treeElementName;
	}
	
	/**
	 * expands tree recursive with help of tree method
	 * depends on variable expanded
	 */
	private void ExpandTree2(){
		SBase document = SBTabController.getDoc();
		TreeNode root = document.getRoot();
		TreeItem<String> root2 = new TreeItem<String>(String.valueOf(root));
		root2.setExpanded(expanded);
		treeView.setRoot(root2);

		tree(root, document, root2);
	}
}
