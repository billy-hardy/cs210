package project.data.values;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import project.data.DatabaseStrings;
import project.data.HardyException;


public class VarcharValue extends AbstractValue<VarcharValue> implements DatabaseStrings {
	
	private static final long serialVersionUID = 1L;
	private String value;
	private static RandomAccessFile stringWriter;
	
	static {
		try {
			makeRAF();
		} catch (HardyException e) { }
	}
	
	public static void makeRAF() throws HardyException {
		try {
			stringWriter = new RandomAccessFile(new File(databaseStorage, varcharFile), "rw");
		} catch (FileNotFoundException e) {
			throw new HardyException("Stupid IOException", e);
		}
	}
	
	public VarcharValue(String s) throws HardyException {
		value.trim();
		value = s.substring(1, s.length()-1);
	}
	
	public VarcharValue(RandomAccessFile reader) throws HardyException {
		try {
			stringWriter.seek(reader.readLong());
			value = stringWriter.readUTF();
		} catch (IOException e) {
			throw new HardyException("Stupid IOException", e);
		}
	}


	@Override
	public void writeFile(RandomAccessFile writer) throws HardyException {
		try {
			setFilePointer(writer.getFilePointer());
			stringWriter.seek(stringWriter.length());
			writer.writeLong(stringWriter.getFilePointer());
			stringWriter.writeUTF(value);
		} catch (IOException e) {
			throw new HardyException("Stupid IOException", e);
		}
	}
	
	@Override
	public String toString() {
		return value;
	}

	@Override
	public int compareTo(VarcharValue o) {
		return this.value.compareTo(o.value);
	}
	
	@Override
	public int hashCode() {
		return value.hashCode();
	}
	
}
