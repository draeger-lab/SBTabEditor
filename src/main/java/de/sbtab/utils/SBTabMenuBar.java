package de.sbtab.utils;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

/**
 * A simple menu bar for displaying in {@code SBTabEditor}.
 * */
public class SBTabMenuBar {
	
	ContextMenu context;

	public SBTabMenuBar() {
		super();
		this.context = new ContextMenu();
	}
	
	/**
	 * Adds an element to the menu bar 
	 * @param name text to show in menu
	 * @param onEvent event to fire on user interaction
	 * @return decorated instance of {@code SBTabMenuBar}
	 * 
	 * */
	public SBTabMenuBar addMenuBarItem(String name, EventHandler<ActionEvent> onEvent) {
		MenuItem item = new MenuItem(name);
		item.setOnAction(onEvent);
		context.getItems().add(item);
		return this;
	}
	
	/**
	 * Shows decorated {@code ContextMenu}
	 * */
	public void showDefault(MouseEvent e) {
		getContext().show(new Text(), e.getScreenX(), e.getScreenY());
	}
	
	public ContextMenu getContext() {
		return this.context;
	}
}
