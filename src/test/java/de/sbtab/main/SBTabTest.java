package de.sbtab.main;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.File;

import org.junit.jupiter.api.Test;

import de.sbtab.controller.SBTabController;

class SBTabTest {
 
    @Test
    void readSBMLFileTest() {
    	SBTabController controller = new SBTabController();
    	assertNull(controller.read("42"));
    }
}