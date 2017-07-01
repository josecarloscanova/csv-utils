package org.nanotek.apache_csv;


import java.io.CharArrayReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.Assert;
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
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test() throws Exception
	{ 
		int size = 0;
		CsvFileProcessor cfp = new CsvFileProcessor(fileUri);
		CsvBuffer csvb = null;
		CharArrayReader car;
		List <CSVRecord> fullRecordList = new ArrayList<CSVRecord>();
		do { 
			csvb = cfp.readBuffer();
			car = new CharArrayReader(csvb.get().array());
			final CSVParser parser = new CSVParser(car, CSVFormat.newFormat('\t'));
			List<CSVRecord> csvr = parser.getRecords();
			size += size > 0 && csvr.size() > 0 ? (csvr.size() -1): csvr.size() ;
//			new Thread(new Runnable(){
//				public void run(){
//					csvr.stream().filter(e-> e.size() > 1).forEach(e -> System.out.println((e)));
//				}
//			}).start();
			parser.close();
		}while(!csvb.empty());
		
		//A little issue with ApacheCsv
		Assert.assertTrue(0 < size);
	}
}
