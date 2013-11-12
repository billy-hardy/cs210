package project.statements.ddl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import project.data.Database;
import project.data.HardyException;
import project.data.persistor.XmlSaver;
import project.statements.ICommand;

/**
 * Drops the table with the given name 
 * from the database, if it exists.
 */
public class DropCommand implements ICommand  {

	private Pattern pattern = Pattern.compile("drop +table +(\\S*) *;", Pattern.CASE_INSENSITIVE);
	private Pattern pattern2 = Pattern.compile("drop +dictionary *;", Pattern.CASE_INSENSITIVE);
	private String tableName;
	
	/* (non-Javadoc)
	 * @see project.one.ICommand#matches(java.lang.String)
	 */
	@Override
	public boolean matches(String input) {
		Matcher matcher = pattern.matcher(input.trim());
		if(matcher.matches()) {
			tableName = matcher.group(1);
			return true;
		}
		matcher = pattern2.matcher(input.trim());
		if(matcher.matches()) {
			tableName = "dictionary";
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see project.one.ICommand#execute()
	 */
	@Override
	public void execute() throws HardyException {
		if(tableName.equalsIgnoreCase("dictionary")) {
			Database.get().dropAllTables();
		} else Database.get().dropTable(tableName);
		XmlSaver.save();
	}

}
