package project.statements.ddl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import project.data.Database;
import project.data.HardyException;
import project.data.persistor.XmlSaver;
import project.statements.ICommand;


/**
 * The Class DefineIndexCommand.
 */
public class DefineIndexCommand implements ICommand {

	private Pattern pattern = Pattern.compile("define +index +on +(\\S*) *?\\( *?(.*) *?\\) *;", Pattern.CASE_INSENSITIVE);
	private String tableName, fieldName;
	
	/* (non-Javadoc)
	 * @see project.one.ICommand#matches(java.lang.String)
	 */
	@Override
	public boolean matches(String input) {
		Matcher matcher = pattern.matcher(input.trim());
		if(matcher.matches()) {
			tableName = matcher.group(1);
			fieldName = matcher.group(2);
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see project.one.ICommand#execute()
	 */
	@Override
	public void execute() throws HardyException {
		Database.get().defineIndex(tableName, fieldName);
		XmlSaver.save();
	}

}
