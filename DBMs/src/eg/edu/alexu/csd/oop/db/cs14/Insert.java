package eg.edu.alexu.csd.oop.db.cs14;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.events.XMLEvent;

public class Insert extends Statement {

	private static final Exception SQLClientInfoException = null;
	private static final Exception SQLException = null;
	private final ArrayList<String> TableColumns = new ArrayList<>();

	public Insert(String[] querySplited, String currentDataBase, Object returnObject) {
		super(querySplited, currentDataBase,returnObject);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object excute() throws Exception {

		returnObject = new Object();
		Boolean writeColumns = false;
		StringBuilder st = new StringBuilder();
		for (int i = 2; i < querySplited.length; i++) {

			st.append(querySplited[i]);

		}
		final String tem  =st.toString();
		final String[] strings = tem.split("\\(" , 2);
		final File file = CheckTable(strings[0].trim());
		if(file == null){
			this.returnObject = 0;
			return returnObject;
		}
		final String temp = file.getPath();
		final File tempFile = new File(super.currentDataBase + "\\" + "tempo" + ".xml");
		file.renameTo(tempFile);
		st = new StringBuilder();

		String pro = strings[1];
		pro = NewString(pro);
		Map<String, String> mapColumns = new HashMap<>();
		mapColumns = GetColumns(pro.toLowerCase().split("values", 2));
		final XMLInputFactory inFactory = XMLInputFactory.newInstance();
		final XMLOutputFactory factory = XMLOutputFactory.newInstance();
		final XMLEventFactory eventFactory = XMLEventFactory.newInstance();
		final FileOutputStream output = new FileOutputStream(new File(temp));
		final XMLEventWriter writer = factory.createXMLEventWriter(output , "ISO-8859-1");
		final XMLEventReader eventReader = inFactory.createXMLEventReader(new FileInputStream(tempFile));
		//		final XMLStreamWriter writer = factory.createXMLStreamWriter(output,"ISO-8859-1");
		//		writer.writeStartDocument("ISO-8859-1","1.0");
		//		final FileWriter stringWriter = new FileWriter(table);
		//		final XMLOutputFactory factory = XMLOutputFactory.newInstance();
		//		final XMLStreamWriter writer = factory.createXMLStreamWriter(stringWriter);
		//		writer.writeStartDocument();
		while (eventReader.hasNext()) {

			final XMLEvent event = eventReader.nextEvent();

			switch (event.getEventType()) {
			case XMLEvent.START_DOCUMENT:
				writer.add(eventFactory.createStartDocument("ISO-8859-1","1.0"));
				continue;
				//break;
			case XMLEvent.START_ELEMENT:
				if (event.asStartElement().getName().toString().equalsIgnoreCase("columns")) {

					writeColumns = true;
				} else if (writeColumns) {
					TableColumns.add(event.asStartElement().getName().toString());
				}
				break;

			case XMLEvent.END_ELEMENT:
				if (event.asEndElement().getName().toString().equalsIgnoreCase("elements")) {

					writer.add(eventFactory.createStartElement("", null, "element"));
					for (int i = 0; i < TableColumns.size(); i++) {

						final String column = TableColumns.get(i).trim();
						writer.add(eventFactory.createStartElement("", null, column));
						String value = mapColumns.get(column);
						if(value == null){
							value = "null";
						}
						writer.add(eventFactory.createCharacters(value));
						writer.add(eventFactory.createEndElement("", null, column));
					}
				} else if (event.asEndElement().getName().toString().equalsIgnoreCase("columns")) {
					writeColumns = false;
				}

				break;

			}

			writer.add(event);

		}
		writer.close();
		output.close();
		tempFile.delete();

		final int rowsAdded = 1;
		super.returnObject = rowsAdded;
		return rowsAdded ;
	}

	private String NewString(String pro) {
		pro = pro.replaceAll("\\(", " ");
		pro = pro.replaceAll("\\)", " ");
		pro = pro.replaceAll(";", " ");
		pro = pro.replaceAll("\\'", " ");

		return pro;
	}

	private Map<String, String> GetColumns(String[] split) throws Exception {

		final String[] columns = split[0].split(",");
		final String[] values = split[1].split(",");
		// if values and columns were with different length
		if (columns.length != values.length) {
			throw SQLException;
		}
		final Map<String, String> properties = new HashMap<>();
		for (int i = 0; i < columns.length; i++) {
			properties.put(columns[i].trim(), values[i].trim());
		}

		return properties;
	}

	private File CheckTable(String trim) throws Exception {
		final File file = new File(super.currentDataBase + "\\" + trim + ".xml");
		if (!file.exists()) {
			this.returnObject = 0;
			//throw SQLClientInfoException;
			return null;
		}

		return file;
	}

}
