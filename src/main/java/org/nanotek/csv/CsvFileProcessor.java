package org.nanotek.csv;

import static org.nanotek.csv.FileConstants.DEFAULT_BUFFER_SIZE;
import static org.nanotek.csv.FileConstants.DEFAULT_CHARSET;
import static org.nanotek.csv.FileConstants.DEFAULT_DIRECTORY;

import java.io.Closeable;
import java.io.File;
import java.io.RandomAccessFile;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CsvFileProcessor {

	private String fileName;
	private String defaultDirectory;
	private String charSet;
	private int bufferSize;
	private File csvFile;
	private ByteBuffer byteBuffer;
	private Charset currentCharSet;
	private CharsetDecoder decoder;
	private int current_position;

	public CsvFileProcessor(String fileName) {
		configureCsvProcessor(DEFAULT_DIRECTORY, fileName, DEFAULT_CHARSET, DEFAULT_BUFFER_SIZE);
	}

	public CsvFileProcessor(String directory, String fileName) {
		configureCsvProcessor(directory, fileName, DEFAULT_CHARSET, DEFAULT_BUFFER_SIZE);
	}

	public CsvFileProcessor(String directory, String fileName, String charSet) {
		configureCsvProcessor(directory, fileName, charSet, DEFAULT_BUFFER_SIZE);
	}

	public CsvFileProcessor(String directory, String fileName, String charSet, int bufferSize) {
		configureCsvProcessor(directory, fileName, charSet, bufferSize);
	}

	public CsvFileProcessor(URI fileLocation) {
		String[] pathFileStr = getDirectoryFileLocation(fileLocation);
		configureCsvProcessor(pathFileStr[0], pathFileStr[1], DEFAULT_CHARSET, DEFAULT_BUFFER_SIZE);
	}

	public CsvFileProcessor(URI fileLocation, String charSet, int bufferSize) {
		String[] pathFileStr = getDirectoryFileLocation(fileLocation);
		configureCsvProcessor(pathFileStr[0], pathFileStr[1], charSet, bufferSize);
	}

	private String[] getDirectoryFileLocation(URI fileLocation) {
		Path path = Paths.get(fileLocation);
		return new String[] { path.getParent().toString(), path.toFile().getName() };
	}

	private void configureCsvProcessor(String directory, String fileName, String charSet, int bufferSize) {
		this.defaultDirectory = directory;
		this.fileName = fileName;
		this.charSet = charSet;
		this.bufferSize = bufferSize;
		current_position = 0;
		createDecoder();
	}

	private void createDecoder() {
		currentCharSet = Charset.forName(this.charSet);
		decoder = currentCharSet.newDecoder();
	}

	/**
	 * can be used to process an entire file...
	 * 
	 * @return
	 */
	public CsvBuffer processFile() {
		verifyAndInitialize();
		return new CsvBuffer(createCharBuffer(byteBuffer));
	}

	public CsvBuffer readBuffer() {
		verifyAndInitialize();
		int len = Math.min(bufferSize, byteBuffer.remaining());
		return new CsvBuffer(readCharBuffer(len));
	}

	private void verifyAndInitialize() {
		if (csvFile == null) {
			byteBuffer = verifyFileAndCreateBuffer();
		}
	}

	private ByteBuffer verifyFileAndCreateBuffer() {
		verifyFileLocation();
		return bufferFromFile(csvFile);
	}

	private CharBuffer readCharBuffer(final int len) {
		// TODO: create method to read last newline from the byte buffer.
		byteBuffer.position(current_position + len);
		int ba_buffer_position = readLastLineDelimiter(byteBuffer);
		CharBuffer cb;
		byte[] ba;
		try {
			byteBuffer.position(current_position);
			if (byteBuffer.hasRemaining()) {
				int remain = ba_buffer_position - current_position;
				int ba_len = (remain < len) ? remain : len;
				ba = new byte[ba_len];
				byteBuffer.get(ba);
				cb = decoder.decode(ByteBuffer.wrap(ba));
				current_position = ba_buffer_position;
			} else {
				cb = CharBuffer.allocate(0);
			}
		} catch (CharacterCodingException e) {
			throw new RuntimeException(e);
		}
		return cb;
	}

	private int readLastLineDelimiter(ByteBuffer bb) {
		int counter = bb.position();
		char c = '\n';
		try {
			do {
				c = (char) bb.get(--counter);
			} while (c != '\n');
		} catch (IndexOutOfBoundsException e) {
			throw new RuntimeException("Use processFile() instead");
		}
		return counter;
	}

	private File verifyFileLocation() {
		Path path = Paths.get(this.defaultDirectory, this.fileName);
		if (!(csvFile = path.toFile()).exists()) {
			throw new RuntimeException("Not a valid file to process");
		}
		return csvFile;
	}

	private MappedByteBuffer bufferFromFile(File file) {
		MappedByteBuffer buffer = null;
		FileChannel fileChannel = null;
		try {
			fileChannel = new RandomAccessFile(file, "r").getChannel();
			buffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileChannel.size());
		} catch (Exception ioe) {
			throw new RuntimeException(ioe);
		}
		close(fileChannel);
		return buffer;
	}

	private CharBuffer createCharBuffer(ByteBuffer buffer) {
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

	private <T extends Closeable> void close(T t) {
		try {
			t.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
