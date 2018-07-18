package de.sbtab.view;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.SBase;
import org.sbml.jsbml.util.ResourceManager;

import de.sbtab.controller.SBTabController;
import de.sbtab.services.TableType;
import de.sbtab.utils.SBTabTableHandler;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class SBTabTreeController implements Initializable {
	private SBTabController controller;
	
	public SBTabTreeController(SBTabController controller) {
		this.controller = controller;
	}

	@FXML
	private TreeView<String> treeView;
	
	@FXML
	private Button expandButton;
	
	private ResourceBundle bundle;
	
	public Boolean expanded = false;
	
	private SBTabMainView mainView;
	
	private SBTabTableHandler handler;
	
	public SBTabTreeController(SBTabMainView mainView) {
		this.mainView = mainView;
	} 
	
	public SBTabTreeController(SBTabController controller, SBTabTableHandler handler) {
		this.controller = controller;
		this.handler = handler;
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
		SBase document = controller.getDoc();
		TreeNode root = document.getRoot();
		TreeItem<String> rootAsItem = new TreeItem<String>(String.valueOf(root));
		treeView.setRoot(rootAsItem);
		tree(root, document, rootAsItem);
		treeView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> handleTreeLabelSelected(event)); 
	}

	/**
	 * Fires further event on tree node label select
	 * @author zakharc
	 * */
	private void handleTreeLabelSelected(MouseEvent event) {
	    Node node = event.getPickResult().getIntersectedNode();
	    if (node instanceof Text && ((Text) node).getText() != null) {
	    	String name = (String) ((TreeItem<String>)treeView.getSelectionModel().getSelectedItem()).getValue();
	    	TableType tableType = getEnumFromString(TableType.class, name);
	    	handler.createTable(tableType);
	    }
	}
	
	/**
	 * A common method for all enums since they can't have another base class
	 * @param <T> Enum type
	 * @param c enum type. All enums must be all caps.
	 * @param string case insensitive
	 * @return corresponding enum, or null
	 * @author zakharc
	 */
	public static <T extends Enum<T>> T getEnumFromString(Class<T> c, String string) {
	    if( c != null && string != null ) {
	        try {
	            return Enum.valueOf(c, string.trim().replaceAll(" ", "_").toUpperCase());
	        } catch(IllegalArgumentException ex) {
	        	//
	        }
	    }
	    return null;
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
		SBase document = controller.getDoc();
		TreeNode root = document.getRoot();
		TreeItem<String> root2 = new TreeItem<String>(String.valueOf(root));
		root2.setExpanded(expanded);
		treeView.setRoot(root2);

		tree(root, document, root2);
	}
}
