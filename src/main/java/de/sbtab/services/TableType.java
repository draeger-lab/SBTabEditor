package de.sbtab.services;

/**
 * Represents a type of {@code SBTabTable} view
 * 
 * <p>
 * If a new name to appropriate table should be assigned, the following naming rules 
 * must be observed:
 * <blockquote><pre>
 * 1. If a name consists of several words every space(blank) should be substituted with
 *	  underscore(_)
 * </pre></blockquote>
 * <blockquote><pre>
 * 2. Every separate type must be written in upper case(general conventions)
 * </pre></blockquote>
 * </p>
 * 
 * Why naming rules are important?
 * <p>
 * The {@link de.sbtab.utils.SBTabTabsComposer} creates new tabs based on the name of the jfx 
 * {@code TextLabel} of corresponding {@code TreeItem} in {@code TreeView}TreeView. The
 * name of the {@code Tab} is translated into the corresponding {@code TableType}, 
 * depending on that {@link de.sbtab.services.SBTabTableProducer} generates necessary 
 * {@code TableView} for displaying it in the {@code Tab}.
 * </p>
 * 
 * */
public enum TableType {
	REACTIONS,
	SPECIES,
	COMPARTMENTS,
	UNIT_DEFINITIONS,
	//
}
