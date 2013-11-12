package project.statements;

import project.data.HardyException;


/**
 * The Interface ICommand.
 */
public interface ICommand {

	/**
	 * Tells whether the input matches this command's pattern.
	 *
	 * @param input the input
	 * @return true, if successful
	 */
	public boolean matches(String input);
	
	/**
	 * Executes the functionality of this command.
	 *
	 * @throws HardyException the database exception
	 */
	public void execute() throws HardyException;
	
}
