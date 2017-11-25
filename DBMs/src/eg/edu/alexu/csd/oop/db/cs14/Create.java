package eg.edu.alexu.csd.oop.db.cs14;

import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

public class Create extends Statement {
	
	

	
	private static final Exception SQLException = null;

	public Create(String[] querySplited, String currentDataBase) {
		super(querySplited, currentDataBase);
	}

	@Override
	public void excute() throws Exception {
		if (super.querySplited[1].equalsIgnoreCase("database")) {
			System.out.println(super.currentDataBase);
			final File f = new File(super.currentDataBase);
			f.mkdir();
		} else if (super.querySplited[1].equalsIgnoreCase("table")) {

			createTable(Arrays
					.toString(Arrays.copyOfRange(super.querySplited, 2,
							super.querySplited.length - 1)));

		}

	}

	private void createTable(String columns) throws Exception {
		ArrayList<String> ColumnNames = new ArrayList<>();

		String[] strings = columns.split("(", 2);

		String tableName = strings[0].trim();

		CheckTable(tableName);
		StringWriter stringWriter = new StringWriter();
		final XMLOutputFactory factory = XMLOutputFactory.newInstance();
		final XMLStreamWriter writer = factory.createXMLStreamWriter(stringWriter);
		writer.writeStartDocument();
		writer.writeStartElement(tableName);
		writer.writeStartElement("columns");
		strings = strings[1].split(",");
		writer.writeAttribute("count", String.valueOf(strings.length));

		for (int i = 0; i < strings.length; i++) {

			String[] column = strings[i].split(" ", 2);
			strings[1].replaceAll(");", " ");
			String name = column[0].trim() ;
			if(!ColumnNames.contains(name)){
			ColumnNames.add(name);
			writer.writeStartElement(name);
			writer.writeAttribute( "type", column[1].trim() );
			writer.writeEndElement();
			}

		}
		writer.writeEndElement();
		writer.writeStartElement("elements");
		writer.writeEndDocument();
		
		

	}

	private void CheckTable(String tableName) throws Exception {
		//check from the keywords
		File table = new File(super.currentDataBase + tableName + ".xml");
		if (table.exists()) {
			throw SQLException;
		}
		table.createNewFile();

		
	}
	
	

}
