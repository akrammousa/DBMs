package eg.edu.alexu.csd.oop.db.cs14;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
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
	private ArrayList<String> TableColumns;

	public Insert(String[] querySplited, String currentDataBase) {
		super(querySplited, currentDataBase);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void excute() throws Exception {

		super.excute();
		Boolean writeColumns = false;
		File file = CheckTable(super.querySplited[2]);
		String temp = file.getAbsolutePath();
		File tempFile = new File(super.currentDataBase + "tempo" + ".xml");
		file.renameTo(tempFile);
		String pro = Arrays.toString(Arrays.copyOfRange(super.querySplited, 3, super.querySplited.length - 1));
		pro = NewString(pro);
		Map<String, String> mapColumns = new HashMap<>();
		mapColumns = GetColumns(pro.toLowerCase().split("values", 2));
		final XMLInputFactory inFactory = XMLInputFactory.newInstance();
		final XMLOutputFactory factory = XMLOutputFactory.newInstance();
		final XMLEventFactory eventFactory = XMLEventFactory.newInstance();
		final XMLEventWriter writer = factory.createXMLEventWriter(new FileOutputStream(new File(temp)));
		final XMLEventReader eventReader = inFactory.createXMLEventReader(new FileInputStream(tempFile));

		while (eventReader.hasNext()) {

			final XMLEvent event = eventReader.nextEvent();

			switch (event.getEventType()) {
			case XMLEvent.START_ELEMENT:
				if (event.asStartElement().getName().toString().equalsIgnoreCase("columns")) {

					writeColumns = true;
				} else if (writeColumns) {
					TableColumns.add(event.asStartElement().getName().toString());
				}
				break;

			case XMLEvent.END_ELEMENT:
				if (event.asEndElement().getName().toString().equalsIgnoreCase("elements")) {

					for (int i = 0; i < TableColumns.size(); i++) {

						String column = TableColumns.get(i).trim();
						writer.add(eventFactory.createStartElement("", null, column));
						writer.add(eventFactory.createCharacters(mapColumns.get(column)));
						writer.add(eventFactory.createStartElement("", null, column));
					}
				} else if (event.asEndElement().getName().toString().equalsIgnoreCase("columns")) {
					writeColumns = false;
				}

				break;

			}

			writer.add(event);

		}
		writer.close();
		tempFile.delete();
	}

	private String NewString(String pro) {
		pro = pro.replaceAll("(", " ");
		pro = pro.replaceAll(")", " ");
		pro = pro.replaceAll(";", " ");
		pro = pro.replaceAll("'", " ");

		return pro;
	}

	private Map<String, String> GetColumns(String[] split) throws Exception {

		String[] columns = split[0].split(",");
		String[] values = split[1].split(",");
		// if values and columns were with different length
		if (columns.length != values.length) {
			throw SQLException;
		}
		Map<String, String> properties = new HashMap<>();
		for (int i = 0; i < columns.length; i++) {
			properties.put(columns[i].trim(), values[i].trim());
		}

		return properties;
	}

	private File CheckTable(String trim) throws Exception {
		File file = new File(super.currentDataBase + trim + ".xml");
		if (!file.exists()) {
			throw SQLClientInfoException;
		}

		return file;
	}

}
