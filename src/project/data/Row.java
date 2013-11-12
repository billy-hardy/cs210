package project.data;

import java.io.RandomAccessFile;
import java.io.Serializable;
import java.util.ArrayList;

import project.data.fields.AbstractField;
import project.data.values.AbstractValue;
import project.data.values.VarcharValue;

public class Row implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("rawtypes")
	private ArrayList<AbstractValue> values = new ArrayList<AbstractValue>();
	
	public Row(RandomAccessFile reader, ArrayList<AbstractField> fields, long pos) throws HardyException {
		for(AbstractField f: fields)
			values.add(f.readFile(reader, pos));
	}

	public Row(Row r, ArrayList<Integer> positions) {
		for(Integer i: positions)
			values.add(r.values.get(i));
	}

	@SuppressWarnings("rawtypes")
	public Row(ArrayList<AbstractValue> vals) {
		values.addAll(vals);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Row) || o == null)
			return false;
		Row r = (Row) o;
		for(int i=0; i<values.size(); i++) {
			if(values.get(i).compareTo(r.values.get(i)) != 0)
				return false;
		}
		return true;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public int hashCode() {
		int h = 0;
		for(AbstractValue v: values)
			h *= 37 + v.hashCode();
		return h;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public String toString() {
		String s = "";
		for(AbstractValue val: values)
			s += val.toString() + "\t";
		return s;
	}

	public void unionCompatible(Row r) throws HardyException {
		if(this.values.size() != r.values.size())
			throw new HardyException("Data sets not union compatible");
		for(int i=0; i<values.size(); i++) {
			if(values.get(i).getClass() != r.values.get(i).getClass())
				throw new HardyException("Data sets not union compatible");
		}
	}

	@SuppressWarnings("rawtypes")
	public Row append(Row r2) {
		ArrayList<AbstractValue> vals = new ArrayList<AbstractValue>();
		vals.addAll(this.values);
		vals.addAll(r2.values);
		return new Row(vals);
	}

	@SuppressWarnings("rawtypes")
	public AbstractValue getValue(int i) {
		return values.get(i);
	}

	@SuppressWarnings("rawtypes")
	public void write(RandomAccessFile writer) throws HardyException {
		VarcharValue.makeRAF();
		for(AbstractValue v: values)
			v.writeFile(writer);
	}
}