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
		test.executeQuery("Insert into test1 (id , name , photo ) values ('45' , 'dwidar' , '56541')");
		test.executeQuery("UPDATE test1 SET name = 'youmna' "
				+ "WHERE id = 72;");

	}

}
