package org.nanotek.csv;

import java.nio.CharBuffer;
import java.util.function.Supplier;

public class CsvBuffer implements Supplier<CharBuffer>{

	private CharBuffer cb;
	private int remaining;
	private boolean hasContent; 
	
	public CsvBuffer (CharBuffer cb)
	{ 
		if (cb!=null)
			this.cb = cb;
		else 
			throw new RuntimeException("Invalid char buffer");
		remaining = cb.remaining();
		if (remaining == 0)
			hasContent = false;
		else 
			hasContent = true;
	}
	
	@Override
	public CharBuffer get() {
		return cb;
	}

	public int getRemaining()
	{ 
		return remaining;
	}
	
	public boolean hasContent()
	{ 
		return hasContent;
	}
	
}
