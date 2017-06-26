package org.nanotek.csv;

import java.io.CharArrayReader;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class CsvFileProcessorApacheCsv {

	public static void main(String[] args)
	{ 
		try {
//			FileChannel fileChannel = new RandomAccessFile(new File("c:/place"), "rw").getChannel();
			CsvFileProcessor cfp = new CsvFileProcessor("place");
			CharBuffer cb = null;
			CharArrayReader car;
		CharsetEncoder cse = Charset.forName("UTF-8").newEncoder();
		do { 
			cb = cfp.readBuffer();
			car = new CharArrayReader(cb.array());
			final CSVParser parser = new CSVParser(car, CSVFormat.newFormat('\t'));
			List<CSVRecord> csvr = parser.getRecords();
			System.out.println("size of records in segment " + csvr.size());
//			ByteBuffer buff = cse.encode(cb);
//			System.out.println("processed buffer " + ++counter);
//			fileChannel.write(buff);
//			cb.position(0);
		}while(cb.hasRemaining());
//		System.out.println("files size " + file_size);
//		fileChannel.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
