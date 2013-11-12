package project.one;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BackupCommand implements ICommand {

	private Pattern pattern = Pattern.compile("backup to (.*) *?;", Pattern.CASE_INSENSITIVE);
	private String fileName;
	
	@Override
	public boolean matches(String input) {
		Matcher matcher = pattern.matcher(input.trim());
		if(matcher.matches()) {
			fileName = matcher.group(1);
			return true;
		}
		return false;
	}

	@Override
	public void execute() throws DatabaseException {
		System.out.println("This is a correct backup statement");

	}

}
