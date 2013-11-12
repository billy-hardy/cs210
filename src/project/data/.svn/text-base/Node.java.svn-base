package project.data;

import java.io.IOException;
import java.io.RandomAccessFile;

import project.data.values.AbstractValue;

public class Node {
	
	private final long NULLNODE = -1l;
	
	@SuppressWarnings("rawtypes")
	private AbstractValue value;
	private long filePointer;
	private Node parent;
	private Node rChild;
	private Node lChild;
	private int num;
	
	@SuppressWarnings("rawtypes")
	public Node(AbstractValue val, long fp) {
		value = val;
		filePointer = fp;
		setParent(null);
		setrChild(null);
		setlChild(null);
	}
	
	@SuppressWarnings("rawtypes")
	public AbstractValue getVal() {
		return value;
	}
	
	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public Node getrChild() {
		return rChild;
	}
	
	public void setrChild(Node r) {
		rChild = r;
	}

	public Node getlChild() {
		return lChild;
	}
	
	public void setlChild(Node l) {
		lChild = l;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Node select(AbstractValue v) {
		int c = value.compareTo(v);
		if(c == 0)
			return this;
		if(c < 0) {
			if(rChild == null) 
				return null;
			return rChild.select(v);
		}
		if(lChild == null)
			return null;
		return lChild.select(v);
	}
	
	public Node max() {
		if(rChild == null)
			return this;
		return rChild.max();
	}
	
	public Node min() {
		if(lChild == null)
			return this;
		return lChild.max();
	}
	
	public void inOrderWalk() {
		if(lChild != null)
			lChild.inOrderWalk();
		System.out.print(value + " ");
		if(rChild != null)
			rChild.inOrderWalk();
	}
	
	public void reverseInOrderWalk() {
		if(rChild != null)
			rChild.reverseInOrderWalk();
		System.out.print(value + " ");
		if(lChild != null)
			lChild.reverseInOrderWalk();
	}
	
	public Node successor() {
		if(rChild == null)
			return null;
		return rChild.min();
	}
	
	public Node predecessor() {
		if(lChild == null)
			return null;
		return lChild.max();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void insert(AbstractValue val, long fp) {
		int c = value.compareTo(val);
		Node n = new Node(val, fp);
		if(c < 0) {
			if(rChild == null) {
				setrChild(n);
				n.setParent(this);
			} else rChild.insert(val, fp);
		} else {
			if(lChild == null) {
				setlChild(n);
				n.setParent(this);
			} else lChild.insert(val, fp);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean delete(Node d) {
		int c = value.compareTo(d.value);
		if(c == 0) {
			Node s = successor();
			AbstractValue temp = s.value;
			s.value = value;
			value = temp;
			s.rChild.setParent(s.parent);
			s.parent.setlChild(s);
			return true;
		}
		if(c < 0) {
			if(rChild != null)
				return rChild.delete(d);
			else return false;
		}
		if(lChild != null)
			return lChild.delete(d);
		return false;
	}
	
	private int getNumOfLeftChildren() {
		int n = 1;
		if(lChild != null)
			n += lChild.getNumNodes();
		return n;
	}
	
	private int getNumNodes() {
		int n = 1;
		if(lChild != null)
			n += lChild.getNumNodes();
		if(rChild != null)
			n += rChild.getNumNodes();
		return n;
	}
	
	public void write(RandomAccessFile writer, int i) throws HardyException {
		try {
			writer.writeLong(filePointer);
			if(parent != null)
				writer.writeLong((parent.num)*32);
			else writer.writeLong(NULLNODE);
			if(lChild != null)
				writer.writeLong((lChild.num)*32);
			else writer.writeLong(NULLNODE);
			if(rChild != null)
				writer.writeLong((rChild.num)*32);
			else writer.writeLong(NULLNODE);
			if(lChild != null)
				lChild.write(writer, ++i);
			if(rChild != null) {
				i += getNumOfLeftChildren();
				rChild.write(writer, ++i);
			}
		} catch (IOException e) {
			throw new HardyException("Stupid IOException", e);
		}
	}



}
