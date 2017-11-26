package eg.edu.alexu.csd.oop.db.cs14;

import java.io.File;

public class Drop extends Statement  {

	public Drop(String[] querySplited, String currentDataBase) {
		super(querySplited, currentDataBase);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void excute() throws Exception {
		if (super.querySplited[1].equalsIgnoreCase("database")) {
			final File f = new File(currentDataBase);
			deleteDir(f);
		} else if (super.querySplited[1].equalsIgnoreCase("table")) {

			final File f = new File(super.currentDataBase +
					"\\" + super.querySplited[2] +".xml");
			f.delete();

		}
	}
	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			final String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				final boolean success = deleteDir (new File(dir, children[i]));

				if (!success) {
					return false;
				}
			}
		}
		return dir.delete();
	}
}
