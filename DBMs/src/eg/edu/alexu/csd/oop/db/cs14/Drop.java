package eg.edu.alexu.csd.oop.db.cs14;

import java.io.File;
import java.util.Arrays;

public class Drop extends Statement  {

	public Drop(String[] querySplited, String currentDataBase) {
		super(querySplited, currentDataBase);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void excute() throws Exception {
		if (super.querySplited[1].equalsIgnoreCase("database")) {
			final File f = new File(super.querySplited[2]);
			
		} else if (super.querySplited[1].equalsIgnoreCase("table")) {

			final File f = new File(super.currentDataBase +
					"\\" + super.querySplited[2] +".xml");
			f.delete();

		}
	}
}
