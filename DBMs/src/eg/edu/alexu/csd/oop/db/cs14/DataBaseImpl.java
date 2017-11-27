package eg.edu.alexu.csd.oop.db.cs14;

import java.io.File;
import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.Database;

public class DataBaseImpl implements Database {
	String currentDataBase;

	@Override
	public String createDatabase(String databaseName, boolean dropIfExists) {

		
		this.currentDataBase = "DataBases" + "\\" + databaseName;
		final File f = new File(this.currentDataBase);
		if (f.exists()) {
			if (dropIfExists) {
				executeStructureQuery("DROP DATABASE " + "DataBases" + "\\" + databaseName);
				executeStructureQuery("CREATE DATABASE " + "DataBases" + "\\" + databaseName);
			}
		} else {
			executeStructureQuery("CREATE DATABASE " + this.currentDataBase);
		}
		this.currentDataBase = f.getPath();
		// throw new RuntimeException( "Creat db: " + databaseName);

		return currentDataBase;
	}

	@Override
	public boolean executeStructureQuery(String query) {
		// if(currentDataBase == null){
		// currentDataBase = "DataBases" + "\\" + "default";
		// }
		/*
		 * final File f= new File("DataBases"); if(!f.exists()){ f.mkdir();
		 *
		 * }
		 */
		// this.currentDataBase = f.getPath();

		

		final ChooseStatement statment = new ChooseStatement(query, currentDataBase, null);
		try {
			statment.createStatement();
			System.out.println(statment.returnObject);
			return (boolean) statment.returnObject;
		} catch (final Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		 throw new RuntimeException( "Ex struct: " + query);

		return false;
	}

	@Override
	public Object[][] executeQuery(String query) throws SQLException {

		final ChooseStatement statment = new ChooseStatement(query, currentDataBase, null);
		try {
			statment.createStatement();
		} catch (final Exception e) {
			// TODO Auto-generated catch block
//			 throw new RuntimeException( "ex qu: " + query);

			e.printStackTrace();
		}
//		 throw new RuntimeException( "ex qu: " + query);

		return (Object[][]) statment.returnObject;
	}

	@Override
	public int executeUpdateQuery(String query) throws SQLException {
		final ChooseStatement statment = new ChooseStatement(query, currentDataBase, null);
		try {
			statment.createStatement();
		} catch (final Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int temp = 100;

		try {
			temp = (int) statment.returnObject;
		} catch (final Exception e) {
			 throw new RuntimeException(query);
		}

		return temp;
	}

}
