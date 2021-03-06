package project.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.util.ArrayList;

import project.data.fields.AbstractField;
import project.data.persistor.XmlEncoder;
import project.data.values.AbstractValue;


public class BSTree implements DatabaseStrings, Serializable {

	private static final long serialVersionUID = 1L;
	private Table table;
	private String fileName;
	private AbstractField field;
	private transient RandomAccessFile writer;
	private long start;
	private static boolean debug = false;
	
	public BSTree(Table table, AbstractField f) throws HardyException {
		this.table = table;
		field = f;
		makeRAF();
		start = 0;
	}
	
	public void makeRAF() throws HardyException {
		try {
			fileName = table.getName()+".BST."+field.getName();
			writer = new RandomAccessFile(new File(databaseStorage, fileName), "rw");
		} catch (FileNotFoundException e) {
			throw new HardyException("Stupid IOException", e);
		}
	}
	
	public void renameFile() {
		String newFileName = table.getName()+".BST."+field.getName();
		new File(databaseStorage, fileName).renameTo(new File(databaseStorage, newFileName));
		fileName = newFileName;
	}
	
	public void deleteFile() throws HardyException {
		try {
			writer.close();
			new File(databaseStorage, table.getName()+".BST."+field.getName()).delete();
		} catch (IOException e) {
			throw new HardyException("Stupid IOException", e);
		}
	}
	
	public void deleteFileContents() throws HardyException {
		try {
			writer.setLength(0);
			start = 0;
		} catch (IOException e) {
			throw new HardyException("Stupid IOException", e);
		}
	}
	
	enum NODE { 
		THIS(0), PARENT(8), LCHILD(16), RCHILD(24);
		public long value;
		private NODE(long v) {
			value = v;
		}
	}
	
	public void set(NODE offSet, long node, long set) throws HardyException {
		try {
			writer.seek(node + offSet.value);
			writer.writeLong(set);
		} catch (IOException e) {
			throw new HardyException("Stupid IOException", e);
		}
	}
	
	public long get(NODE offSet, long node) throws HardyException {
		try {
			writer.seek(node + offSet.value);
			return writer.readLong();
		} catch (IOException e) {
			throw new HardyException("Stupid IOException", e);
		}
	}
	
	public long getLength() throws HardyException {
		try {
			return writer.length();
		}  catch (IOException e) {
			throw new HardyException("Stupid IOException", e);
		}
	}
	
	public long getFilePointer(RandomAccessFile r) throws HardyException {
		try {
			return r.getFilePointer();
		} catch (IOException e) {
			throw new HardyException("Stupid IOException", e);
		}
	}
	
	public void writeNode(long parent, long ofp, NODE offSet) throws HardyException {
		long l = getLength();
		set(offSet, parent, l);
		set(NODE.THIS, l, ofp);
		set(NODE.PARENT, l, parent);
		set(NODE.LCHILD, l, -1);
		set(NODE.RCHILD, l, -1);
	}
	
	public void writeFirstNode(long ofp) throws HardyException {
		set(NODE.THIS, 0, ofp);
		set(NODE.PARENT, 0, -1);
		set(NODE.LCHILD, 0, -1);
		set(NODE.RCHILD, 0, -1);
	}
	
	public long getMostChild(NODE offSet, long child) throws HardyException {
		long prev;
		do {
			prev = child;
			child = get(offSet, child);
		} while(child != -1);
		return prev;
	}
	
	public long getReplacement(NODE first, NODE offSet, long child) throws HardyException {
		long prev = child;
		child = get(first, child);
		while(child != -1) {
			prev = child;
			child = get(offSet, child);
		}
		return prev;
	}
	
	public ArrayList<Long> inOrderWalk() throws HardyException {
		return inOrderWalk(start);
	}
	
	public ArrayList<Long> inOrderWalk(long node) throws HardyException {
		ArrayList<Long> walk = new ArrayList<Long>();
		long l = get(NODE.LCHILD, node);
		if(l != -1)
			walk.addAll(inOrderWalk(l));
		walk.add(get(NODE.THIS, node));
		long r = get(NODE.RCHILD, node);
		if(r != -1)
			walk.addAll(inOrderWalk(r));
		return walk;
	}
	
