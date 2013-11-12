package project.data.persistor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import project.data.Database;
import project.data.DatabaseStrings;
import project.data.HardyException;
import project.data.Table;


/**
 * Reads the xml file
 */
public class SAXReader implements DatabaseStrings {

	/**
	 * Writes the dtd file.
	 *
	 * @throws FileNotFoundException the file not found exception
	 */
	private void writeDTD() throws FileNotFoundException {
		PrintWriter printer = new PrintWriter(dtdFileName);
		printer.print(data);
		printer.close();
	}
	
	/**
	 * Writes dtd file and then reads the xml file.
	 *
	 * @throws SAXException the SAX exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void saxReader() throws SAXException, IOException {
		new SAXReader().writeDTD();
		XMLReader reader = XMLReaderFactory.createXMLReader();
		reader.setContentHandler(new HardyHandler());
		reader.parse(xmlFile);
	}

	
	
	private class HardyHandler extends DefaultHandler implements ContentHandler {
		
		private boolean table=false, tableName=false, fields=false, field=false;
		private boolean tree=false, fieldName=false, fieldType=false, fieldSize=false;
		private String treeData;
		private Table table1;
		private String fData, tName;
		private Database db = Database.get();
		
		/* (non-Javadoc)
		 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
		 */
		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
			try {
				if(qName.equalsIgnoreCase("table"))
					table = true;
				else if(qName.equalsIgnoreCase("tableName"))
					tableName = true;
				else if(qName.equalsIgnoreCase("fields")) {
					table1 = new Table(tName); //creates a new table with name tName.
					fields = true;
				} else if(qName.equalsIgnoreCase("field"))
					field = true;
				else if(qName.equalsIgnoreCase("fieldName"))
					fieldName = true;
				else if(qName.equalsIgnoreCase("fieldType"))
					fieldType = true;
				else if(qName.equalsIgnoreCase("fieldSize"))
					fieldSize = true;
				else if(qName.equalsIgnoreCase("BSTree"))
					tree = true;
			} catch (HardyException e) {
				//System.out.println(e.getMessage());
			}
		}
		
		/* (non-Javadoc)
		 * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
		 */
		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
			try {
				if(fieldSize && !fieldType && !fieldName)
					fieldSize = false;
				else if(fieldType && !fieldName)
					fieldType = false;
				else if(fieldName)
					fieldName = false;
				else if(field) {
					table1.addFields(fData);
					field = false;
				} else if(tree) {
					tree = false;
					table1.instantiateBinaryTree(treeData);
				} else if(tableName && !fields)
					tableName = false;
				else if(fields)
					fields = false;
				else if(table) {
					db.addTable(tName, table1); //adds table to database
					table1 = null;
					table = false;
				} 
			} catch (HardyException e) {
				//System.out.println(e.getMessage());
			}
		}
		
		/* (non-Javadoc)
		 * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
		 */
		@Override
		public void characters(char[] ch, int start, int length) throws SAXException {
			String datum = new String(ch, start, length);
			if(fieldName)
				fData = datum + " ";
			else if(fieldType)
				fData += datum + " ";
			else if(fieldSize)
				fData += "(" + datum + ")";
			else if(tree)
				treeData = datum;
			else if(tableName)
				tName = datum;
		}
		
	}
	
}
