package project.statements.dml;

import org.junit.Before;

import project.statements.AbstractCommandTest;
import project.statements.dml.InsertCommand;

public class InsertCommandTest extends AbstractCommandTest {

	@Override
	@Before
	public void setUp() throws Exception {
		command = new InsertCommand();
		good = new String[]{"insert (a)into emp;", " INSert(a,b, d) iNto emp   ;", "InSert   (  a)  into emp;"};
		bad = new String[]{"insert a into emp;", "insErT (a) b into emp  ;", "  Insert (a)   into emp and  sal;"};
		
		
	}

}
