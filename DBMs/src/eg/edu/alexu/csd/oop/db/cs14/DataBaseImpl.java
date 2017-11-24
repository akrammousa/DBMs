package eg.edu.alexu.csd.oop.db.cs14;

import java.io.File;
import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.Database;

public class DataBaseImpl implements Database {
	String currentDataBase;

	@Override
	public String createDatabase(String databaseName, boolean dropIfExists) {
		final File f = new File(databaseName);
		this.currentDataBase = databaseName;
		if (f.exists()) {
			if (dropIfExists) {
				//f.delete();
				//f.mkdir();
				try {
					executeStructureQuery("DROP DATABASE " + databaseName);
					executeStructureQuery("CREATE DATABASE " + databaseName);
				} catch (final SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		else {
			try {
				executeStructureQuery("CREATE DATABASE " + databaseName);
			} catch (final SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.currentDataBase = f.getAbsolutePath();
		return f.getAbsolutePath();
	}

	@Override
	public boolean executeStructureQuery(String query) throws SQLException {
		final ChooseStatement statment = new ChooseStatement(query, currentDataBase);
		statment.createStatement();
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
