package project.data;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import project.data.fields.*;
import project.data.persistor.XmlEncoder;
import project.data.values.AbstractValue;

/**
 * The Class Table stores the table's name and field list and formats the table
 * for printout.
 */
public class Table implements DatabaseStrings, DataManipulator, Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private ArrayList<AbstractField> fields = new ArrayList<AbstractField>();
	private ArrayList<String> fieldNames = new ArrayList<String>();
	private transient RandomAccessFile writer = null;
	private DataSet data;
	private int rowLength = 1;

	/**
	 * Instantiates a new table and calls the methods parseFields and
	 * checkAllFields.
	 * 
	 * @param n
	 *            the name
	 * @param f
	 *            the fieldString
	 * @throws HardyException
	 *             the hardy exception
	 */
	public Table(String n, String f) throws HardyException {
		this(n);
		addFields(f);
	}

	public Table(String n) throws HardyException {
		name = n.toLowerCase().trim();
		makeRAF();
	}
	
	private void makeRAF() throws HardyException {
		try {
			if(writer == null) {
				new File(databaseStorage).mkdir();
				writer = new RandomAccessFile(new File(databaseStorage, name), "rw");
			}
			writer.seek(0);
		} catch (IOException e) {
			throw new HardyException("Stupid IOException", e);
		}
	}

	/**
	 * Deletes the binary file associated with this table.
	 */
	public void deleteBinaryFile() throws HardyException {
		try {
			writer.close();
			new File(databaseStorage, name).delete();
			for(AbstractField f: fields)
				f.deleteBSTFile();
		} catch (IOException e) {
			throw new HardyException("Stupid IOException", e);
		}
	}

	/**
	 * Parses the fields from the input.
	 * 
	 * @param f
	 *            the fieldString
	 * @throws HardyException
	 *             the hardy exception
	 */
	public void addFields(String f) throws HardyException {
		for (String s : f.split(",")) {
			AbstractField temp = createField(s);
			checkField(temp);
			temp.setReadPos(rowLength);
			rowLength += temp.getBinarySize();
			fields.add(temp);
			fieldNames.add(name+"."+temp.getName());
		}
	}

	/**
	 * Creates the field of the given type.
	 * 
	 * @param f
	 *            the field name and type
	 * @return the new field
	 * @throws HardyException
	 *             the hardy exception
	 */
	public AbstractField createField(String f) throws HardyException {
		String[] all = f.trim().split(" ");
		int t = all.length-1;
		Pattern p = Pattern.compile("(\\S*) +char *\\( *(\\d+) *\\)", Pattern.CASE_INSENSITIVE);
		Matcher matcher = p.matcher(f.trim());
		if (matcher.matches())
			return new CharField(matcher.group(1) + " " + matcher.group(2));
		else if (all[t].equals("boolean"))
			return new BooleanField(all[0]);
		else if (all[t].equals("date"))
			return new DateField(all[0]);
		else if (all[t].equals("varchar"))
			return new VarcharField(all[0]);
		else if (all[t].equals("real"))
			return new RealField(all[0]);
		else if (all[t].equals("integer"))
			return new IntegerField(all[0]);
		throw new HardyException("Invalid field entry, table not created");
	}

	/**
	 * Check for duplicate field names.
	 * 
	 * @throws HardyException
	 *             the hardy exception
	 */
	private void checkField(AbstractField f1) throws HardyException {
		for(AbstractField f2 : fields) {
			if(f1.getName().equals(f2.getName()))
				throw new HardyException("Duplicate field names not allowed");
		}
	}
	
	public int getRowLength() {
		return rowLength;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String s = "Table " + name + ":";
		for (AbstractField f : fields)
			s += " " + f + ",";
		return s.substring(0, s.length() - 1);
	}

	/**
	 * To xml string.
	 * 
	 * @return the string
	 */
	public String toXmlString() {
		String s = "\t<table>\n\t\t<tableName>" + XmlEncoder.encode(name)
				+ "</tableName>\n\t\t<fields>\n";
		for(AbstractField f: fields)
			s += f.toXmlString();
		s += "\t\t\t<trees>\n";
		for(AbstractField f: fields) {
			BSTree tree = f.getTree();
			if(tree != null)
				s += tree.toXmlString();
		}
		return s + "\t\t\t</trees>\n\t\t</fields>\n\t</table>\n";
	}

	/**
	 * Changes the table name and the associated binary file names.
	 */
	public void setName(String newName) {
		new File(databaseStorage, name).renameTo(new File(databaseStorage, newName.toLowerCase().trim()));
		name = newName;
		for(AbstractField f: fields) {
			BSTree tree = f.getTree();
			if(tree != null)
				tree.renameFile();
		}
	}

	public String getName() {
		return name;
	}
	
	/*
	 * Writes the data to the binary file.
	 */
	public void writeFile(String input) throws HardyException {
		try {
			String[] in = input.split(",");
			if(in.length != fields.size())
				throw new HardyException("Incorrect number of fields");
			long fp = writer.length();
			writer.seek(fp);
			try {
				writer.writeBoolean(true);
				for(int i = 0; i < in.length; i++)
					fields.get(i).write(writer, fp, in[i].trim());
			} catch(HardyException e) {
				writer.setLength(fp);
				throw e;
			}
//			ArrayList<AbstractValue> vals = new ArrayList<AbstractValue>();
//			for(int i = 0; i < in.length; i++) {
//				AbstractValue val = fields.get(i).toValue(in[i].trim());
//				vals.add(val);
//			}
//			writer.writeBoolean(true);
//			new Row(vals).write(writer);
		} catch(IOException e) {
			throw new HardyException("Stupid IOException", e);
		}
	}

	public Matcher parseMatcher(String toBeParsed) {
		Pattern pattern = Pattern.compile("([\\S&&[^><!=]]*) *(<|>|<=|>=|=|!=) *([^=><!]+?)");
		return pattern.matcher(toBeParsed.trim());
	}
	
	private int parseField(String fname) throws HardyException {
		for(int i=0; i<fields.size(); i++) {
			if(fields.get(i).isName(fname))
				return i;
		}
		throw new HardyException("Invalid field");
	}
	
	@SuppressWarnings("rawtypes")
	public ArrayList<Long> getRows(String where, boolean delete) throws HardyException {
		try {
			ArrayList<Long> rows = new ArrayList<Long>();
			if(where == null) {
				long pos = 0l;
				while(pos < writer.length()) {
					writer.seek(pos);
					if(writer.readBoolean())
						rows.add(pos);
					pos += rowLength;
				}
				return rows;
			}
			Matcher matcher = parseMatcher(where);
			if(matcher.matches()) {
				String fname = matcher.group(1), relop = matcher.group(2), value = matcher.group(3);
				AbstractField field = fields.get(parseField(fname));
				AbstractValue val = field.toValue(value);
				BSTree tree = field.getTree();
				if(tree != null)
					rows = tree.getRows(writer, val, relop, delete);
				else {
					Long pos = 0l;
					while(pos < writer.length()) {
						writer.seek(pos);
						if(writer.readBoolean()) {
							AbstractValue readVal = field.readFile(writer, pos);
							if(compare(readVal, relop, val))
								rows.add(pos);
						}
						pos += rowLength;
					}
				}
				return rows;
			} else throw new HardyException("Invalid where clause");
		} catch(IOException e) {
			throw new HardyException("Stupid IOException", e);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static boolean compare(AbstractValue readVal, String relop, AbstractValue val) throws HardyException {
		int c = readVal.compareTo(val);
		if(relop.equals("="))
			return c == 0;
		if(relop.equals("!="))
			return c != 0;
		if(relop.equals(">="))
			return c >= 0;
		if(relop.equals("<="))
			return c <= 0;
		if(relop.equals(">"))
			return c > 0;
		if(relop.equals("<"))
			return c < 0;
		throw new HardyException("Invalid relop value");
	}

	public void updateFile(String set, String where) throws HardyException {
		Matcher matcher = parseMatcher(set);
		if(matcher.matches()) {
			String sName = matcher.group(1), sValue = matcher.group(3);
			AbstractField field = fields.get(parseField(sName));
			ArrayList<Long> rows = getRows(where, true);
			for(long l: rows)
				field.write(writer, l, sValue);
		} else throw new HardyException("Invalid set clause");
	}
	
	public void deleteRowsInFile(String where) throws HardyException {
		try {
			ArrayList<Long> rows = getRows(where, true);
			for(long l: rows) {
				writer.seek(l);
				writer.writeBoolean(false);
			}
		} catch (IOException e) {
			throw new HardyException("Stupid IOException", e);
		}
	}
	
	public DataSet walk(String fName, boolean reversed) throws HardyException {
		AbstractField field = fields.get(parseField(fName));
		BSTree tree = field.getTree();
		if(tree != null)
			return new DataSet(this, tree, reversed);
		throw new HardyException("Must have index defined on field of table");
	}

	@Override
	public DataSet selectRows(String where) throws HardyException {
		return new DataSet(this, where);
	}

	@Override
	public DataSet projectOver(String fieldList) throws HardyException {
		DataSet data = new DataSet(this);
		return data.projectOver(fieldList);
	}
	
	@Override
	public DataSet join(DataManipulator dm) throws HardyException {
		DataSet tData = selectRows(null);
		DataSet tData2 = dm.selectRows(null);
		return tData.join(tData2);
	}

	@Override
	public DataSet union(DataManipulator dm) throws HardyException {
		DataSet tData1 = selectRows(null);
		DataSet tData2 = dm.selectRows(null);
		return tData1.union(tData2);
	}

	@Override
	public DataSet intersect(DataManipulator dm) throws HardyException {
		DataSet tData1 = selectRows(null);
		DataSet tData2 = dm.selectRows(null);
		return tData1.intersect(tData2);
	}

	@Override
	public DataSet minus(DataManipulator dm) throws HardyException {
		DataSet tData1 = selectRows(null);
		DataSet tData2 = dm.selectRows(null);
		return tData1.minus(tData2);
	}

	@Override
	public DataSet symmetricDifference(DataManipulator dm) throws HardyException {
		DataSet tData1 = selectRows(null);
		DataSet tData2 = dm.selectRows(null);
		return tData1.symmetricDifference(tData2);
	}
	
	public void updateDataSet() throws HardyException {
		data = selectRows(null);
	}

	public void writeEntireFile() throws HardyException {
		try {
			makeRAF();
			for(AbstractField f: fields) {
				BSTree tree = f.getTree();
				if(tree != null) {
					tree.makeRAF();
					f.makeBSTree(this, writer);
				}
			}
			writer.setLength(0);
			data.write(writer);
		} catch (IOException e) {
			throw new HardyException("Stupid IOException", e);
		}
	}

	public class DataSet implements DataManipulator, Serializable {
		
		private static final long serialVersionUID = 1L;
		private ArrayList<Row> rows = new ArrayList<Row>();
		private ArrayList<String> names = new ArrayList<String>();
		private ArrayList<AbstractField> fields= new ArrayList<AbstractField>();
		
		public DataSet(Table t) throws HardyException {
			this(t, null);
		}
		
		public DataSet(Table t, String where) throws HardyException {
			this.names.addAll(t.fieldNames);
			this.fields.addAll(t.fields);
			RandomAccessFile reader = t.writer;
			ArrayList<Long> positions = t.getRows(where, false);
			for(long l: positions)
				rows.add(new Row(reader, fields, l));
		}
	
		public DataSet(DataSet d, ArrayList<String> names, ArrayList<Integer> projections) throws HardyException {
			this.names.addAll(names);
			for(int i: projections)
				this.fields.add(d.fields.get(i));
			for(Row r: d.rows)
				rows.add(new Row(r, projections));
		}
	
		public DataSet(ArrayList<String> names, ArrayList<AbstractField> fields, ArrayList<Row> rows) {
			this.names = names;
			this.fields.addAll(fields);
			this.rows.addAll(rows);
		}
		
		public DataSet(Table t, BSTree tree, boolean reversed) throws HardyException {
			this.names.addAll(t.fieldNames);
			this.fields.addAll(t.fields);
			ArrayList<Long> positions;
			if(reversed)
				positions = tree.reverseOrderWalk();
			else positions = tree.inOrderWalk();
			for(long l: positions)
				rows.add(new Row(t.writer, fields, l));
		}
	
		@Override
		public DataSet projectOver(String fieldList) throws HardyException {
			String[] fArray = fieldList.split(",");
			ArrayList<String> names1 = new ArrayList<String>();
			ArrayList<Integer> projections = new ArrayList<Integer>();
			for(String s: fArray) {
				for(int i=0; i<names.size(); i++) {
					AbstractField f = fields.get(i);
					if(f.isName(s)) {
						names1.add(names.get(i));
						projections.add(i);
						break;
					}
				}
				if(names1.size() == 0)
					throw new HardyException("Invalid field");
			}
			return new DataSet(this, names1, projections);
		}
	
		@Override
		public DataSet join(DataManipulator dm) throws HardyException {
			ArrayList<String> names = new ArrayList<String>();
			ArrayList<AbstractField> fields = new ArrayList<AbstractField>();
			DataSet d = dm.selectRows(null);
			names.addAll(this.names);
			names.addAll(d.names);
			fields.addAll(this.fields);
			fields.addAll(d.fields);
			ArrayList<Row> dReturn = new ArrayList<Row>();
			for(Row r: this.rows) {
				for(Row r2: d.rows)
					dReturn.add(r.append(r2));
			}
			return new DataSet(names, fields, dReturn);
		}
	
		@Override
		public DataSet union(DataManipulator dm) throws HardyException {
			DataSet d = dm.selectRows(null);
			DataSet a = this.minus(d);
			a.rows.addAll(d.rows);
			return a;
		}
	
		@Override
		public DataSet intersect(DataManipulator d) throws HardyException {
			DataSet r = this.minus(d);
			r = this.minus(r);
			return r;
		}
	
		@Override
		public DataSet minus(DataManipulator dm) throws HardyException {
			DataSet d = dm.selectRows(null);
			rows.get(0).unionCompatible(d.rows.get(0));
			ArrayList<Row> rows1 = new ArrayList<Row>();
			ArrayList<Row> rows2 = new ArrayList<Row>();
			for(Row r: this.rows) {
				for(Row r2: d.rows) {
					if(r.equals(r2))
						rows1.add(r);
				}
			}
			rows2.addAll(this.rows);
			rows2.removeAll(rows1);
			return new DataSet(names, fields, rows2);
		}
	
		@SuppressWarnings("rawtypes")
		@Override
		public DataSet selectRows(String where) throws HardyException {
			if(where == null)
				return this;
			ArrayList<Row> rows2 = new ArrayList<Row>();
			Matcher matcher = parseMatcher(where);
			if(matcher.matches()) {
				String fname = matcher.group(1), relop = matcher.group(2), value = matcher.group(3);
				int i = parseField(fname);
				AbstractField field = fields.get(i);
				AbstractValue val = field.toValue(value);
				for(Row r: rows) {
					if(Table.compare(r.getValue(i), relop, val))
						rows2.add(r);
				}
				return new DataSet(names, fields, rows2);
			} throw new HardyException("Invalid where clause");
		}
		
		@Override
		public DataSet symmetricDifference(DataManipulator d) throws HardyException {
			DataSet a = this.union(d);
			DataSet b = this.intersect(d);
			return a.minus(b);
		}
		
		@Override
		public String toString() {
			String s = "";
			for(String f: names)
				s += f + "\t";
			for(Row r: rows)
				s += "\n" + r.toString();
			return s;
		}
	
		public Row getRow(int i) throws HardyException {
			try {
				return rows.get(i);
			} catch (IndexOutOfBoundsException e) {
				throw new HardyException("Index out of bounds", e);
			}
		}
		
		public int getNumRows() {
			return rows.size();
		}
	
		public void write(RandomAccessFile writer) throws HardyException {
			try {
				for(Row r: rows) {
					writer.writeBoolean(true);
					r.write(writer);
				}
			} catch (IOException e) {
				throw new HardyException("Stupid IOException", e);
			}
		}
	}

	public void writeBinaryTree(String fieldName) throws HardyException {
		AbstractField field = fields.get(parseField(fieldName));
		field.makeBSTree(this, writer);
	}
	
	public void instantiateBinaryTree(String fieldName) throws HardyException {
		AbstractField field = fields.get(parseField(fieldName));
		field.setTree(this);
	}
	
}