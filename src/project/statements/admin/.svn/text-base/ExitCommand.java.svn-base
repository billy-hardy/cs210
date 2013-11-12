package project.statements.admin;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import project.data.HardyException;
import project.statements.ICommand;


/**
 * The class ExitCommand recognizes whether a command is
 * an exit command and if it is, ends the program.
 */
public class ExitCommand implements ICommand {
	
	private Pattern pattern = Pattern.compile("exit *;", Pattern.CASE_INSENSITIVE);
	
	/* (non-Javadoc)
	 * @see project.one.ICommand#matches(java.lang.String)
	 */
	@Override
	public boolean matches(String input) {
		Matcher matcher = pattern.matcher(input.trim());
		if(matcher.matches()) {
			return true;
        }
		return false;
	}

	/* (non-Javadoc)
	 * @see project.one.ICommand#execute()
	 */
	@Override
	public void execute() throws HardyException {
		System.exit(0);
	}
}
