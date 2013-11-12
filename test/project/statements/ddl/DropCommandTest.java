package project.statements.ddl;

import org.junit.Before;

import project.statements.AbstractCommandTest;
import project.statements.ddl.DropCommand;

public class DropCommandTest extends AbstractCommandTest {

	@Override
	@Before
	public void setUp() throws Exception {
		command = new DropCommand();
		good = new String[]{"drop table emp;", "DROP taBle table  ;", "  drop tabLE emp;"};
		bad = new String[]{"drp table  emp ;", " Drop tAble emp and table;", "drop taable emp;"};
		
	}
}
