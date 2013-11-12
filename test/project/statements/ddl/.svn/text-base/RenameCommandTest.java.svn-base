package project.statements.ddl;

import org.junit.Before;

import project.statements.AbstractCommandTest;
import project.statements.ddl.RenameCommand;

public class RenameCommandTest extends AbstractCommandTest {

	@Override
	@Before
	public void setUp() throws Exception {
		command = new RenameCommand();
		good = new String[]{"rename table emp to employee;", " ReNAme  taBLe rabbit   to   jackRabbit   ;", "RenamE table  file to   folder;"};
		bad = new String[]{"re-name table emp to employee;", "rename  tables emp and file to employee and folder;", "rename table emp;"};
	}

}
