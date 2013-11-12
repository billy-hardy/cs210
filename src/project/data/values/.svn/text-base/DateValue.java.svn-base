package project.data.values;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import project.data.HardyException;



public class DateValue extends AbstractValue<DateValue> {

	private static final long serialVersionUID = 1L;
	private static SimpleDateFormat date = new SimpleDateFormat("MM/dd/yyyy");
	private Long value;
	
	public DateValue(Long l) {
		value = l;
	}
	
	
	public DateValue(RandomAccessFile reader) throws HardyException {
		try {
			setFilePointer(reader.getFilePointer());
			value = reader.readLong();
		} catch (IOException e) {
			e.printStackTrace();
			throw new HardyException("Stupid IOException", e);
		}
	}
	public DateValue(String s) throws HardyException {
		try {
			s.toLowerCase().trim();
			s=s.replace("'", "");
			value = date.parse(s).getTime();
		} catch (ParseException e) {
			throw new HardyException("Values inconsistent with table");
		}
	}


	@Override
	public void writeFile(RandomAccessFile writer) throws HardyException {
		try {
			writer.writeLong(value);
		} catch (IOException e) {
			throw new HardyException("Stupid IOException", e);
		}
	}
	
	@Override
	public String toString() {
		if(value == null) {
			return "";
		}
		return date.format(value);
	}


	@Override
	public int compareTo(DateValue o) {
		return this.value.compareTo(o.value);
	}
	
	@Override
	public int hashCode() {
		return value.hashCode();
	}

}
