package project.data.fields;

import org.junit.Before;

import project.data.fields.AbstractField;
import project.data.fields.BooleanField;
import project.data.fields.CharField;
import project.data.fields.IntegerField;
import project.data.fields.RealField;
import project.data.fields.VarcharField;

public class VarcharFieldTest extends AbstractFieldTest {

	@Override
	@Before
	public void setUp() throws Exception {
		fields = new AbstractField[] { new VarcharField("new"),
				new VarcharField("old"), new VarcharField("temp") };
		expected = new String[] { "new varchar", "old varchar", "temp varchar" };
		goodFields = new AbstractField[] { new BooleanField("new"),
				new CharField("old 11"), new RealField("temp") };
		badFields = new AbstractField[] { new BooleanField("n"),
				new CharField("tem 11"), new IntegerField("old") };
		xmlField = "\t\t\t<field>\n\t\t\t\t<fieldName>new</fieldName>\n\t\t\t\t<fieldType>varchar</fieldType>\n\t\t\t</field>\n";
	}

}
