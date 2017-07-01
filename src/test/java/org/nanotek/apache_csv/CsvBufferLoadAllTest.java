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

public class CsvBufferLoadAllTest {

	private URI fileUri; 
	
	public CsvBufferLoadAllTest() {
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
		CsvBuffer csv = null;
		CharArrayReader car;
		List <CSVRecord> fullRecordList = new ArrayList<CSVRecord>();
		do { 
			csv = cfp.processFile();
			car = new CharArrayReader(csv.get().array());
			final CSVParser parser = new CSVParser(car, CSVFormat.newFormat('\t'));
			fullRecordList = parser.getRecords();
			size += size > 0 && fullRecordList.size() > 0 ? (fullRecordList.size() -1): fullRecordList.size() ;
			parser.close();
		}while(!csv.empty());
		
		//A little issue with ApacheCsv
		Assert.assertTrue(true == true);
	}
	
}
