package project.statements.admin;

import org.junit.Before;

import project.statements.AbstractCommandTest;
import project.statements.admin.RestoreCommand;

public class RestoreCommandTest extends AbstractCommandTest {

	@Override
	@Before
	public void setUp() throws Exception {
		command = new RestoreCommand();
		good = new String[]{"restore from 'sel';", "   reSTORE   froM    'rabbit'   ;", "restore   From 'Jet.txt';"};
		bad = new String[]{"Restere from 'sel';", "RESTore;", "restore from 'sel' and  'jet.txt';"};
		
		
	}

}
