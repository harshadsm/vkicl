package vkicl.util;

public class SectionWeightCalculator {

	public static Double calculateSectionWeight(Double t, Integer l, Integer w, Integer q){
		Double sectionWeight = 0.0d;
		
		if(t!=null && l!=null && w!=null && q!=null){
			sectionWeight = t * l * w * q * 7.85d / 1000000000;
		}
		
		return sectionWeight;
	}
}
