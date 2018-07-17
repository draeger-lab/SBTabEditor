package de.sbtab.utils;

import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;

/**
 * Used to create and control tabs in jfx <p>Node</p>
 * @author zakharc
 * 
 * */
public class SBTabTabsComposer {
	private BorderPane root;
	private TabPane tabPane = new TabPane();

	public SBTabTabsComposer(BorderPane root) {
		super();
		this.root = root;
		this.root.setCenter(tabPane);
	}

	/**
	 * TODO: should not generate already generated tabs; 
	 * 		 focus on some tab should be changed when generated
	 * Generates a tab with some name
	 * @param name text to be shown on tab 
	 * @node content to show
	 **/
	public void generateTab(Node node, String name) {
		if (isTabExists(name)) {
			// change focus on this tab
		} else {
			Tab tab = new Tab();
			tab.setText(name);
			tab.setContent(node);
			tabPane.getTabs().add(tab);
			// change focus on this tab
		}
	}
	
	/**
	 * Used to indicate if tab with this name already exists
	 * @param name string to compare
	 * 
	 * */
	private boolean isTabExists(String name) {
		return tabPane.getTabs().stream()
				.anyMatch(elem -> elem.textProperty().get().equalsIgnoreCase(name));
	}
}
