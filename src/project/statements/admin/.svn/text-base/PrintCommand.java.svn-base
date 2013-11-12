package project.statements.admin;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import project.data.Database;
import project.data.HardyException;
import project.statements.ICommand;

/**
 * Prints all the tables in the database
 * when dictionary is told to be printed.
 */
public class PrintCommand implements ICommand {

	private Pattern pattern = Pattern.compile("print +(\\S*) *;", Pattern.CASE_INSENSITIVE);
	private Pattern pattern2 = Pattern.compile("print +(\\S*) +(.*?) +order +on +(\\S*) *;", Pattern.CASE_INSENSITIVE);
	private String tableName, fieldName;
	private boolean inOrder, reversed;
	
	/* (non-Javadoc)
	 * @see project.one.ICommand#matches(java.lang.String)
	 */
	@Override
	public boolean matches(String input) {
		Matcher matcher = pattern.matcher(input.trim());
		if(matcher.matches()) {
			tableName = matcher.group(1);
			inOrder = false;
			return true;
		}
		matcher = pattern2.matcher(input.trim());
		if(matcher.matches()) {
			tableName = matcher.group(1);
			reversed = (matcher.group(2).trim()).equalsIgnoreCase("in reverse");
			fieldName = matcher.group(3);
			inOrder = true;
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see project.one.ICommand#execute()
	 */
	@Override
	public void execute() throws HardyException {
		Database db = Database.get();
		if(tableName.equalsIgnoreCase("dictionary")) {
			System.out.println(db);
		} else {
			if(inOrder)
				System.out.println(db.inOrderPrint(tableName, fieldName, reversed));
			else System.out.println(db.selectTable(tableName, null));
		}
	}
}