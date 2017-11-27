package eg.edu.alexu.csd.oop.db.cs14;

public class Statement {

	String[] querySplited;
	String currentDataBase;
	Object returnObject;
	public Statement(String[] querySplited, String currenrDataBase, Object returnObject) {
		this.querySplited = querySplited;
		this.currentDataBase = currenrDataBase;
		this.returnObject = returnObject;
	}

	public Object excute() throws Exception {

		return null ;
	}
}
