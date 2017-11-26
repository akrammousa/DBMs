package eg.edu.alexu.csd.oop.db.cs14;

import java.io.File;
import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.Database;

public class DataBaseImpl implements Database {
	String currentDataBase;

	@Override
	public String createDatabase(String databaseName, boolean dropIfExists) {
		databaseName = databaseName.toLowerCase();
		final File f = new File(databaseName);
		this.currentDataBase = databaseName;
		if (f.exists()) {
			if (dropIfExists) {
				executeStructureQuery("DROP DATABASE " + databaseName);
				executeStructureQuery("CREATE DATABASE " + databaseName);
			}
		}
		else {
			executeStructureQuery("CREATE DATABASE " + databaseName);
		}
		this.currentDataBase = f.getAbsolutePath();
		return f.getAbsolutePath();
	}

	@Override
	public boolean executeStructureQuery(String query) {
		query = query.toLowerCase();
		final ChooseStatement statment = new ChooseStatement(query, currentDataBase);
		try {
			statment.createStatement();
			return true;
		} catch (final Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Object[][] executeQuery(String query) throws SQLException {
		final ChooseStatement statment = new ChooseStatement(query, currentDataBase);
		try {
			statment.createStatement();
		} catch (final Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null ;
	}

	@Override
	public int executeUpdateQuery(String query) throws SQLException {
		final ChooseStatement statment = new ChooseStatement(query, currentDataBase);
		try {
			statment.createStatement();
		} catch (final Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

}
