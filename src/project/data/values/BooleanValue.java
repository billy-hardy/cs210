package project.data.values;

import java.io.IOException;
import java.io.RandomAccessFile;

import project.data.HardyException;


public class BooleanValue extends AbstractValue<BooleanValue> {
	
	private static final long serialVersionUID = 1L;
	private Boolean value;
	
	public BooleanValue(String datum) throws HardyException {
		datum.toLowerCase().trim();
		if(datum.equals("true")) {
			value = true;
		} else if(datum.equals("false")) {
			value = false;
		} else throw new HardyException("Values inconsistent with table");
	}
	
	public BooleanValue(RandomAccessFile reader) throws HardyException {
		try {
			setFilePointer(reader.getFilePointer());
			value = reader.readBoolean();
		} catch (IOException e) {
			throw new HardyException("Stupid IOException", e);
		}
	}
	
	public BooleanValue(Boolean b) throws HardyException {
		value = b;
	}

	@Override
	public void writeFile(RandomAccessFile writer) throws HardyException {
		try {
			writer.writeBoolean(value);
		} catch (IOException e) {
			throw new HardyException("Stupid IOException", e);
		}
	}
	
	@Override
	public String toString() {
		return value.toString();
	}

	
	@Override
	public int compareTo(BooleanValue b) {
		if(b.value == this.value) {
			return 0;
		} else return 1;
	}
	
	@Override
	public int hashCode() {
		return value.hashCode();
	}
	
}
