package de.sbtab.main;

import de.sbtab.view.SBTabMainView;
import javafx.application.Application;

public class SBTabMain {

	public static void main(String[] args) {
		//Application.launch(SBTabMainView.class, args);
		SBTabMainView GUI = new SBTabMainView();
        Thread t = new Thread(GUI);
        t.start();
	}
}