package eg.edu.alexu.csd.oop.db.cs14;

import java.io.File;
import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.Database;

public class DataBaseImpl implements Database {
	String currentDataBase;

	@Override
	public String createDatabase(String databaseName, boolean dropIfExists) {
		if(!new File("DataBases").exists()){
			final File file = new File("DataBases");
			file.mkdir();
		}
		this.currentDataBase = "DataBases" + "\\" + databaseName;
		final File f = new File(this.currentDataBase);
		if (f.exists()) {
			if (dropIfExists) {
				executeStructureQuery("DROP DATABASE " + "DataBases" + "\\" + databaseName);
				executeStructureQuery("CREATE DATABASE " + "DataBases" + "\\" +  databaseName);
			}
		}
		else {
			executeStructureQuery("CREATE DATABASE " + this.currentDataBase);
		}
		this.currentDataBase = f.getPath();
		return currentDataBase;
	}

	@Override
	public boolean executeStructureQuery(String query) {
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
