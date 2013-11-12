package project.statements.dml;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import project.data.Database;
import project.data.HardyException;
import project.statements.ICommand;

/**
 * The Class InsertCommand.
 */
public class InsertCommand implements ICommand {
	
	private Pattern pattern = Pattern.compile("insert *?\\( *(.*) *\\) *?into +(\\S*) *;", Pattern.CASE_INSENSITIVE);
	private String valueList, tableName;
	
	/* (non-Javadoc)
	 * @see project.one.ICommand#matches(java.lang.String)
	 */
	@Override
	public boolean matches(String input) {
		Matcher matcher = pattern.matcher(input.trim());
		if(matcher.matches()) {
			valueList = matcher.group(1);
			tableName = matcher.group(2);
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
		db.writeToFile(tableName, valueList);

	}

}
