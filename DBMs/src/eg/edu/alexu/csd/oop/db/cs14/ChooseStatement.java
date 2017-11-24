package eg.edu.alexu.csd.oop.db.cs14;

import java.lang.reflect.Constructor;
import java.util.LinkedList;






public class ChooseStatement {
	protected final String query;
	protected final String dataBase;
	public LinkedList<Class<? extends Statement>> supportedStatements = new LinkedList<Class<? extends Statement>>();
	public void createStatement() {
		final String[] querySplited = query.split(" ");
		Statement instance = null;
		for (int i = 0; i < supportedStatements.size(); i++) {
			if (querySplited[0]
					.equalsIgnoreCase(supportedStatements
							.get(i)
							.getSimpleName())) {
				try {
					final Constructor<? extends Statement> constructor = supportedStatements.get(i).getConstructor(String[].class, String.class);
					instance = constructor.newInstance(querySplited,dataBase);
					break;
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
		}
		instance.excute();

	}
	public ChooseStatement(String query, String dataBase) {
		super();
		this.query = query;
		this.dataBase = dataBase;
		supportedStatements.add(Create.class);
		supportedStatements.add(Drop.class);
		supportedStatements.add(Insert.class);
		supportedStatements.add(Select.class);
		supportedStatements.add(Delete.class);
		supportedStatements.add(Update.class);
	}

}
