package org.nanotek.apache_csv;

import java.io.CharArrayReader;
import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.nanotek.csv.CsvBuffer;
import org.nanotek.csv.CsvFileProcessor;
import org.spf4j.jmx.Registry;

@Deprecated
public class CsvFileProcessorApacheCsv {

	public static void main(String[] args)
	{ 
		int size =0;
		try {
			FileChannel fileChannel = new RandomAccessFile(new File("c:/place"), "rw").getChannel();
			CsvFileProcessor cfp = new CsvFileProcessor("release");
			CsvBuffer csv = null;
			CharArrayReader car;
			CharsetEncoder cse = Charset.forName("UTF-8").newEncoder();
		do { 
			csv = cfp.readBuffer();
			car = new CharArrayReader(csv.get().array());
			final CSVParser parser = new CSVParser(car, CSVFormat.newFormat('\t'));
			List<CSVRecord> csvr = parser.getRecords();
			size += size > 0 && csvr.size() > 0 ? (csvr.size() -1): csvr.size() ;
			ByteBuffer buff = cse.encode(csv.get());
//			System.out.println("processed buffer " + ++counter);
			System.out.println("buffer size " + csvr.size());
			fileChannel.write(buff);
			parser.close();
		}while(!csv.empty());
//		System.out.println("files size " + file_size);
//		fileChannel.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("size of records in file " + size);
	}
	
}
