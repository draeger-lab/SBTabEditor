package de.sbtab.controller;

import java.beans.PropertyChangeEvent;
import java.io.File;
import javax.swing.tree.TreeNode;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.sbml.jsbml.util.TreeNodeChangeListener;
import org.sbml.jsbml.AbstractTreeNode;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.util.TreeNodeRemovedEvent;

/**
 * SBTabDocument contains a temporary document and a temporary file. 
 * SBTabDocument implements {@link TreeNodeChangeListener}. 
 * If {@link SBTabDocument#tempDoc} is changed, boolean changed is set on true.
 *
 * @param <T>
 */
public class SBTabDocument<T> implements TreeNodeChangeListener {

	private static final transient Logger LOGGER = LogManager.getLogger(SBTabController.class);

	private T tempDoc;

	private File tempDocFile;

	boolean changed;

	/**
	 * Constructor for Document
	 *
	 * @param T doc
	 * 
	 * @param File file
	 */
	public SBTabDocument(T doc,File file) {
		try {
			tempDoc = doc;
			tempDocFile = file;

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
	
	public void setTempDoc(T doc) {
		tempDoc=doc;
	}
	
	public T getTempDoc() {
		return tempDoc;
	}
	
	public void setFile(File file) {
		tempDocFile=file;
	}
	
	/**
	 * 
	 * @return temporary File or null
	 */
	public File getFile() {
		if (tempDocFile!=null) {
		return tempDocFile;
		}
		else return null;
	}
	
	/**
	 * if document has unsaved changes, returns true 
	 * 
	 * @return boolean changed
	 */
	public boolean getChanged() {
		return changed;
	}
	
	/**
	 * 
	 * @param bool 
	 */
	public void setChanged(boolean bool) {
		changed=bool;
	}
}