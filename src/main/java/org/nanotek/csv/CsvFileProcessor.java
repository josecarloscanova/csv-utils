package org.nanotek.csv;

import static org.nanotek.csv.FileConstants.DEFAULT_BUFFER_SIZE;
import static org.nanotek.csv.FileConstants.DEFAULT_CHARSET;
import static org.nanotek.csv.FileConstants.DEFAULT_DIRECTORY;

import java.io.CharArrayReader;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class CsvFileProcessor {

	private String fileName;
	private String defaultDirectory;
	private String charSet;
	private int bufferSize; 
	private File csvFile; 
	private ByteBuffer byteBuffer;
	private Charset currentCharSet; 
	private CharsetDecoder decoder;
	int current_position;
	
	
	public CsvFileProcessor(String fileName)
	{ 
		configureCsvProcessor(DEFAULT_DIRECTORY , fileName , DEFAULT_CHARSET , DEFAULT_BUFFER_SIZE);
	}
	
	public CsvFileProcessor(String directory , String fileName)
	{ 
		configureCsvProcessor(directory , fileName , DEFAULT_CHARSET , DEFAULT_BUFFER_SIZE);
	}
	
	public CsvFileProcessor(String directory , String fileName , String charSet)
	{ 
		configureCsvProcessor(directory , fileName , charSet , DEFAULT_BUFFER_SIZE);
	}
	
	public CsvFileProcessor(String directory , String fileName , String charSet , int bufferSize)
	{ 
		configureCsvProcessor(directory , fileName , charSet , bufferSize);
	}
	
	private void configureCsvProcessor(String directory , String fileName , String charSet , int bufferSize)
	{ 
		this.defaultDirectory = directory;
		this.fileName = fileName;
		this.charSet = charSet; 
		this.bufferSize = bufferSize;
		current_position = 0;
		createDecoder();
	}
	
	private void createDecoder()
	{ 
		currentCharSet = Charset.forName(this.charSet);
        decoder = currentCharSet.newDecoder();
	}
	
	/** 
	 * can be used to process and entire file... 
	 * @return
	 */
	public CsvBuffer processFile()
	{ 
		return new CsvBuffer(createCharBuffer(bufferFromFile(verifyFileLocation())));
	}
	
	public CharBuffer readBuffer()
	{ 
		if(csvFile ==null){ 
			verifyFileLocation();
			byteBuffer = bufferFromFile(csvFile); 
		}
		int len = Math.min(bufferSize, byteBuffer.remaining());
		return readCharBuffer(len);
	}
	
	private CharBuffer readCharBuffer(int len) {
		//TODO: create method to read last newline from the byte buffer.
		byteBuffer.position(current_position + len);
		int ba_buffer_position = readLastLineDelimiter(byteBuffer);
		CharBuffer cb = null;
		try {   
				byteBuffer.position(current_position);
			    if (byteBuffer.hasRemaining()){ 
			    	int remain = ba_buffer_position - current_position;
			    if (len > remain)
			    	len = remain;
				byte[] ba = new byte[len];
				byteBuffer.position(current_position);
				byteBuffer.get(ba);
				cb = decoder.decode(ByteBuffer.wrap(ba));
				current_position = ba_buffer_position;
			    }else { 
			    	cb = CharBuffer.allocate(0);
			    }
		} catch (CharacterCodingException e) {
			throw new RuntimeException(e);
		}
		return cb;
	}

	private int readLastLineDelimiter(ByteBuffer bb) {
		int counter = bb.position();
		int position = counter;
		char c = '\n';
		do{ 
			c = (char)bb.get(--counter);
		}while(c != '\n');
		return counter;
	}

	private File verifyFileLocation() {
		Path path = Paths.get(this.defaultDirectory, this.fileName);
		if ((csvFile  = path.toFile()) ==null)
		{ 
			throw new RuntimeException("Not a valid file to process");
		}
		return csvFile;
	}

	private MappedByteBuffer bufferFromFile(File file) {
		MappedByteBuffer buffer = null;
		try {
			FileChannel fileChannel = new RandomAccessFile(file, "r").getChannel();
			buffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileChannel.size());
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
		return buffer;
	}
	
	private CharBuffer  createCharBuffer(ByteBuffer buffer) {
        Charset utfCharSet = Charset.forName(charSet);
		CharsetDecoder decoder = utfCharSet.newDecoder();
		CharBuffer cb = null;
        try {
				cb = decoder.decode(buffer);
		} catch (CharacterCodingException e) {
			throw new RuntimeException(e);
		}
        return cb;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	
}