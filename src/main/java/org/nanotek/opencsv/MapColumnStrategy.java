package org.nanotek.opencsv;

import java.util.Map;

import org.nanotek.Base;
import org.nanotek.example.Place;
import org.nanotek.example.PlaceCsvMapping;
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
			for (String key : baseMap.keySet()){ 
				Integer position = baseMap.get(key);
				if (position !=null)
					csvColumns[position] = key;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new MappingStrategyException (e);
		}
		this.setColumnMapping(csvColumns);
	}

	
	public static void main(String args[])
	{ 
		MapColumnStrategy<? , Place> mcs =  new PlaceCsvMapping().get();
	}
}
