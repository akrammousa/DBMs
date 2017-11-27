package eg.edu.alexu.csd.oop.db.cs14;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

public class Test {

	public static void main(String[] args) throws SQLException {

		DataBaseImpl test = new DataBaseImpl();

		test.createDatabase("csed", false);
		test.executeStructureQuery("create table table_name13 (column_name1 int, coluMN_NAME2 int , column_Name3 int );");
		test.executeUpdateQuery("SELECT column_name1 FROM table_name13 WHERE coluMN_NAME2 < 5");
		//test.executeStructureQuery("DROP DATABASE csed");
		
	}

}
