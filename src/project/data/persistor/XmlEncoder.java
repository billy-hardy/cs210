package project.data.persistor;

import java.util.HashMap;

public class XmlEncoder {
	
	private static HashMap<String, String> codes;
	static {
		codes = new HashMap<String, String>();
		codes.put("\"", "&quot;");
		codes.put("&", "&amp;");
		codes.put("'", "&apos;");
		codes.put("<", "&lt;");
		codes.put(">", "&gt;");
	}
	
	private XmlEncoder(){}
	public static String encode(String input) {
		for(String x : codes.keySet())
			input = input.replace(x, codes.get(x));
		return input;
	}
}
