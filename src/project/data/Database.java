package project.data;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import project.data.Table.DataSet;
import project.data.persistor.XmlSaver;



/**
 * The Class Database stores all of the tables
 * that have been created.
 */
public class Database implements DatabaseStrings, Serializable {

	private static final long serialVersionUID = 1L;
	private HashMap<String, Table> tables = new HashMap<String, Table>();
	private static Database db = null;
	
	private Database() { }
	
	/**
	 * Gets the database object if it has already been
	 * instantiated. If it has not been, it instantiates one
	 * and gets that one.
	 *
	 * @return the database
	 */
	public static Database get() {
		if(db == null)
			db = new Database();
		return db;
	}
	
	/**
	 * Formats a table and adds it.
	 *
	 * @param name the name
	 * @param fields the fields
	 * @throws HardyException the hardy exception
	 */
	public void addTable(String name, String fields) throws HardyException {
		addTable(name, new Table(name, fields));
	}
	
	
	
	/**
	 * Adds the table.
	 *
	 * @param n the name
	 * @param t the table
	 * @throws HardyException the hardy exception
	 */
	public void addTable(String n, Table t) throws HardyException {
		n = n.toLowerCase();
		Table table = tables.remove(n);
		if(table == null)
			tables.put(n, t);
		else {
			tables.put(n, table); //put back the original table
			throw new HardyException("Table already exists");
		}
	}
	
	
	/**
	 * Drops the table with that name, if it exists
	 * and deletes all associated file.
	 *
	 * @param name the name
	 * @throws HardyException the hardy exception
	 */
	public void dropTable(String name) throws HardyException {
		Table table = tables.remove(name.toLowerCase());
		if(table == null)
			throw new HardyException("Table does not exist");
		table.deleteBinaryFile();
	}
	
	public void dropAllTables() throws HardyException {
		ArrayList<Table> arrayTables = new ArrayList<Table>(tables.values());
		for(Table t: arrayTables)
			t.deleteBinaryFile();
		tables.clear();
		new File(databaseStorage, varcharFile).delete();
		new File(databaseStorage).delete();
	}
	
	/**
	 * Creates another (identical) table with the
	 * new name and drops the old table, only if 
	 * the old table exists and the new one does not.
	 *
	 * @param oldName the old name
	 * @param newName the new name
	 * @throws HardyException the hardy exception
	 */
	public void renameTable(String oldName, String newName) throws HardyException {
		Table old = getTable(oldName);
		try {
			 getTable(newName);
		} catch (HardyException e) {
			old.setName(newName);
			tables.put(newName, old);
			return;
		}
    	tables.put(oldName, old);
		throw new HardyException("A table by that name already exists");
	}

	/**
	 * Gets the table.
	 *
	 * @param tName the table name
	 * @return the table
	 */
	public Table getTable(String tName) throws HardyException {
		Table t = tables.get(tName.toLowerCase());
		if(t==null)
			throw new HardyException("Invalid table name");
		return t;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String s = "";
		for(Table t: tables.values())
			s += t + "\n";
		if(s == "")
			return "Dictionary is empty";
		return s.substring(0, s.length()-1);
	}
	
	/**
	 * Gives an xml representation of the database.
	 *
	 * @return the string
	 */
	public String toXmlString() {
		String s = "<database>\n";
		for(Table t: tables.values())
			s += t.toXmlString();
		return s + "</database>";
	}
	
	/**
	 * Writes the information to the tables
	 * binary file. 
	 *
	 * @param tableName the table name
	 * @param valueList the values
	 * @throws HardyException the hardy exception
	 */
	public void writeToFile(String tableName, String valueList) throws HardyException {
		getTable(tableName).writeFile(valueList);
	}
	
	public void updateTable(String tableName, String set, String where) throws HardyException {
		Table t = getTable(tableName);
		t.updateFile(set, where);
	}
	
	public void deleteRowsInTable(String tableName, String where) throws HardyException {
		Table t = getTable(tableName);
		t.deleteRowsInFile(where);
	}
	
	public DataSet inOrderPrint(String tableName, String fName, boolean reversed) throws HardyException {
		Table t = getTable(tableName);
		return t.walk(fName, reversed);
	}
	
