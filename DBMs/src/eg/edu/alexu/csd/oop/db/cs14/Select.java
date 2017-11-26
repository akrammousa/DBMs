package eg.edu.alexu.csd.oop.db.cs14;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

public class Select extends Statement {

	private static final Exception SQLClientInfoException = null;
	private ArrayList<String> columnsNeeded;
	private final ArrayList<Map<String, String>> Results = new ArrayList<>();
	private Condition[] conditonsArray;
	private char operation;
	private HandleCondition handler ;

	public Select(String[] querySplited, String currentDataBase) {
		super(querySplited, currentDataBase);
	}

	@Override
	public void excute() throws Exception {
		final StringBuilder st = new StringBuilder();
		for (int i = 1; i < querySplited.length; i++) {

			st.append(querySplited[i]);

		}
		final String query = st.toString();
		String[] strings = query.toLowerCase().split("from", 2);
		if (strings[0].trim().equals("*")) {
			columnsNeeded = null;
		} else {
			columnsNeeded.addAll(Arrays.asList(strings[0].split(",")));
		}
		File file = null;
		if (strings[1].toLowerCase().contains("where")) {
			strings = strings[1].toLowerCase().split("where", 2);
			file = CheckTable(strings[0].trim());
			String conditionString;
			conditionString = strings[1];
			handler = new HandleCondition(conditionString);

		} else {
			handler = new HandleCondition();
			handler.setConditionsArray(null);
			file = CheckTable(strings[1].trim());

		}
		Iterate(file);

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
				if (writeElement) {
					XMLEvent elementEvent = event;
					final Map<String, String> elementMap = new HashMap<>();
					while (elementEvent.getEventType() != XMLEvent.END_ELEMENT ) {
						//!elementEvent.asEndElement().getName().toString().equalsIgnoreCase("element")
						if(!elementEvent.isStartElement()){
							elementEvent = eventReader.nextEvent();
							continue;
						}
						final String columnName = elementEvent.asStartElement().getName().toString();
						elementEvent = eventReader.nextEvent();
						elementMap.put(columnName, elementEvent.asCharacters().getData().trim());
						elementEvent = eventReader.nextEvent();
						elementEvent = eventReader.nextEvent();
					}
					//					final boolean put = checkCondition(elementMap);

					final boolean put = handler.checkCondition(elementMap);

					if(put){
						Results.add(elementMap);
					}
					writeElement = false ;
				}
				writeElement = StartElementConditions(event, writeElement);
				//use or not
				//	writeColumns = StartColumnsConditions(event, writeColumns);

				break;

			case XMLEvent.END_ELEMENT:
				/*if (event.asEndElement().getName().toString().equalsIgnoreCase("element")) {

					writeElement = false;

				} else */if (event.asEndElement().getName().toString().equalsIgnoreCase("columns")) {
					writeColumns = false;
				}

				break;

			}


		}

	}




	private boolean StartElementConditions(XMLEvent event, boolean writeElement) {

		if (event.asStartElement().getName().toString().equalsIgnoreCase("element")) {

			return true;
		}
		return writeElement;

	}

	private File CheckTable(String trim) throws Exception {
		final File file = new File(super.currentDataBase + "\\" + trim + ".xml");
		if (!file.exists()) {
			throw SQLClientInfoException;
		}

		return file;
	}

}
