package project.data.fields;

import java.io.RandomAccessFile;

import project.data.HardyException;
import project.data.values.IntegerValue;


/**
 * The Class IntegerField stores the name of the
 * integer field.
 */
public class IntegerField extends AbstractField {

	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new integer field.
	 *
	 * @param n the name
	 */
	public IntegerField(String n) {
		super(n, "integer");
	}

	@Override
	public IntegerValue readFile(RandomAccessFile reader, long pos) throws HardyException {
		seekToReadPos(reader, pos);
		return new IntegerValue(reader);
	}

	@Override
	public int getBinarySize() {
		return 4;
	}
	
	@Override
	public IntegerValue toValue(String datum) throws HardyException {
		return new IntegerValue(datum);
	}
	
}