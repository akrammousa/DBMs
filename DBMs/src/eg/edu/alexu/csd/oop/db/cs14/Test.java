package eg.edu.alexu.csd.oop.db.cs14;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

public class Test {

	public static void main(String[] args) throws SQLException {

		DataBaseImpl db = new DataBaseImpl();

		db.createDatabase("csed", false);
		db.executeStructureQuery(
				"Create TABLE table_name1(column_name1 varchar, column_name2 int, column_name3 varchar)");
		int count1 = db.executeUpdateQuery(
				" INSERT INTO table_name1(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 4) ");
		int count2 = db.executeUpdateQuery(
				"INSERt INTO table_name1(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 4)");
		int count3 = db.executeUpdateQuery(
				"INSERT INTO table_name1(column_name1, COLUMN_NAME3, column_NAME2) VAlUES ('value2', 'value4', 5)");
		int count4 = db.executeUpdateQuery(
				"UPDATE table_namE1 SET column_name1='11111111', COLUMN_NAME2=22222222, column_name3='333333333' WHERE coLUmn_NAME3='VALUE3'");

	}

}
