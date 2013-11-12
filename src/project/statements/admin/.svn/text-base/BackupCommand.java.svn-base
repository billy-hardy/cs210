package project.statements.admin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import project.data.Database;
import project.data.HardyException;
import project.statements.ICommand;

/**
 * The Class BackupCommand.
 */
public class BackupCommand implements ICommand {

	private Pattern pattern = Pattern.compile("backup +to +'(\\S*)' *;", Pattern.CASE_INSENSITIVE);
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
			FileOutputStream file = new FileOutputStream(new File(fileName));
			ObjectOutputStream backup = new ObjectOutputStream(file);
			db.serialize(backup);
		} catch (IOException e) {
			throw new HardyException("Stupid IOException", e);
		}
	}

}
