package project.data.fields;

import java.io.RandomAccessFile;

import project.data.HardyException;
import project.data.values.RealValue;


/**
 * The Class RealField stores the name of the
 * given real field.
 */
public class RealField extends AbstractField {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new real field.
	 *
	 * @param n the name
	 */
	public RealField(String n) {
		super(n, "real");
	}
	
	@Override
	public RealValue readFile(RandomAccessFile reader, long pos) throws HardyException {
		seekToReadPos(reader, pos);
		return new RealValue(reader);
	}

	@Override
	public int getBinarySize() {
		return 8;
	}

	@Override
	public RealValue toValue(String datum) throws HardyException {
		return new RealValue(datum);
	}

}