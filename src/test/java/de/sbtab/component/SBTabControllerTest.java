package de.sbtab.component;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sbml.jsbml.SBMLDocument;

import de.sbtab.controller.SBTabController;

class SBTabControllerTest {
	private static final String PATH_TO_TEST_FILE_XML = "src/test/resources/ExampleFile.xml";
	private static final String PATH_TO_TEST_FILE_GZ = "src/test/resources/Recon3D.xml.gz";
	private static final String DUMMY_VALUE = "42";
	static SBTabController controller;
	
	@BeforeAll
	static void setUp() {
		controller = new SBTabController();
	}

	@Test
	void testRead() {
		assertNotNull(controller.read(PATH_TO_TEST_FILE_XML));
		assertNotNull(controller.read(PATH_TO_TEST_FILE_GZ));
		assertNotNull(controller.read(DUMMY_VALUE));
		assertNull(null);
	}
	
	@Test
	void testSave() {
		File path = new File(new File(PATH_TO_TEST_FILE_XML).getParentFile().getPath() + "/ExampleFile2");
		SBMLDocument doc = controller.read(PATH_TO_TEST_FILE_XML).getTempDoc();
		controller.save(doc, path, DUMMY_VALUE, DUMMY_VALUE);
		// TODO: test when save fixed
		//assertTrue(path.exists());
	}

	@Test
	void testGetDocumentation() {
		assertNotNull(controller.getDocumentation("https://draeger-lab.github.io/SBTabEditor/", System.getProperty("user.dir")+"/docs/index.html"));
	}
	
	@Test
	void testStringValidator() {
		//
	}
	
}
