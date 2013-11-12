package project.statements.admin;

import org.junit.Before;

import project.statements.AbstractCommandTest;
import project.statements.admin.ExitCommand;

public class ExitCommandTest extends AbstractCommandTest {

	@Override
	@Before
	public void setUp() throws Exception {
		command = new ExitCommand();
		good = new String[]{"exit;", "EXIT  ;", "   ExIt;"};
		bad = new String[]{"escape;", "Ex", "quit;"};
		
	}

}
