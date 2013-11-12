package project.statements.dml;

import org.junit.Before;

import project.statements.AbstractCommandTest;
import project.statements.dml.UpdateCommand;

public class UpdateCommandTest extends AbstractCommandTest {

	@Override
	@Before
	public void setUp() throws Exception {
		command = new UpdateCommand();
		good = new String[]{"updATE emp set sal=322 where sal > 322;", "update  lada set rabbit  = cute where sc.hasNext()  ;", "update command set  Jet = blue;"};
		bad = new String[]{"update emp seet sal == 322 where;", " Updat emp set sal = 322 where sal>322;", "update emp;"};
		
	}

}
