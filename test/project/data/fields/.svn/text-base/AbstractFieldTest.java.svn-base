package project.data.fields;

import static org.junit.Assert.*;
import org.junit.Test;

import project.data.fields.AbstractField;

public abstract class AbstractFieldTest {

	protected AbstractField[] fields;
	protected String[] expected;
	protected AbstractField[] goodFields;
	protected AbstractField[] badFields;
	protected String xmlField;

	@Test
	public void testToString() {
		for(int i=0; i<fields.length; i++)
			assertTrue(fields[i].toString().equals(expected[i]));
	}
	
	public abstract void setUp() throws Exception;
	
	@Test
	public void testIsMatch() {
		for(int i=0; i<fields.length; i++) {
			assertTrue(fields[i].getName().equals((goodFields[i])));
			assertFalse(fields[i].getName().equals(badFields[i]));
		}
	}
	
	@Test
	public void testToXmlString() {
		assertTrue(fields[0].toXmlString().equals(xmlField));
	}

}