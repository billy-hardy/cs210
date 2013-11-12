package project.data.fields;

import org.junit.Before;

import project.data.fields.AbstractField;
import project.data.fields.CharField;
import project.data.fields.IntegerField;
import project.data.fields.RealField;

public class IntegerFieldTest extends AbstractFieldTest {

	@Override
	@Before
	public void setUp() throws Exception {
		fields = new AbstractField[] { new IntegerField("new"),
				new IntegerField("old"), new IntegerField("temp") };
		expected = new String[] { "new integer", "old integer", "temp integer" };
		goodFields = new AbstractField[] { new BooleanField("new"),
				new CharField("old 11"), new RealField("temp") };
		badFields = new AbstractField[] { new BooleanField("n"),
				new CharField("tem 11"), new IntegerField("old") };
		xmlField = "\t\t\t<field>\n\t\t\t\t<fieldName>new</fieldName>\n\t\t\t\t<fieldType>integer</fieldType>\n\t\t\t</field>\n";
	}

}
