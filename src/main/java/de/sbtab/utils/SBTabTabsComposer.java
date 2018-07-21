package de.sbtab.utils;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;

/**
 * Used to create and control tabs in jfx {@code Node}
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
	 * Generates a tab with some name
	 * @param name text to be shown on tab 
	 * @node content to show
	 * 
	 **/
	public void generateTab(Node node, String name) {
		if (isTabExists(name)) {
			setFocusOnTab(name);
		} else {
			Tab tab = new Tab();
			tab.setText(name);
			tab.setContent(node);
			tabPane.getTabs().add(tab);
			setFocusOnTab(name);
		}
	}
	
	/**
	 * Used to indicate if tab with this name already exists
	 * @param name string to compare
	 * 
	 * */
	public boolean isTabExists(String name) {
		return tabPane.getTabs().stream()
				.anyMatch(elem -> elem.textProperty().get().equalsIgnoreCase(name));
	}
	
	/**
	 * Focus tab depending on the name of it
	 * @param name name of a tab to focus
	 * 
	 * */
	private void setFocusOnTab(String name) {
		ObservableList<Tab> tabs = tabPane.getTabs();
		for (int i = 0; i < tabs.size(); i++) {
			if (tabs.get(i).textProperty().get().equalsIgnoreCase(name)) {
				tabPane.getSelectionModel().select(i);
			}
		}
	}

	/**
	 * Returns state of table pull at this moment of time
	 * @return the tabPane
	 */
	public TabPane getTabPane() {
		return tabPane;
	}
}
