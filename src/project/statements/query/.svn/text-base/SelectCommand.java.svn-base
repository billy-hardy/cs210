package project.statements.query;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import project.data.DataManipulator;
import project.data.Table.DataSet;
import project.data.Database;
import project.data.HardyException;
import project.statements.ISetCommand;

/**
 * The Class SelectCommand.
 */
public class SelectCommand implements ISetCommand {

	private Pattern pattern = Pattern.compile(
			"select +(.*?)(?: +where +(.*?))? *;", Pattern.CASE_INSENSITIVE);
	private String tableName, where;

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
			where = matcher.group(2);
			return true;
		}
		return false;
	}

	@Override
	public DataSet execute(DataManipulator d1, DataManipulator d2) throws HardyException {
		if(d1 == null) {
			return Database.get().selectTable(tableName, where);
		}
		return Database.get().selectData(d1, where);
	}

}