	public DataSet selectTable(String tableName, String where) throws HardyException {
		Table t = getTable(tableName);
		return t.selectRows(where);
	}

	public DataSet selectData(DataManipulator d1, String where) throws HardyException {
		return d1.selectRows(where);
	}
	
	public DataSet join(String tableName1, String tableName2) throws HardyException {
		Table table1 = getTable(tableName1);
		return join(table1, tableName2, false);
	}
	
	public DataSet join(DataManipulator d, String tableName, boolean flipped) throws HardyException {
		Table table = getTable(tableName);
		if(flipped)
			return join(table, d);
		return join(d, table);
	}

	public DataSet join(DataManipulator d1, DataManipulator d2) throws HardyException {
		return d1.join(d2);
	}
	
	public DataSet union(String tableName1, String tableName2) throws HardyException {
		Table table1 = getTable(tableName1);
		return union(table1, tableName2, false);
	}
	
	public DataSet union(DataManipulator d, String tableName, boolean flipped) throws HardyException {
		Table table = getTable(tableName);
		if(flipped)
			return union(table, d);
		return union(d, table);
	}

	public DataSet union(DataManipulator d1, DataManipulator d2) throws HardyException {
		return d1.union(d2);
	}
	
	public DataSet intersect(String tableName1, String tableName2) throws HardyException {
		Table table1 = getTable(tableName1);
		return intersect(table1, tableName2, false);
	}
	
	public DataSet intersect(DataManipulator d1, String tableName, boolean flipped) throws HardyException {
		Table table = getTable(tableName);
		if(flipped)
			return intersect(table, d1);
		return intersect(d1, table);
		
	}
	
	public DataSet intersect(DataManipulator d1, DataManipulator d2) throws HardyException {
		return d1.intersect(d2);
	}
	
	public DataSet minus(String tableName1, String tableName2) throws HardyException {
		Table table1 = getTable(tableName1);
		return minus(table1, tableName2, false);
	}
	
	public DataSet minus(DataManipulator d, String tableName, boolean flipped) throws HardyException {
		Table table = getTable(tableName);
		if(flipped)
			return minus(table, d);
		return minus(d, table);
	}

	public DataSet minus(DataManipulator d1, DataManipulator d2) throws HardyException {
		return d1.minus(d2);
	}

	public DataSet symmetricDifference(String tableName1, String tableName2) throws HardyException {
		Table table1 = getTable(tableName1);
		return symmetricDifference(table1, tableName2, false);
	}
	
	public DataSet symmetricDifference(DataManipulator d, String tableName, boolean flipped) throws HardyException {
		Table table = getTable(tableName);
		if(flipped)
			return symmetricDifference(table, d);
		return symmetricDifference(d, table);
	}
	
	public DataSet symmetricDifference(DataManipulator d1, DataManipulator d2) throws HardyException {
		return d1.symmetricDifference(d2);
	}

	public DataSet projectTable(String tableName, String fields) throws HardyException {
		Table t = getTable(tableName);
		return t.projectOver(fields);
	}

	public DataSet projectData(DataManipulator d1, String fields) throws HardyException {
		return d1.projectOver(fields);
	}
	
	public void serialize(ObjectOutputStream backup) throws HardyException {
		try {
			for(Table t: tables.values())
				t.updateDataSet();
			backup.writeObject(tables);
			backup.close();
		} catch (IOException e) {
			throw new HardyException("Stupid IOException", e);
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public void deserialize(ObjectInputStream restore) throws HardyException {
		try {
			tables = (HashMap<String, Table>) restore.readObject();
			for(Table t: tables.values())
				t.writeEntireFile();
			restore.close();
			XmlSaver.save();
		} catch (IOException e) {
			throw new HardyException("Stupid IOException", e);
		} catch (ClassNotFoundException e) {
			throw new HardyException("Invalid class in file", e);
		}
		
	}

	public void defineIndex(String tableName, String fieldName) throws HardyException {
		Table table = getTable(tableName);
		table.writeBinaryTree(fieldName);
	}
	
}
