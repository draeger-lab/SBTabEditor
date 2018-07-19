package de.sbtab.utils;

import de.sbtab.services.SBTabTableProducer;
import de.sbtab.services.TableType;
import javafx.scene.layout.BorderPane;

/**
 * Used to handle click events in <p>SBTabTreeController</p>
 * @author zakharc
 * 
 * */
public class SBTabTableHandler {
	private SBTabTableProducer producer;
	private BorderPane pane;
	private SBTabTabsComposer tabsComposer;
	
	public SBTabTableHandler(SBTabTableProducer producer, BorderPane pane) {
		super();
		this.producer = producer;
		this.pane = pane;
		this.tabsComposer = new SBTabTabsComposer(this.pane);
	}
	
	/**
	 * Generates new table and initializes it in <p>Parent</p> base class for all nodes.  
	 * Different tables are separated with help of <p>TableComposer</p>
	 * */
	public void createTable(TableType type) {
		if (type != null) {
			tabsComposer.generateTab(producer.getTableView(type), capitalizeString(type));
		}
	}

	/**
	 * Convert string to low case and capitalize it. Example: "REACTION" -> "Reaction"
	 * */
	private String capitalizeString(TableType type) {
		return (type.toString().substring(0,1).toUpperCase() + type.toString().substring(1).toLowerCase()).replaceAll("_", " ");
	}
}
