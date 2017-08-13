package vkicl.util;

import java.util.Map;

import org.apache.log4j.Logger;

import vkicl.daoImpl.PortInwardDaoImpl;

public class Utils {

	private static Logger logger = Logger.getLogger(PortInwardDaoImpl.class);
	
	public static Double sectionWeightConstant = 7.85d/1000000000d;
	public static String fetchFromMap(Map<String, String[]> map, String key) {
		String value = null;
		if (map.containsKey(key)) {
			value = map.get(key)[0].toUpperCase();
		}
		
		return value;
	}
	
	public static Double calculateSectionWeight(Integer length, Integer width, Integer thickness, Integer quantity){
		
		
		Double sectionWeight = (length*1L) * (width*1L) * (thickness*1L) * (quantity*1L) * sectionWeightConstant;
		
		logger.debug("length="+length+"* width="+width+"* thickness="+thickness +"* quantity="+quantity+"* sectionWeightConstant="+sectionWeightConstant);
		logger.debug("sectionWeight = "+sectionWeight);
		return sectionWeight;
	}
	
}
