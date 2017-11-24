package eg.edu.alexu.csd.oop.db.cs14;

import java.io.File;

public class Create extends Statement {

	public Create(String[] querySplited, String currentDataBase) {
		super(querySplited, currentDataBase);
	}

	@Override
	public void excute() {
		if (super.querySplited[1].equalsIgnoreCase("database")){
			System.out.println(super.currentDataBase);
			final File f = new File(super.currentDataBase);
			f.mkdir();
		}
		else if (super.querySplited[1].equalsIgnoreCase("table")){

		}


	}


}
