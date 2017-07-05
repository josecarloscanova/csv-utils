package org.nanotek.apache_csv;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.Test;

public class ApacheCsvBufferedReader {

	public ApacheCsvBufferedReader() {
	}

	@Test
	public void test() throws Exception {
		List <CSVRecord> fullRecordList = new ArrayList<CSVRecord>();
		BufferedReader br;
		FileReader fr = new FileReader(ApacheCsvBufferedReader.class.getResource("/release").getFile());
		br = new BufferedReader(fr);
		final CSVParser parser = new CSVParser(br, CSVFormat.newFormat('\t'));
		Iterator<CSVRecord> csvr = parser.iterator();
		csvr.forEachRemaining(r ->fullRecordList.add(r));
		parser.close();
	}

}
