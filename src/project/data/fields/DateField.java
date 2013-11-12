package project.data.fields;

import java.io.RandomAccessFile;

import project.data.HardyException;
import project.data.values.DateValue;


/**
 * The Class DateField stores the name of
 * the date field.
 */
public class DateField extends AbstractField {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new date field.
	 *
	 * @param n the name
	 */
	public DateField(String n) {
		super(n, "date");
	}

	@Override
	public DateValue readFile(RandomAccessFile reader, long pos) throws HardyException {
		seekToReadPos(reader, pos);
		return new DateValue(reader);
	}

	@Override
	public DateValue toValue(String datum) throws HardyException {
		return new DateValue(datum);
	}

	@Override
	public int getBinarySize() {
		return 8;
	}

}