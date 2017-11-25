package eg.edu.alexu.csd.oop.db.cs14;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.management.Attribute;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

public class Select extends Statement {

	private static final Exception SQLClientInfoException = null;
	private ArrayList<String> columnsNeeded;
	private Map<String, String> TableColumns;
	private ArrayList<String[]> Results;
	private	Condition[] conditonsArray;
	private char operation ;

	public Select(String[] querySplited, String currentDataBase) {
		super(querySplited, currentDataBase);
	}

	@Override
	public void excute() throws Exception {
		StringBuilder st = new StringBuilder();
		for (int i = 1; i < querySplited.length; i++) {

			st.append(querySplited[i]);

		}
		String query = st.toString();
		String[] strings = query.toLowerCase().split("from", 2);
		if (strings[0].trim().equals("*")) {
			columnsNeeded = null;
		} else {
			columnsNeeded.addAll(Arrays.asList(strings[0].split(",")));
		}
		File file = null ;
		if (strings[1].toLowerCase().contains("where")){
		strings = strings[1].toLowerCase().split("where", 2);
		file = CheckTable(strings[0].trim());
		String conditionString;
		conditionString = strings[1];
		String[] conditonsArrayString = splitConditions (conditionString) ;
		parseConditions(conditonsArrayString);
		
		}else {
			conditonsArray = null ;
			file = CheckTable(strings[1].trim());
			

		}
		Iterate(file);

	}

	private void parseConditions(String[] conditonsArrayString) {
		// TODO Auto-generated method stub
		
	}

	private String[] splitConditions (String conditionString) {
		String[] conditonsArrayString = null;
		if(conditionString != null){
			
			if(conditionString.toLowerCase().contains("and")){
				operation = 'a';
				conditonsArrayString = conditionString.toLowerCase().split("and", 2);
				
			}else if(conditionString.toLowerCase().contains("or")){
				operation = 'o';
				conditonsArrayString = conditionString.toLowerCase().split("or", 2);
			}else if(conditionString.toLowerCase().contains("not")){
				operation = 'n';
				conditionString = conditionString.toLowerCase().replaceAll("not", "");
				conditonsArrayString = new String[2];
				conditonsArrayString[0] = conditionString.trim();
			}else {
				operation = 'e';
				conditonsArrayString = new String[2];
				conditonsArrayString[0] = conditionString.trim();
			}
			return conditonsArrayString;
		}
		return conditonsArrayString;
		
		
	}

	private void Iterate(File file) throws FileNotFoundException, XMLStreamException {
		boolean writeColumns = false;
		boolean writeElement = false;
		final XMLInputFactory inFactory = XMLInputFactory.newInstance();
		final XMLEventReader eventReader = inFactory.createXMLEventReader(new FileInputStream(file));

		while (eventReader.hasNext()) {
			final XMLEvent event = eventReader.nextEvent();
			switch (event.getEventType()) {

			case XMLEvent.START_ELEMENT:
				if(writeElement){
				final	XMLEvent elementEvent =event;
				Map<String, String> elementMap = new HashMap<>();
					while(!elementEvent.asEndElement().getName().toString().equalsIgnoreCase("element")){
						String columnName = elementEvent.asStartElement().getName().toString();
						elementEvent = eventReader.nextEvent();
						elementMap.put(columnName, elementEvent.asCharacters().getData());
						elementEvent = eventReader.nextEvent();
						elementEvent = eventReader.nextEvent();
					}
					checkCondition(elementMap);
				}
				writeElement = StartElementConditions(event, writeElement);
				writeColumns = StartColumnsConditions(event, writeColumns);

				break;

			case XMLEvent.END_ELEMENT:
				if (event.asEndElement().getName().toString().equalsIgnoreCase("element")) {

					writeElement = false;

				} else if (event.asEndElement().getName().toString().equalsIgnoreCase("columns")) {
					writeColumns = false;
				}

				break;

			}

			writer.add(event);

		}
		writer.close();

	}

	private void checkCondition(Map<String, String> elementMap) {
		
		
	}

	private boolean StartColumnsConditions(XMLEvent event, boolean writeColumns) {
		if (event.asStartElement().getName().toString().equalsIgnoreCase("columns")) {

			return true;
		} else if (writeColumns) {
			Iterator<Attribute> attributes = event.asStartElement().getAttributes();
			String type = attributes.next().getValue().toString();
			TableColumns.put(event.asStartElement().getName().toString(), type);

		}
		return writeColumns;

	}

	private boolean StartElementConditions(XMLEvent event, boolean writeElement) {

		if (event.asStartElement().getName().toString().equalsIgnoreCase("element")) {

			return true;
		} else if (writeElement) {

		}
		return writeElement;

	}

	private File CheckTable(String trim) throws Exception {
		File file = new File(super.currentDataBase + "\\" + trim + ".xml");
		if (!file.exists()) {
			throw SQLClientInfoException;
		}

		return file;
	}

}
