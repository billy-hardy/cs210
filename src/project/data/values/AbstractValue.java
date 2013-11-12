package project.data.values;

import java.io.RandomAccessFile;
import java.io.Serializable;

import project.data.HardyException;


public abstract class AbstractValue<T extends AbstractValue<?>> implements Comparable<T>, Serializable {

	private static final long serialVersionUID = 1L;
	private long filePointer;
	public abstract void writeFile(RandomAccessFile writer) throws HardyException;
	@Override
	public abstract int compareTo(T abstractValue);
	
	public void setFilePointer(long l) {
		filePointer = l;
	}
	
	public long getFilePointer() {
		return filePointer;
	}
}
