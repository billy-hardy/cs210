package project.data.fields;

import java.io.RandomAccessFile;

import project.data.HardyException;
import project.data.values.BooleanValue;


/**
 * This class stores the name of
 * the boolean field.
 */
public class BooleanField extends AbstractField {

	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new boolean field.
	 *
	 * @param n the name
	 */
	public BooleanField(String n) {
		super(n, "boolean");
	}

	@Override
	public BooleanValue readFile(RandomAccessFile reader, long pos) throws HardyException {
		seekToReadPos(reader, pos);
		return new BooleanValue(reader);
	}
	
	@Override
	public BooleanValue toValue(String datum) throws HardyException {
		return new BooleanValue(datum);
	}

	@Override
	public int getBinarySize() {
		return 1;
	}

}