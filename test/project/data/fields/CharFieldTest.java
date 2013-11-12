package project.data.fields;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import project.data.fields.AbstractField;
import project.data.fields.CharField;
import project.data.fields.IntegerField;
import project.data.fields.RealField;
import project.data.fields.VarcharField;

public class CharFieldTest extends AbstractFieldTest {

	@Override
	@Before
	public void setUp() throws Exception {
		fields = new AbstractField[] { new CharField("new 12122"),
				new CharField("old 323"), new CharField("temp 5") };
		expected = new String[] { "new char(12122)", "old char(323)",
				"temp char(5)" };
		goodFields = new AbstractField[] { new VarcharField("new"),
				new CharField("old 11"), new RealField("temp") };
		badFields = new AbstractField[] { new BooleanField("n"),
				new CharField("tem 11"), new IntegerField("old") };
		xmlField = "\t\t\t<field>\n\t\t\t\t<fieldName>new</fieldName>\n\t\t\t\t<fieldType>char</fieldType>\n\t\t\t\t<fieldSize>12122</fieldSize>\n\t\t\t</field>\n";
	}
	
	@Override
	@Test
	public void testToXmlString() {
		assertTrue(fields[0].toXmlString().equals(xmlField));
	}

}
