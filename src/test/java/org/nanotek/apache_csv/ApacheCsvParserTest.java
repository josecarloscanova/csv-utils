package org.nanotek.apache_csv;


import java.io.CharArrayReader;
import java.io.File;
import java.io.RandomAccessFile;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Paths;
import java.util.List;

import javax.annotation.OverridingMethodsMustInvokeSuper;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import org.junit.*;
import org.nanotek.csv.CsvFileProcessor;

import com.google.common.io.Files;

public class ApacheCsvParserTest {

	private URI fileUri; 

	public ApacheCsvParserTest() {
	}

	@Before
	public void loadPlaceTestFile()
	{ 
		try {
			fileUri = this.getClass().getResource("/place").toURI();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test() throws Exception
	{ 
		int size = 0;
		CsvFileProcessor cfp = new CsvFileProcessor(fileUri);
		CharBuffer cb = null;
		CharArrayReader car;
		do { 
			cb = cfp.readBuffer();
			car = new CharArrayReader(cb.array());
			final CSVParser parser = new CSVParser(car, CSVFormat.newFormat('\t'));
			List<CSVRecord> csvr = parser.getRecords();
			size += size > 0 && csvr.size() > 0 ? (csvr.size() -1): csvr.size() ;
			cb.position(0);
			parser.close();
		}while(cb.hasRemaining());
		
		Assert.assertTrue(size == 21216);
	}
}
