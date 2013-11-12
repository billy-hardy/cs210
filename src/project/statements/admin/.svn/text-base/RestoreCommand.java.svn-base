package project.statements.admin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import project.data.Database;
import project.data.HardyException;
import project.statements.ICommand;

/**
 * The Class RestoreCommand.
 */
public class RestoreCommand implements ICommand {

	private Pattern pattern = Pattern.compile("restore +from +\'(\\S*)\' *;", Pattern.CASE_INSENSITIVE);
	private String fileName;
	
	/* (non-Javadoc)
	 * @see project.one.ICommand#matches(java.lang.String)
	 */
	@Override
	public boolean matches(String input) {
		Matcher matcher = pattern.matcher(input.trim());
		if(matcher.matches()) {
			fileName = matcher.group(1);
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see project.one.ICommand#execute()
	 */
	@Override
	public void execute() throws HardyException {
		try {
			Database db = Database.get();
			FileInputStream file = new FileInputStream(new File(fileName));
			ObjectInputStream restore = new ObjectInputStream(file);
			db.deserialize(restore);
		} catch (IOException e) {
			throw new HardyException("Stupid IOException", e);
		}
	}

}
