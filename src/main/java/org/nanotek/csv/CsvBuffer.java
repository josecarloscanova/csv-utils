package org.nanotek.csv;

import java.nio.CharBuffer;
import java.util.function.Supplier;

public class CsvBuffer implements Supplier<CharBuffer>{

	private CharBuffer cb;
	private int remaining;
	
	public CsvBuffer (CharBuffer cb)
	{ 
		if (cb!=null)
			this.cb = cb;
		else 
			throw new RuntimeException("Invalid char buffer");
		remaining = cb.remaining();
	}
	
	@Override
	public CharBuffer get() {
		return cb;
	}

	public boolean empty()
	{ 
		return (remaining == 0)? true : false;
	}
	
}
