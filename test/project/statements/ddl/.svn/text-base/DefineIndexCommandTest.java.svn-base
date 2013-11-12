package project.statements.ddl;

import org.junit.Before;

import project.statements.AbstractCommandTest;
import project.statements.ddl.DefineIndexCommand;

public class DefineIndexCommandTest extends AbstractCommandTest {

	@Override
	@Before
	public void setUp() throws Exception {
		command = new DefineIndexCommand();
		good = new String[]{"define index on emp(sal);", " DEFINE  index on  emp  (  sal )  ;", "define  INDEX   on file  (num);"};
		bad = new String[]{"define  index on emp;", "Define index on emp (sal) num ;", "define Index  on emp (sal, num;"};
	}

}
