package eg.edu.alexu.csd.oop.db.cs14;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class Select extends Statement {
	
	private static final Exception SQLClientInfoException = null;
	private ArrayList<String> columnsNeeded ;
	private ArrayList<String> TableColumns ;
	private ArrayList<String[]> Results ;
	

	public Select(String[] querySplited, String currentDataBase) {
		super(querySplited, currentDataBase);
	}
	
	@Override
	public void excute() throws Exception {
		super.excute();
		String query = Arrays
				.toString(Arrays.copyOfRange(super.querySplited, 1 ,
						super.querySplited.length - 1));
		String[] strings = query.toLowerCase().split("from" , 2);
		if(strings[0].trim().equals("*")){
			columnsNeeded = null ;
		}else {
			columnsNeeded.addAll(Arrays.asList(strings[0].split(",")));
		}
		strings = strings[1].toLowerCase().split("where" , 2);
		File file = CheckTable(strings[0].trim());
		//ParseCondition(strings[1]);
		
		
		
				
	}

	private void ParseCondition(String string) {
		
		
		
	}

	private File CheckTable(String trim) throws Exception {
		File file = new File(super.currentDataBase + trim + ".xml");
		if(!file.exists()){
			throw SQLClientInfoException;
		}
		
		return file;
	}

}
