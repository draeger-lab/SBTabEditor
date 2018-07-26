package de.sbtab.component;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	SBTabTest.class,
	SBTabControllerTest.class,
	SBTabTabsComposerTest.class,
	//
})
public class SBTabComponentTests {
	// the class remains empty,
	// used only as a holder for the above annotations
}
