package project.data.values;

import java.io.IOException;
import java.io.RandomAccessFile;

import project.data.HardyException;


public class CharValue extends AbstractValue<CharValue> {
	
	private static final long serialVersionUID = 1L;
	private String value;
	
	public CharValue(String s, int size) throws HardyException {
		s.trim();
		s = s.substring(1, s.length()-1);
		if(s.length()==size) {
			value = s;
		} else throw new HardyException("Values inconsistent with table");
	}
	
	public CharValue(RandomAccessFile reader, int size) throws HardyException {
		try {
			setFilePointer(reader.getFilePointer());
			value = "";
			for(int i=0; i<size; i++) {
				value += reader.readChar();
			}
		} catch (IOException e) {
			throw new HardyException("Stupid IOException", e);
		}
	}

	@Override
	public void writeFile(RandomAccessFile writer) throws HardyException {
		try {
			writer.writeChars(value);
		} catch (IOException e) {
			throw new HardyException("Stupid IOException", e);
		}
	}
	
	@Override
	public String toString() {
		return value;
	}

	@Override
	public int compareTo(CharValue c) {
		return this.value.compareTo(c.value);
	}
	
	@Override
	public int hashCode() {
		return value.hashCode();
	}
	
}
