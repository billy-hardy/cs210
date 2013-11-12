package project.statements.admin;

import org.junit.Before;

import project.statements.AbstractCommandTest;
import project.statements.admin.ReadCommand;

public class ReadCommandTest extends AbstractCommandTest {

	@Override
	@Before
	public void setUp() throws Exception {
		command = new ReadCommand();
		good = new String[]{"read 'sel';", " ReaD   'rabbit'   ;", "READ   'Jet.txt';"};
		bad = new String[]{"read 'sel;", "Read rabbi.txt;", "read 'jet.txt' and 'sal.txt';"};
		
		
	}

}
