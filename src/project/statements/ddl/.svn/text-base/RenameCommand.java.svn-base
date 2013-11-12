package project.statements.ddl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import project.data.Database;
import project.data.HardyException;
import project.data.persistor.XmlSaver;
import project.statements.ICommand;

/**
 * Renames the table by the given name to
 * the other given name.
 */
public class RenameCommand implements ICommand {

	private Pattern pattern = Pattern.compile("rename +table +(\\S*) +to +(\\S*) *;", Pattern.CASE_INSENSITIVE);
	private String oldName, newName;
	
	/* (non-Javadoc)
	 * @see project.one.ICommand#matches(java.lang.String)
	 */
	@Override
	public boolean matches(String input) {
		Matcher matcher = pattern.matcher(input.trim());
		if(matcher.matches()) {
		    oldName = matcher.group(1);
			newName = matcher.group(2);
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see project.one.ICommand#execute()
	 */
	@Override
	public void execute() throws HardyException {
		Database.get().renameTable(oldName, newName);
		XmlSaver.save();
	}
}
