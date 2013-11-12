package project.statements.dml;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import project.data.Database;
import project.data.HardyException;
import project.statements.ICommand;

/**
 * The Class UpdateCommand.
 */
public class UpdateCommand implements ICommand {

	private Pattern pattern = Pattern.compile(
			"update +(\\S*) +set +(.*?)(?: +where +(.*))? *;",
			Pattern.CASE_INSENSITIVE);
	private String tableName, set, where;

	/*
	 * (non-Javadoc)
	 * 
	 * @see project.one.ICommand#matches(java.lang.String)
	 */
	@Override
	public boolean matches(String input) {
		Matcher matcher = pattern.matcher(input.trim());
		if (matcher.matches()) {
			tableName = matcher.group(1);
			set = matcher.group(2);
			where = matcher.group(3);
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see project.one.ICommand#execute()
	 */
	@Override
	public void execute() throws HardyException {
		Database.get().updateTable(tableName, set, where);
	}

}