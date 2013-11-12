package project.statements.query;

import org.junit.Before;

import project.statements.AbstractCommandTest;

public class JoinCommandTest extends AbstractCommandTest {

	@Override
	@Before
	public void setUp() throws Exception {
		good = new String[]{"join emp and sel;", "  JOIn lada   AnD   rabbit   ;", "joiN command and Jet;"};
		bad = new String[]{"join emp or dsfl;", "JOIN select;", "Join emp from sel;"};
		
		
	}

}
