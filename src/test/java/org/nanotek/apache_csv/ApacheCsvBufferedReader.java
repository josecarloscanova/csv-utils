package org.nanotek.apache_csv;

import java.io.BufferedReader;
import java.io.FileReader;
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
		BufferedReader br;
		FileReader fr = new FileReader(ApacheCsvBufferedReader.class.getResource("/place").getFile());
		br = new BufferedReader(fr);
		final CSVParser parser = new CSVParser(br, CSVFormat.newFormat('\t'));
		List<CSVRecord> csvr = parser.getRecords();
		System.out.println(csvr.size());
		parser.close();
	}

}
