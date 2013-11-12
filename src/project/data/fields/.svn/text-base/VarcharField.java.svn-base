package project.data.fields;

import java.io.RandomAccessFile;

import project.data.HardyException;
import project.data.values.VarcharValue;


/**
 * The Class VarcharField stores the name of the
 * given varchar field.
 */
public class VarcharField extends AbstractField {

	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new varchar field.
	 *
	 * @param n the name
	 */
	public VarcharField(String n) {
		super(n, "varchar");
	}

	@Override
	public VarcharValue readFile(RandomAccessFile reader, long pos) throws HardyException {
		seekToReadPos(reader, pos);
		return new VarcharValue(reader);
	}
	
	@Override
	public VarcharValue toValue(String datum) throws HardyException {
		return new VarcharValue(datum);
	}
	
	@Override
	public int getBinarySize() {
		return 8;
	}

}