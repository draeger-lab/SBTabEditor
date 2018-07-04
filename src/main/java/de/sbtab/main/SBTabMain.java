package de.sbtab.main;

import org.sbml.jsbml.SBMLDocument;

import de.sbtab.view.SBTabMainView;

public class SBTabMain {

	public static void main(String[] args) {
		//Application.launch(SBTabMainView.class, args);
		SBTabMainView GUI = new SBTabMainView();
        Thread t = new Thread(GUI);
        t.start();
	}
	
	public static void mainOnFile(SBMLDocument doc) {
		//Application.launch(SBTabMainView.class, args);
		SBTabMainView GUI = new SBTabMainView(doc);
        Thread t = new Thread(GUI);
        t.start();
	}
}
