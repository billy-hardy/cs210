package project.statements.ddl;

import org.junit.Before;

import project.statements.AbstractCommandTest;
import project.statements.ddl.DefineTableCommand;

public class DefineTableCommandTest extends AbstractCommandTest {

	@Override
	@Before
	public void setUp() throws Exception {
		command = new DefineTableCommand();
		good = new String[]{"define table emp having fields (sal);", " DEFINE  table  emp having   fields ( sal, num )  ;", "define  table   file haviNG fIelds (sal, num, exe);"};
		bad = new String[]{"define  table emp having fields sal;", "Define table emp having fields (sal, num ;", "define table emp hving fields (sal) (num);"};
	}

}
