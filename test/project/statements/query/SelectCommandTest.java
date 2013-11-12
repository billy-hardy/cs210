package project.statements.query;

import org.junit.Before;

import project.statements.AbstractCommandTest;

public class SelectCommandTest extends AbstractCommandTest {

	@Override
	@Before
	public void setUp() throws Exception {
		good = new String[]{"select emp where sal >=30;", "  SELECT   rabbit   ;", "SeleCt command where Jet= 3 ;"};
		bad = new String[]{"select command and sal;", "selet emp;", "select emp whre sal<45;"};
		
		
	}

}
