package org.nanotek.test.opencsv;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Before;

public class OpenCsvTest {

	private URI fileUri; 

	public OpenCsvTest() {
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

}
