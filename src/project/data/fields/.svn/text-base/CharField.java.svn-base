package project.data.fields;

import java.io.RandomAccessFile;

import project.data.HardyException;
import project.data.persistor.XmlEncoder;
import project.data.values.CharValue;



/**
 * This class stores the name and
 * size of the charfield.
 */
public class CharField extends AbstractField {
	
	private static final long serialVersionUID = 1L;
	private int size;
	
	/**
	 * Instantiates a new char field.
	 *
	 * @param n the name and type
	 */
	public CharField(String n) {
		super(n.split(" ")[0], "char");
		size = Integer.parseInt(n.split(" ")[1]);
	}
	
	public String toString() {
		return name + " " + type + "(" + size + ")";
	}
	
	@Override
	public String toXmlString() {
		String s = "\t\t\t<field>\n\t\t\t\t<fieldName>" + XmlEncoder.encode(name) + "</fieldName>\n";
		s += "\t\t\t\t<fieldType>" + XmlEncoder.encode(type) + "</fieldType>\n";
		s += "\t\t\t\t<fieldSize>" + size + "</fieldSize>\n\t\t\t</field>\n";
		return s;
	}
	
	
	@Override
	public CharValue readFile(RandomAccessFile reader, long pos) throws HardyException {
		seekToReadPos(reader, pos);
		return new CharValue(reader, size);
	}

	@Override
	public CharValue toValue(String datum) throws HardyException {
		return new CharValue(datum, size);
	}

	@Override
	public int getBinarySize() {
		return 2*size;
	}

}