package project.data.fields;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;

import project.data.BSTree;
import project.data.HardyException;
import project.data.Table;
import project.data.Table.DataSet;
import project.data.persistor.XmlEncoder;
import project.data.values.AbstractValue;


/**
 * This class sets up the different field types.
 */
public abstract class AbstractField implements Serializable {
	
	private static final long serialVersionUID = 1L;
	protected String name;
	protected String type;
	protected int readPos;
	protected BSTree tree = null;
	
	/**
	 * Instantiates a new abstract field.
	 *
	 * @param n the name
	 * @param t the type
	 */
	public AbstractField(String n, String t) {
		name = n.toLowerCase().trim();
		type = t.toLowerCase().trim();
	}
	
	public boolean isName(String s) {
		return name.equalsIgnoreCase(s);
	}
	
	public String getName() {
		return name;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return name + " " + type;
	}
	
	/**
	 * To xml string.
	 *
	 * @return the string
	 */
	public String toXmlString() {
		String s = "\t\t\t<field>\n\t\t\t\t<fieldName>" + XmlEncoder.encode(name) + "</fieldName>\n";
		s += "\t\t\t\t<fieldType>" + XmlEncoder.encode(type) + "</fieldType>\n\t\t\t</field>\n";
		return s;
	}
	
	public void setReadPos(int readPos) {
		this.readPos = readPos;
	}
	
	public int getReadPos() {
		return readPos;
	}
	
	protected void seekToReadPos(RandomAccessFile writer, long pos) throws HardyException {
		try {
			writer.seek(pos+readPos);
		} catch (IOException e) {
			throw new HardyException("Stupid IOException", e);
		}
	}
	
	@SuppressWarnings("rawtypes")
	public void write(RandomAccessFile writer, long pos, String value) throws HardyException {
			seekToReadPos(writer, pos);
			AbstractValue val = toValue(value);
			val.writeFile(writer);
			if(tree != null) {
				val.setFilePointer(pos+readPos);
				tree.insert(writer, val);
			}
	}
	
	public void makeBSTree(Table table, RandomAccessFile reader) throws HardyException {
		setTree(table);
		tree.deleteFileContents();
		DataSet d = table.projectOver(name);
		for(int i=0; i<d.getNumRows(); i++)
			tree.insert(reader, d.getRow(i).getValue(0));
	}
	
	public void deleteBSTFile() throws HardyException {
		if(tree != null)
			tree.deleteFile();
	}
	
	public BSTree getTree() {
		return tree;
	}
	
	public void setTree(Table table) throws HardyException {
		tree = new BSTree(table, this);
	}
	
	@SuppressWarnings("rawtypes")
	public abstract AbstractValue readFile(RandomAccessFile reader, long pos) throws HardyException;
	@SuppressWarnings("rawtypes")
	public abstract AbstractValue toValue(String datum) throws HardyException;
	public abstract int getBinarySize();

}
