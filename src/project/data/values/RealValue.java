package project.data.values;

import java.io.IOException;
import java.io.RandomAccessFile;

import project.data.HardyException;


public class RealValue extends AbstractValue<RealValue> {
	
	private static final long serialVersionUID = 1L;
	private Double value;

	public RealValue(Double d) {
		value = d;
	}

	public RealValue(String datum) throws HardyException {
		try {
			value = Double.parseDouble(datum.trim());
		} catch (NumberFormatException e) {
			throw new HardyException("Values inconsistent with table");
		}
	}
	
	public RealValue(RandomAccessFile reader) throws HardyException {
		try {
			setFilePointer(reader.getFilePointer());
			value = reader.readDouble();
		} catch (IOException e) {
			throw new HardyException("Stupid IOException", e);
		}
	}

	@Override
	public void writeFile(RandomAccessFile writer) throws HardyException {
		try {
			setFilePointer(writer.getFilePointer()-1);
			writer.writeDouble(value);
		} catch (IOException e) {
			throw new HardyException("Stupid IOException", e);
		}
	}
	
	@Override
	public String toString() {
		return value.toString();
	}

	@Override
	public int compareTo(RealValue o) {
		return value.compareTo(o.value);
	}
	
	@Override
	public int hashCode() {
		return value.hashCode();
	}

}
