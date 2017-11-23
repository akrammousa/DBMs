package eg.edu.alexu.csd.oop.db.cs14;

import java.io.File;
import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.Database;

public class DataBaseImpl implements Database {

	@Override
	public String createDatabase(String databaseName, boolean dropIfExists) {
		final File f = new File(databaseName);
		if (f.exists()) {
			if (dropIfExists) {
				f.delete();
				f.mkdir();
			} else {
				f.mkdir();
			}
		}
		return f.getAbsolutePath();
	}

	@Override
	public boolean executeStructureQuery(String query) throws SQLException {
		final ChooseStatment statment = new ChooseStatment(query);
		statment.excute();
		return false;
	}

	@Override
	public Object[][] executeQuery(String query) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int executeUpdateQuery(String query) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

}
