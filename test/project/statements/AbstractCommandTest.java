package project.statements;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import project.statements.ICommand;

public abstract class AbstractCommandTest {

	protected ICommand command;

	public abstract void setUp() throws Exception;

	protected String[] good;
	protected String[] bad;

	public AbstractCommandTest() {
		super();
	}

	@Test
	public void testMatches() {
		for(String s: good) {
			System.out.print(s);
			assertTrue(command.matches(s));
		}
		for(String s: bad) {
			assertFalse(command.matches(s));
		}
	}

}