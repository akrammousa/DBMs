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

		test.executeQuery("create table test1(id int, name varchar , photo int );");
		test.executeUpdateQuery("UPDATE test1 SET name = 'youmna' "
				+ "WHERE id = 72;");
		
	}

}
