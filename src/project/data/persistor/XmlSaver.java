package project.data.persistor;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import project.data.Database;
import project.data.DatabaseStrings;
import project.data.HardyException;


public class XmlSaver implements DatabaseStrings {

	public static void save() throws HardyException {
		try {
			PrintWriter printer = new PrintWriter(xmlFile);
			String s = xmlHeader;
			printer.print(s + Database.get().toXmlString());
			printer.close();
		} catch(FileNotFoundException e) {
			throw new HardyException("File not found");
		}
	}
	
	
}
