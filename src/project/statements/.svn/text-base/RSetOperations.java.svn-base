package project.statements;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import project.data.Table.DataSet;
import project.data.HardyException;
import project.statements.query.*;

public class RSetOperations {
	
	private Pattern pattern = Pattern.compile("\\(.[^()]*\\)");
	private ISetCommand[] setCommands = new ISetCommand[] {
			new ProjectCommand(),		new IntersectCommand(),
			new UnionCommand(),  	  	new MinusCommand(),
			new SelectCommand(),	  	new JoinCommand(),
			new SymmetricDifferenceCommand() };
	private HashMap<String, DataSet> hm = new HashMap<String, DataSet>();
	private static RSetOperations rso = null;
	private RSetOperations() {}
	public static RSetOperations get() {
		if(rso == null)
			rso = new RSetOperations();
		return rso;
	}
	
	public DataSet execute(String in) throws HardyException {
		Matcher matcher = pattern.matcher(in);
		String inner;
		int i = 1;
		while(matcher.find()) {
			inner = matcher.group();
			for(ISetCommand c: setCommands) {
				if(c.matches(inner.substring(1,inner.length()-1) + ";")) {
					int first = inner.indexOf("$");
					int last = inner.lastIndexOf("$");
					DataSet d, d1, d2;
					if(first != -1) {
						if(first == last) {
							d1 = hm.get(inner.substring(first, first+2));
							d2 = null;
						} else {
							d1 = hm.get(inner.substring(first, first+2));
							d2 = hm.get(inner.substring(last, last+2));
						}
					} else {
						d1 = null;
						d2 = null;
					}
					d = c.execute(d1, d2);
					hm.put("$"+i, d);
					in = in.replace(inner, "$"+i);
					i++;
					matcher = pattern.matcher(in);
					break;
				}
			}
		}
		for(ISetCommand c: setCommands) {
			inner = in;
			if(c.matches(inner)) {
				int first = inner.indexOf("$");
				int last = inner.lastIndexOf("$");
				DataSet d1, d2;
				if(first != -1) {
					if(first == last) {
						d1 = hm.get(inner.substring(first, first+2));
						d2 = null;
					} else {
						d1 = hm.get(inner.substring(first, first+2));
						d2 = hm.get(inner.substring(last, last+2));
					}
				} else {
					d1 = null;
					d2 = null;
				}
				return c.execute(d1, d2);
			}
		}
		throw new HardyException("There is no match");
	}
}
