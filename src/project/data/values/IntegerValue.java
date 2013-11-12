package project.data.values;

import java.io.IOException;
import java.io.RandomAccessFile;

import project.data.HardyException;


public class IntegerValue extends AbstractValue<IntegerValue> {
	
	private static final long serialVersionUID = 1L;
	private Integer value;
	
	public IntegerValue(Integer i) {
		value = i;
	}

	public IntegerValue(String datum) throws HardyException{
		try {
			value = Integer.parseInt(datum.trim());
		} catch (NumberFormatException e) {
			throw new HardyException("Values inconsistent with table");
		}
		
	}
	
	public IntegerValue(RandomAccessFile reader) throws HardyException {
		try {
			setFilePointer(reader.getFilePointer());
			value = reader.readInt();
		} catch (IOException e) {
			throw new HardyException("Stupid IOException", e);
		}
	}

	@Override
	public void writeFile(RandomAccessFile writer) throws HardyException {
		try {
			writer.writeInt(value);
		} catch (IOException e) {
			throw new HardyException("Stupid IOException", e);
		}
	}
	
	@Override
	public String toString() {
		return value.toString();
	}

	@Override
	public int compareTo(IntegerValue o) {
		return this.value-o.value;
	}
	
	@Override
	public int hashCode() {
		return value.hashCode();
	}

}
