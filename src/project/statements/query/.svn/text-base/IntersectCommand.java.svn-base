package project.statements.query;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import project.data.DataManipulator;
import project.data.Table.DataSet;
import project.data.Database;
import project.data.HardyException;
import project.statements.ISetCommand;

/**
 * The Class IntersectCommand.
 */
public class IntersectCommand implements ISetCommand {
	
	private Pattern pattern = Pattern.compile("intersect +(.+?) +and +(.+?) *;", Pattern.CASE_INSENSITIVE);
	private String query1, query2;
	
	@Override
	public boolean matches(String input) {
		Matcher matcher = pattern.matcher(input.trim());
		if(matcher.matches()) {
			query1 = matcher.group(1);
			query2 = matcher.group(2);
			return true;
		}
		return false;
	}

	@Override
	public DataSet execute(DataManipulator d1, DataManipulator d2) throws HardyException {
		if(d2 == null) {
			if(d1 == null) {
				return Database.get().intersect(query1, query2);
			}
			if(query1.contains("$")) {
				return Database.get().intersect(d1, query2, false);
			} else return Database.get().intersect(d1, query1, true);
		}
		return Database.get().intersect(d1, d2);
	}

}
