package org.nanotek.csv;

import java.nio.CharBuffer;
import java.util.function.Supplier;

public class CsvBuffer implements Supplier<CharBuffer>{

	private CharBuffer cb;
	private int remaining;
	private boolean hasRemaining; 
	
	public CsvBuffer (CharBuffer cb)
	{ 
		if (cb!=null)
			this.cb = cb;
		else 
			throw new RuntimeException("Invalid char buffer");
		remaining = cb.remaining();
		if (remaining == 0)
			hasRemaining = false;
		else 
			hasRemaining = true;
	}
	
	@Override
	public CharBuffer get() {
		return cb;
	}

	public int remaining()
	{ 
		return remaining;
	}
	
	public boolean hasRemaining()
	{ 
		return hasRemaining;
	}
	
}
