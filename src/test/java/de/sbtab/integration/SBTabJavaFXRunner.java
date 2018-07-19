package de.sbtab.integration;


import java.util.concurrent.CountDownLatch;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

import com.sun.javafx.application.PlatformImpl;

/**
 * This Runner should be used to run tests on JFX-Thread. Use @RunWith
 * annotation.
 * 
 */
@SuppressWarnings("restriction")
public class SBTabJavaFXRunner extends BlockJUnit4ClassRunner {
	/**
	 * Initializes the JavaFx runtime.
	 */
	public SBTabJavaFXRunner(final Class<?> klass) throws InitializationError {
		super(klass);
		try {
			platformStart();
		} catch (final InterruptedException e) {
			throw new InitializationError("JFX Platform can not be initialized.");
		}
	}

	/**
	 * Invoked typically on the main thread. At this point JavaFX Application Thread
	 * has been started. A second application in the same VM/Classloader can not be
	 * created with this. Results in an exception.
	 */
	private static void platformStart() throws InterruptedException {
		// Until the count reaches zero, actions in a thread prior to calling 
		// {@code countDown()}
		CountDownLatch latch = new CountDownLatch(1);
		PlatformImpl.startup(() -> {
		});
		latch.countDown();
		latch.await();
	}
}