package project.data;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import project.data.Table;

public class TableTest {

	private Table[] tables;

	@Before
	public void setUp() throws Exception {
		tables = new Table[] {
				new Table("emp", "sal real, job varchar, sday date"),
				new Table("cats",
						"tails integer, birth date, name char(11), asleep boolean"),
				new Table("done", "true boolean") };
	}

	@Test
	public void testToString() {
		//createField(String f) should be indirectly tested with this.
		assertTrue(tables[0].toString().equals(
				"Table emp: sal real, job varchar, sday date"));
		assertTrue(tables[1].toString().equals(
				"Table cats: tails integer, birth date, name char(11), asleep boolean"));
		assertTrue(tables[2].toString().equals("Table done: true boolean"));

	}

}
