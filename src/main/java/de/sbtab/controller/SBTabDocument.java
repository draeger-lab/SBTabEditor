package de.sbtab.controller;

import java.beans.PropertyChangeEvent;
import java.io.File;
import javax.swing.tree.TreeNode;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.sbml.jsbml.util.TreeNodeChangeListener;
import org.sbml.jsbml.AbstractTreeNode;
import org.sbml.jsbml.util.TreeNodeRemovedEvent;

public class SBTabDocument<T> implements TreeNodeChangeListener {

	private static final transient Logger LOGGER = LogManager.getLogger(SBTabController.class);

	private T tempDoc;

	private String pathtempDoc;

	boolean changed;

	/**
	 * Constructor for Document
	 *
	 * @param doc
	 * 
	 * @param pathDoc
	 */
	public SBTabDocument(T doc, String pathDoc) {
		try {
			tempDoc = doc;
			pathtempDoc = pathDoc;
			
//			File temp = File.createTempFile("temp-file-name", ".tmp");			
//			String absolutePath = temp.getAbsolutePath();
//			pathtempDoc = absolutePath.substring(0, absolutePath.lastIndexOf(File.separator));			
//			TidySBMLWriter.write(doc, pathtempDoc, "temp-file-name", version);			
//			tempDoc = SBMLReader.read(new File(tempfilePath));	

			if (tempDoc instanceof AbstractTreeNode) {
				((AbstractTreeNode) tempDoc).addTreeNodeChangeListener(this);
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		changed = false;
	}


	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		System.out.println("change");
		changed = true;
	}

	@Override
	public void nodeAdded(TreeNode node) {
		System.out.println("new entry");
		changed = true;
	}

	@Override
	public void nodeRemoved(TreeNodeRemovedEvent event) {
		TreeNode parent = event.getPreviousParent();
		System.out.println("Child of " + parent.toString() + " removed");
		changed = true;
	}

}