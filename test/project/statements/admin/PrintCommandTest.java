package project.statements.admin;

import org.junit.Before;

import project.statements.AbstractCommandTest;
import project.statements.admin.PrintCommand;

public class PrintCommandTest extends AbstractCommandTest {

	@Override
	@Before
	public void setUp() throws Exception {
		command = new PrintCommand();
		good = new String[]{"print sel;", " Print   rabbit   ;", "  PRINT Jet;"};
		bad = new String[]{"PRint emp sal;", "Prit select;", "print emp from sel;"};
		
		
	}

}
