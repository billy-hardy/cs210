package project.statements.ddl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import project.data.Database;
import project.data.HardyException;
import project.data.persistor.XmlSaver;
import project.statements.ICommand;

/**
 * Creates a new table with the given name
 * and fields, unless a table with that name
 * already exists.
 */
public class DefineTableCommand extends XmlSaver implements ICommand {

	private Pattern pattern = Pattern.compile("define +table +(\\S*) +having +fields *\\( *(.*) *\\) *;", Pattern.CASE_INSENSITIVE);
	private String tableName, extendedFields;
	
	/* (non-Javadoc)
	 * @see project.one.ICommand#matches(java.lang.String)
	 */
	@Override
	public boolean matches(String input) {
		Matcher matcher = pattern.matcher(input.trim());
		if(matcher.matches()) {
			tableName = matcher.group(1);
			extendedFields = matcher.group(2);
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see project.one.ICommand#execute()
	 */
	@Override
	public void execute() throws HardyException {
		if(tableName.equalsIgnoreCase("dictionary"))
			throw new HardyException("Table name not allowed");
		Database.get().addTable(tableName, extendedFields);
		save();
	}
}
