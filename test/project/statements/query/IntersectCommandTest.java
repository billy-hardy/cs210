package project.statements.query;

import org.junit.Before;

import project.statements.AbstractCommandTest;

public class IntersectCommandTest extends AbstractCommandTest {

	@Override
	@Before
	public void setUp() throws Exception {
		good = new String[]{"intersect emp and sal;", "  inTErsect gov   and pres   ;", "INTERSECT file    AND file2;"};
		bad = new String[]{"Inter sect empe and sal;", "intersect emp   ;", " InterSect file emp and sal;"};
		
		
	}

}
