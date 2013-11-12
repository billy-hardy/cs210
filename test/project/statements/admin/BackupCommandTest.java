package project.statements.admin;

import org.junit.Before;

import project.statements.AbstractCommandTest;
import project.statements.admin.BackupCommand;

public class BackupCommandTest extends AbstractCommandTest {

	@Override
	@Before
	public void setUp() throws Exception {
		command = new BackupCommand();
		good = new String[]{"BACKUP to 'emp';", "backUP  to    'file'   ;", "  bacKUP     to 'file';"};
		bad = new String[]{"back up  to 'emp';", "Backup to 'file;", "please BackUP to 'file' ;"};
	}

}
