package de.sbtab.main;

import de.sbtab.view.SBTabMainView;
import javafx.application.Application;
import org.sbml.jsbml.SBMLDocument;

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
