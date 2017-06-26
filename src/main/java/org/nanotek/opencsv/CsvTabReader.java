package org.nanotek.opencsv;

import java.io.Reader;

import au.com.bytecode.opencsv.CSVReader;

public class CsvTabReader extends CSVReader{

	public CsvTabReader(Reader reader) {
		super(reader, '\t');
	}

}
