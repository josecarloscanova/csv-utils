package org.nanotek.example;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import org.nanotek.opencsv.MapColumnStrategy;

public class PlaceCsvMapping implements Supplier<MapColumnStrategy<Map<String,Integer>,Place>>{

	private static final Map<String,Integer> map = new HashMap<String,Integer>();
	private MapColumnStrategy<Map<String,Integer>,Place> mcs ; 
	{
		map.put("placeId" , 0);
		map.put("gid",1); 
		map.put("name" ,2); 
		map.put("type" , 3); 
		map.put("address" ,4); 
		map.put("area"  , 5); 
		map.put("coordinates" ,  6);
	}

	public PlaceCsvMapping(){
		mcs = new MapColumnStrategy<Map<String,Integer>,Place>(map, Place.class);
	}

	@Override
	public MapColumnStrategy<Map<String,Integer>,Place> get() {
		return mcs;
	}

}
