package de.sbtab.integration;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import de.sbtab.view.SBTabMainView;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;


public class SBTabLaunchTest {
	@Rule
	public final ExpectedException exception = ExpectedException.none();
	
	@Test
    public void testStart() throws InterruptedException {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                new JFXPanel(); // Initializes the JavaFx Platform
                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                        try {
                        	exception.expect(NullPointerException.class);
							new SBTabMainView().start(new Stage());
						} catch (Exception e) {
							//
						}
                    }
                });
            }
        });
        thread.start();// Initialize the thread
        Thread.sleep(3000); // Time to use the app
    }
}
