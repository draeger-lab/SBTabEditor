package de.sbmltab.main;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.File;

import org.junit.jupiter.api.Test;

import de.sbmltab.controller.SBMLTabController;

class AppTest {
 
    @Test
    void readSBMLFileTest() {
    	assertNull(SBMLTabController.read(new File("42")));
    }
}