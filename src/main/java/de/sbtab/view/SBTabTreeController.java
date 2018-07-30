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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class SBTabTreeController implements Initializable {
	private SBTabController controller;
	private SBTabMainView mainView;
	private SBTabTableHandler handler;
	
	/**
	 * Constructor for SBTabTreeController
	 * 
	 * @param controller
	 * @param handler
	 * @param mainView
	 */
	public SBTabTreeController(SBTabController controller, SBTabTableHandler handler, SBTabMainView mainView) {
		this.controller = controller;
		this.handler = handler;
		this.mainView = mainView;
	}

	@FXML
	private TreeView<String> treeView;

	@FXML
	private Button expandButton;

	@FXML
	private VBox treeVBox;

	private ResourceBundle bundle;

	public Boolean expanded = false;

	/**
	 * initializes tree 
	 */
	@Override
	public void initialize(URL location, ResourceBundle value) {
		bundle = ResourceManager.getBundle("de.sbtab.view.SBTabTreeElementNames");
		SBase document = controller.getDoc();
		TreeNode root = document.getRoot();
		TreeItem<String> rootAsItem = new TreeItem<String>(String.valueOf(root));
		treeView.setRoot(rootAsItem);
		tree(root, document, rootAsItem);
		treeView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> handleTreeLabelSelected(event));

		treeVBox.setPadding(new Insets(5));
		treeVBox.setFillWidth(true);
		
		treeVBox.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
			double x = treeVBox.getPrefWidth();
			
			@Override
			public void handle(MouseEvent t) {
				double change = t.getX();
				double newWidth = treeVBox.getPrefWidth() + change - x;
				if (newWidth < mainView.getRoot().getWidth()) {
					treeVBox.setPrefWidth(newWidth);
					x = change;
				}
				
			}
		});
	}

	/**
	 * Fires further event on tree node label select
	 * 
	 * @author zakharc
	 */
	private void handleTreeLabelSelected(MouseEvent event) {
		Node node = event.getPickResult().getIntersectedNode();
		if (node instanceof Text && ((Text) node).getText() != null) {
			String name = (String) ((TreeItem<String>) treeView.getSelectionModel().getSelectedItem()).getValue();
			TableType tableType = getEnumFromString(TableType.class, name);
			handler.createTable(tableType);
		}
	}

	/**
	 * A common method for all enums since they can't have another base class
	 * 
	 * @param <T>
	 *            Enum type
	 * @param c
	 *            enum type. All enums must be all caps.
	 * @param string
	 *            case insensitive
	 * @return corresponding enum, or null
	 * @author zakharc
	 */
	public static <T extends Enum<T>> T getEnumFromString(Class<T> c, String string) {
		if (c != null && string != null) {
			try {
				return Enum.valueOf(c, string.trim().replaceAll(" ", "_").toUpperCase());
			} catch (IllegalArgumentException ex) {
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
					if (readableName(node.toString()) != null) {
						TreeItem<String> nodeAsItem = new TreeItem<String>(readableName(node.toString()));
						rootAsItem.setExpanded(expanded);
						rootAsItem.getChildren().add(nodeAsItem);
						tree(node, document, nodeAsItem);
					}
					if (rootAsItem.toString().equals("TreeItem [ value: Unit definitions ]")) {
						TreeItem<String> nodeAsItem = new TreeItem<String>(node.toString());
						rootAsItem.setExpanded(expanded);
						rootAsItem.getChildren().add(nodeAsItem);
						tree(node, document, nodeAsItem);
					}
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
	private String readableName(String name) {
		String treeElementName = null;

		if (bundle.containsKey(name)) {
			treeElementName = bundle.getString(name);
		} else if (name.contains(" ")) {
			String names[] = name.split(" ");
			if (bundle.containsKey(names[0])) {
				treeElementName = bundle.getString(names[0]);

				// check if for example name= is specified and split String where name is
				if (name.contains("name=\"")) {
					treeElementName += cutString("name=\"", name);
				} else if (name.contains("id=\"")) {
					treeElementName += cutString("id=\"", name);
				} else if (name.contains("idRef=\"")) {
					treeElementName += cutString("idRef=\"", name);
				} else if (name.contains("species=\"")) {
					treeElementName += cutString("species=\"", name);
				}
			}
		}
		return treeElementName;

	}

	/**
	 * cuts name at position and "
	 * 
	 * @param position string, where name will be cut
	 * @param name
	 * @return rightName
	 */
	private String cutString(String position, String name) {
		String firstCut[] = name.split(position);
		String secondCut[] = firstCut[1].split("\"");
		String rightName = " " + secondCut[0];
		return rightName;
	}
	
	/**
	 * expands tree at ActionEvent, calls {@link SBTabTreeController#expandTree2()} and sets boolean expanded on opposite
	 * 
	 * @param event clicking on expand-button
	 */
	@FXML
	void doExpandTree(ActionEvent event) {
		Task<Void> task = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						expanded ^= true;
						expandTree2();
					}
				});
				return null;
			}
		};

		Thread th = new Thread(task);
		th.setDaemon(true);
		th.start();
	}

	/**
	 * expands tree recursive with help of tree method depends on variable
	 * expanded
	 */
	private void expandTree2() {
		SBase document = controller.getDoc();
		TreeNode root = document.getRoot();
		TreeItem<String> root2 = new TreeItem<String>(String.valueOf(root));
		root2.setExpanded(expanded);
		treeView.setRoot(root2);

		tree(root, document, root2);
	}
}
