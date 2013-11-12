package project.data;

import project.data.Table.DataSet;

public interface DataManipulator {

	public DataSet projectOver(String fieldList) throws HardyException;
	public DataSet join(DataManipulator d) throws HardyException;
	public DataSet union(DataManipulator d) throws HardyException;
	public DataSet intersect(DataManipulator d) throws HardyException;
	public DataSet minus(DataManipulator d) throws HardyException;
	public DataSet selectRows(String where) throws HardyException;
	public DataSet symmetricDifference(DataManipulator d) throws HardyException;
	
}