	public ArrayList<Long> reverseOrderWalk() throws HardyException {
		return reverseOrderWalk(start);
	}
	
	public ArrayList<Long> reverseOrderWalk(long node) throws HardyException {
		ArrayList<Long> walk = new ArrayList<Long>();
		long l = get(NODE.RCHILD, node);
		if(l != -1)
			walk.addAll(reverseOrderWalk(l));
		walk.add(get(NODE.THIS, node));
		long r = get(NODE.LCHILD, node);
		if(r != -1)
			walk.addAll(reverseOrderWalk(r));
		return walk;
	}
	
	@SuppressWarnings("rawtypes")
	public long select(RandomAccessFile reader, AbstractValue val) throws HardyException {
		return select(reader, val, start);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public long select(RandomAccessFile reader, AbstractValue val, long node) throws HardyException {
		AbstractValue v = field.readFile(reader, get(NODE.THIS, node));
		int c = v.compareTo(val);
		if(c == 0)
			return node;
		if(c < 0) {
			long r = get(NODE.RCHILD, node);
			if(r != -1)
				return select(reader, val, r);
			return -1;
		}
		long l = get(NODE.LCHILD, node);
		if(l != -1)
			return select(reader, val, l);
		return -1;
	}
	
	@SuppressWarnings("rawtypes")
	public void insert(RandomAccessFile reader, AbstractValue val) throws HardyException {
		long ofp = val.getFilePointer() - field.getReadPos();
		insert(reader, val, start, ofp);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void insert(RandomAccessFile reader, AbstractValue val, long node, long ofp) throws HardyException {
		if(getLength() == 0)
			writeFirstNode(ofp);
		else {
			AbstractValue v = field.readFile(reader, get(NODE.THIS, node));
			int c = val.compareTo(v);
			if(c <= 0) {
				long l = get(NODE.LCHILD, node);
				if(l == -1)
					writeNode(node, ofp, NODE.LCHILD);
				else insert(reader, val, l, ofp);
			} else {
				long l = get(NODE.RCHILD, node);
				if(l == -1)
					writeNode(node, ofp, NODE.RCHILD);
				else insert(reader, val, l, ofp);
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public boolean delete(RandomAccessFile reader, AbstractValue val) throws HardyException {
		return delete(reader, val, start);
	}
	
	@SuppressWarnings("rawtypes")
	public boolean delete(RandomAccessFile reader, AbstractValue val, long node) throws HardyException { 
		long l = select(reader, val, node);
		if(l == -1)
			return false;
		long replacement = getReplacement(NODE.RCHILD, NODE.LCHILD, node);
		if(replacement != -1) {
			set(NODE.THIS, l, get(NODE.THIS, replacement));
			if(replacement == get(NODE.RCHILD, l)) {
				set(NODE.RCHILD, l, get(NODE.RCHILD, replacement));
				if(get(NODE.RCHILD, replacement) != -1)
					set(NODE.PARENT, get(NODE.RCHILD, replacement), l);
			} else {
				set(NODE.PARENT, get(NODE.RCHILD, replacement), get(NODE.PARENT, replacement));
				set(NODE.LCHILD, get(NODE.PARENT, replacement), get(NODE.RCHILD, replacement));
			}
			return true;
		}
		replacement = getReplacement(NODE.LCHILD, NODE.RCHILD, node);
		if(replacement != -1) {
			set(NODE.THIS, l, get(NODE.THIS, replacement));
			if(replacement == get(NODE.LCHILD, l)) {
				set(NODE.LCHILD, l, get(NODE.LCHILD, replacement));
				if(get(NODE.LCHILD, replacement) != -1)
					set(NODE.PARENT, get(NODE.LCHILD, replacement), l);
			} else {
				if(get(NODE.LCHILD, replacement) != -1)
					set(NODE.PARENT, get(NODE.LCHILD, replacement), get(NODE.PARENT, replacement));
				set(NODE.LCHILD, get(NODE.PARENT, replacement), get(NODE.LCHILD, replacement));
			}
			return true;
		}
		long parent = get(NODE.PARENT, l);
		if(parent == -1) {
			deleteFileContents();
			return true;
		}
		if(get(NODE.LCHILD, parent) == l)
			set(NODE.LCHILD, parent, -1);
		else set(NODE.RCHILD, parent, -1);
		return true;
	}
	
	public void setNewStart(long node) throws HardyException {
		if(start == node) {
			long child = get(NODE.LCHILD, node);
			if(child != -1)
				start = child;
			else {
				child = get(NODE.RCHILD, node);
				if(child != -1)
					start = child;
				else start = 0;
			}
		}
	}
	
	enum INEQUALITY { LTE, LT, E, GT, GTE;
		public static INEQUALITY parseSymbol(String relop) {
			if(relop.equals("="))
				return E;
			if(relop.equals("<="))
				return LTE;
			if(relop.equals("<"))
				return LT;
			if(relop.equals(">="))
				return GTE;
			return GT;
		}
	}
	
	@SuppressWarnings("rawtypes")
	public ArrayList<Long> getRows(RandomAccessFile reader, AbstractValue val, String relop, boolean delete) throws HardyException {
		INEQUALITY symbol = INEQUALITY.parseSymbol(relop);
		return getRows(reader, val, start, symbol, delete);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ArrayList<Long> getRows(RandomAccessFile reader, AbstractValue val, long node, INEQUALITY symbol, boolean delete) throws HardyException {
		ArrayList<Long> rows = new ArrayList<Long>();
		long t = get(NODE.THIS, node);
		long lc = get(NODE.LCHILD, node);
		long rc = get(NODE.RCHILD, node);
		AbstractValue readVal = field.readFile(reader, t);
		if(debug)
			System.out.println("Checking row " + ((t/table.getRowLength())+1));
		int c = readVal.compareTo(val);
		switch(symbol) {
		case E:
			if(c == 0) {
				rows.add(t);
				if(delete)
					delete(reader, readVal);
			} if(c < 0) {
				if(rc != -1)
					rows.addAll(getRows(reader, val, rc, symbol, delete));
			} else {
				if(lc != -1)
					rows.addAll(getRows(reader, val, lc, symbol, delete));
			}
			break;
		case LTE:
			if(c<=0) {
				rows.add(t);
				if(delete)
					delete(reader, readVal);
				if(rc != -1)
					rows.addAll(getRows(reader, val, rc, symbol, delete));
			} if(lc != -1)
				rows.addAll(getRows(reader, val, lc, symbol, delete));
			break;
		case LT:
			if(c<0) {
				rows.add(t);
				if(delete)
					delete(reader, readVal);
				if(rc != -1)
					rows.addAll(getRows(reader, val, rc, symbol, delete));
			} if(lc != -1)
				rows.addAll(getRows(reader, val, lc, symbol, delete));
			break;
		case GTE:
			if(c>=0) {
				rows.add(t);
				if(delete)
					delete(reader, readVal);
				if(lc != -1)
					rows.addAll(getRows(reader, val, lc, symbol, delete));
			} if(rc != -1)
				rows.addAll(getRows(reader, val, rc, symbol, delete));
			break;
		case GT:
			if(c>0) {
				rows.add(t);
				if(delete)
					delete(reader, readVal);
				if(lc != -1)
					rows.addAll(getRows(reader, val, lc, symbol, delete));
			} if(rc != -1)
				rows.addAll(getRows(reader, val, rc, symbol, delete));
			break;
		}
		return rows;
	}
	
	public static void setToDebug() {
		debug = true;
	}
	
	public String toXmlString() {
		return "\t\t\t\t<BSTree>"+XmlEncoder.encode(field.getName())+"</BSTree>\n";
	}

}
