package project.statements.admin;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

import project.data.Driver;
import project.data.HardyException;
import project.statements.ICommand;


/**
 * The Class ReadCommand recognizes whether a command is
 * a read command and if it is, it reads the specified file.
 */
public class ReadCommand implements ICommand {

	private Pattern pattern = Pattern.compile("read +\'(\\S*)\' *;", Pattern.CASE_INSENSITIVE);
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
	public void execute() throws HardyException  {
		try {
			Scanner sc = new Scanner(new File(fileName));
			new Driver().read(sc);
		} catch (FileNotFoundException e) {
			throw new HardyException("File not found", e);
		}
		
	}
	
}
