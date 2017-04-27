package vkicl.util;

import java.util.Map;

public class Utils {

	public static String fetchFromMap(Map<String, String[]> map, String key) {
		String value = null;
		if (map.containsKey(key)) {
			value = map.get(key)[0].toUpperCase();
		}
		
		return value;
	}
	
}
