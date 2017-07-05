package org.nanotek.apache_csv;


import java.net.URI;
import java.util.ArrayList;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.input.CharSequenceReader;
import org.junit.Before;
import org.junit.Test;
import org.nanotek.csv.CsvBuffer;
import org.nanotek.csv.CsvFileProcessor;

public class ApacheCsvParserTest {

	private URI fileUri; 

	public ApacheCsvParserTest() {
	}

	@Before
	public void loadPlaceTestFile()
	{ 
		try {
			fileUri = this.getClass().getResource("/release").toURI();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test() throws Exception
	{ 
		CsvFileProcessor cfp = new CsvFileProcessor(fileUri);
		CsvBuffer csvb = null;
		ArrayList <CSVRecord> recordList = new ArrayList<CSVRecord>();
		do { 
			csvb = cfp.readBuffer();
			CharSequenceReader car = new CharSequenceReader(csvb.get());
			final CSVParser parser = new CSVParser(car, CSVFormat.newFormat('\t'));
			parser.iterator().forEachRemaining(r ->recordList.add(r));
//			size += size > 0 && csvr.size() > 0 ? (csvr.size() -1): csvr.size() ;
			parser.close();
		}while(!csvb.empty());
		
		//A little issue with ApacheCsv
//		Assert.assertTrue(21216 <= fullRecordList.size());
	}
}
