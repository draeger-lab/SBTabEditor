package de.sbtab.component;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import de.sbtab.utils.SBTabTabsComposer;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;


class SBTabTabsComposerTest {
	private static final String DUMMY_VALUE = "42";
	private static SBTabTabsComposer composer;
	private static BorderPane node = new BorderPane();
	
	@SuppressWarnings("restriction")
	@BeforeAll
	static void setUp() {
		// initiate JavaFxRuntime
		com.sun.javafx.application.PlatformImpl.startup(()->{});
		composer = new SBTabTabsComposer(node);
	}

	@Test
	void testGenerateTab() {
		composer.generateTab(new TableView<>(), DUMMY_VALUE);
		assertTrue(composer.isTabExists(DUMMY_VALUE));
	}

	@Test
	void testTabsPull() {
		composer.generateTab(new TableView<>(), DUMMY_VALUE);
		assertEquals(composer.getTabPane().getTabs().size(), 1);
		composer.generateTab(new TableView<>(), DUMMY_VALUE);
		assertEquals(composer.getTabPane().getTabs().size(), 1);
	}
}
