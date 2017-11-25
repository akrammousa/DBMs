package eg.edu.alexu.csd.oop.db.cs14;

public class Statement {

	String[] querySplited;
	String currentDataBase;
	public Statement(String[] querySplited, String currenrDataBase) {
		super();
		this.querySplited = querySplited;
		this.currentDataBase = currenrDataBase;
	}

	public void excute() throws Exception {
	}
}
