package org.nanotek.opencsv;

import java.util.Map;
import java.util.Map.Entry;

import org.nanotek.Base;
import org.nanotek.PostConstructorStrategy;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;

public class    MapColumnStrategy<T extends Map<String,Integer>, I extends Base<?>> 
extends     ColumnPositionMappingStrategy<I>  
implements PostConstructorStrategy<Map<String,Integer>> {

	private T baseMap; 
	
	public MapColumnStrategy(T baseMap) {
		super();
		this.baseMap = baseMap;
		buildMapping();
	}

	public MapColumnStrategy(T baseMap , Class<I> type) {
		super();
		this.baseMap = baseMap;
		this.type = type;
		buildMapping();
	}
	
	@Override
	public void buildMapping() {
		assert (baseMap !=null && baseMap.size() >=1);
		String [] csvColumns = new String[baseMap.keySet().size()];
		try {
			baseMap.entrySet().stream().filter(e -> checkPositionValid(e)).forEach(e ->csvColumns[e.getValue()] = e.getKey());
		} catch (Exception e) {
			e.printStackTrace();
			throw new MappingStrategyException (e);
		}
		this.setColumnMapping(csvColumns);
	}

	private boolean checkPositionValid(Entry<String, Integer> e) {
		checkPositionUniqueness();
		return e.getValue() !=null && e.getValue() >=0;
	}

	//Implement a validaiton on uniqueness of map element position.
	private void checkPositionUniqueness() {
	}
	
}
