package project.statements.dml;

import org.junit.Before;

import project.statements.AbstractCommandTest;
import project.statements.dml.DeleteCommand;

public class DeleteCommandTest extends AbstractCommandTest {

	@Override
	@Before
	public void setUp() throws Exception {
		command = new DeleteCommand();
		good = new String[]{"delete emp where sal>20;", " delete  emp  ;", "DELETE emp   WHere num < 3;"};
		bad = new String[]{"delETE emp were sal>20;", "delete emp sal  ;", " dlete emp;"};
		
	}

}
