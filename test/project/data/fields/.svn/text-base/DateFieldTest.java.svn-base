package project.data.fields;

import org.junit.Before;

import project.data.fields.AbstractField;
import project.data.fields.CharField;
import project.data.fields.DateField;
import project.data.fields.IntegerField;
import project.data.fields.RealField;

public class DateFieldTest extends AbstractFieldTest {

	@Before
	public void setUp() throws Exception {
		fields = new AbstractField[] { new DateField("new"),
				new DateField("old"), new DateField("temp") };
		expected = new String[] { "new date", "old date", "temp date" };
		goodFields = new AbstractField[] { new BooleanField("new"),
				new CharField("old 11"), new RealField("temp") };
		badFields = new AbstractField[] { new BooleanField("n"),
				new CharField("tem 11"), new IntegerField("old") };
		xmlField = "\t\t\t<field>\n\t\t\t\t<fieldName>new</fieldName>\n\t\t\t\t<fieldType>date</fieldType>\n\t\t\t</field>\n";
	}

}
