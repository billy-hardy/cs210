package project.statements.query;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import project.data.DataManipulator;
import project.data.Table.DataSet;
import project.data.Database;
import project.data.HardyException;
import project.statements.ISetCommand;

/**
 * The Class ProjectCommand
 */
public class ProjectCommand implements ISetCommand {

	private Pattern pattern = Pattern.compile("project +(.+?) +over +(.+?) *;", Pattern.CASE_INSENSITIVE);
	private String queryList, fieldList;
	
	/* (non-Javadoc)
	 * @see project.one.ICommand#matches(java.lang.String)
	 */
	@Override
	public boolean matches(String input) {
		Matcher matcher = pattern.matcher(input.trim());
		if(matcher.matches()) {
			queryList = matcher.group(1);
			fieldList = matcher.group(2);
			return true;
		}
		return false;
	}

	@Override
	public DataSet execute(DataManipulator d1, DataManipulator d2) throws HardyException {
		if(d1 == null) {
			return Database.get().projectTable(queryList, fieldList);
		}
		return Database.get().projectData(d1, fieldList);
	}

}
