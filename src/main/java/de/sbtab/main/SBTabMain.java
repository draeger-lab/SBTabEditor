package de.sbtab.main;

import org.sbml.jsbml.SBMLDocument;

import de.sbtab.controller.SBTabController;
import de.sbtab.view.SBTabMainView;

public class SBTabMain {

	public static void main(String[] args) {
		if (args.length>0){
			SBTabMainView GUI = new SBTabMainView();
			GUI.setDoc(SBTabController.read(args[0]));
            Thread t = new Thread(GUI);
            t.start();
		}
		else {
		    SBTabMainView GUI = new SBTabMainView();
            Thread t = new Thread(GUI);
            t.start();
		}
	}

}
