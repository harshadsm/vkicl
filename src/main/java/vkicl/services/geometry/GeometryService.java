package vkicl.services.geometry;

import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.Area;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public interface GeometryService {
	
	String toInsertSql(Shape s);//Done
	Polygon toPolygon(String mysqlGeometryPolygonAsText);//Done
	List<Area> findAreaGreterThan(Area smallerPlateToBeCut);//@TODO for Shweta
	List<Polygon> findPolygonGreterThan(Polygon smallerPlateToBeCut);//@TODO for Shweta
	List<Area> cut(Double originX, Double originY, Double smallPlateLength, Double smallPlateWidth, Shape biggerPlate);//DONE
	Shape toPolygon(Double originX, Double originY, Double length, Double width);//Done

	
}
