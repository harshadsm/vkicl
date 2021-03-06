package vkicl.services.geometry;

import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;

import vkicl.daoImpl.BaseDaoImpl;
import vkicl.daoImpl.StockBalDaoImpl;
import vkicl.exceptions.CutNotPossibleException;
import vkicl.vo.StockBalanceDetailsVO;

public class GeometryServiceImpl implements GeometryService {

	private final String TABLE_1 = "geom";

	@Override
	public List<Area> findAreaGreterThan(Area smallerPlateToBeCut) {
		throw new NotImplementedException("Shweta, please implement this at DAO layer. And call it here.");
	}

	@Override
	public List<Polygon> findPolygonGreterThan(Polygon smallerPlateToBeCut) {
		throw new NotImplementedException("Shweta, please implement this at DAO layer. And call it here.");
	}

	@Override
	public List<Area> cut(Double originX, Double originY, Double smallPlateLength, Double smallPlateWidth,
			Shape biggerPlate) throws CutNotPossibleException {
		boolean isCutPossible = false;
		Path2D path = new Path2D.Double();
		path.moveTo(originX, originY);
		path.lineTo(originX + smallPlateWidth, originY);
		path.lineTo(originX + smallPlateWidth, originY + smallPlateLength);
		path.lineTo(originX, originY + smallPlateLength);
		path.closePath();

		Area smallPlate = new Area(path);
		Area bigPlateForCutting = new Area(biggerPlate);
		bigPlateForCutting.intersect(smallPlate);

		if (bigPlateForCutting.equals(smallPlate)) {
			isCutPossible = true;
		} else {
			isCutPossible = false;
			throw new CutNotPossibleException("Cut not possible with given dimensions and positioning.");
		}

		Area remainingPlateAfterCut = new Area(biggerPlate);
		remainingPlateAfterCut.subtract(smallPlate);

		List<Area> cutPlates = new ArrayList<Area>();
		cutPlates.add(smallPlate);
		cutPlates.add(remainingPlateAfterCut);

		System.out.println("Small Plate area = " + smallPlate.toString());
		System.out.println("Remaining Plate area = " + remainingPlateAfterCut.toString());

		return cutPlates;
	}

	@Override
	public Shape toPolygon(Double originX, Double originY, Double length, Double width) {
		Path2D path = new Path2D.Double();
		path.moveTo(originX, originY);
		path.lineTo(originX + width, originY);
		path.lineTo(originX + width, originY + length);
		path.lineTo(originX, originY + length);
		path.closePath();
		return path;
	}

	@Override
	public String toInsertSql(Shape s, StockBalanceDetailsVO vo) {

		return prepareInsertSql(s, vo);
	}

	public String toUpdateSql(Shape s, Long stockBalId, double area) {

		return prepareUpdateSql(s, stockBalId, area);
	}

	/**
	 * If a query similar to "SELECT AsText(g) as gg FROM geom" is run, whatever
	 * is returned by MySql should be passed to this function as
	 * mysqlGeometryPolygonAsText.
	 */
	@Override
	public Polygon toPolygon(String mysqlGeometryPolygonAsText) {
		return processPointsIntoPolygon(mysqlGeometryPolygonAsText);
	}

	private Polygon processPointsIntoPolygon(String points) {

		Polygon poly = null;
		if (points != null && !points.isEmpty()) {
			int endIndex = points.length() - 2;
			String p = points.substring(9, endIndex);
			poly = formPolygon(p);
			Area polygonArea = new Area(poly);
			System.out.println("MySQL = " + p);

			System.out.println("Is rectangular = " + polygonArea.isRectangular());

		}
		return poly;
	}

	private Polygon formPolygon(String p) {
		Polygon poly = new Polygon();

		p = p.trim();
		String[] pairs = p.split(",");
		if (pairs != null && pairs.length > 0) {
			for (String pair : pairs) {
				String[] coordinates = pair.split(" ");

				if (coordinates != null && coordinates.length == 2) {
					Integer x = Integer.parseInt(coordinates[0]);
					Integer y = Integer.parseInt(coordinates[1]);

					poly.addPoint(x, y);

				}

			}
		}
		return poly;
	}

	private String prepareInsertSql(Shape s, StockBalanceDetailsVO vo) {

		List<Double[]> coordinatesList = getCoordinatesList(s);

		// Remove the last coordinate because it is a closing one. And usually
		// contains 0,0.
		if (coordinatesList != null && coordinatesList.size() >= 1) {
			coordinatesList.remove(coordinatesList.size() - 1);
		}

		Double width = s.getBounds2D().getWidth();
		Double length = s.getBounds2D().getHeight();
		Area a = new Area(s);
		Integer isRectangular = a.isRectangular() ? 1 : 0;
		String sql = prepareInsertSql(coordinatesList, isRectangular, length, width, vo);

		return sql;
	}

	private String prepareUpdateSql(Shape s, Long stockBalId, double area) {

		List<Double[]> coordinatesList = getCoordinatesList(s);

		// Remove the last coordinate because it is a closing one. And usually
		// contains 0,0.
		coordinatesList.remove(coordinatesList.size() - 1);
		Area a = new Area(s);
		Integer isRectangular = a.isRectangular() ? 1 : 0;
		String sql = prepareUpdateSql(coordinatesList, isRectangular, stockBalId, area);

		return sql;
	}

	private String prepareInsertSql(List<Double[]> coordinatesList, Integer isRectangular, Double length, Double width,
			StockBalanceDetailsVO vo) {
		StringBuilder sql = new StringBuilder();
		// For whatever weired reasons, ST_AsText does not work on server.
		// AsText works.
		// In fact any function prefixed with ST_ does not work on server.
		// Hence removing ST_ prefix from the queries.
		// sql.append("INSERT INTO ").append("stock_balance").append("
		// (plate_shape, mill_name, material_make, material_type, "
		// + " grade, length, width, thickness, plate_area, quantity,
		// is_rectangular) ").
		// append(" VALUES (").append("(ST_GeomFromText('POLYGON((");

		sql.append("INSERT INTO ").append("stock_balance")
				.append(" (plate_shape, mill_name, material_make, material_type, ")
				.append(" grade, length, width, thickness, plate_area, quantity, is_rectangular, location, heat_no, plate_no, warehouse_inward_id )  ")
				.append(" VALUES (").append("(GeomFromText('POLYGON((");

		for (Double[] coords : coordinatesList) {
			sql.append(coords[0]).append(" ").append(coords[1]).append(",");
		}

		// Add first coordinate as last coordinate. As required by MySql syntax
		Double[] firstCoords = coordinatesList.get(0);
		sql.append(firstCoords[0]).append(" ").append(firstCoords[1]);

		sql.append("))'))")
				.append(",'" + vo.getMillName() + "','" + vo.getMake() + "','" + vo.getMaterialType() + "'," + " '"
						+ vo.getGrade() + "'," + length + "," + width + "," + vo.getThickness() + "," + (length * width)
						+ ", 1," + isRectangular + ",'" + vo.getLocation() + "','" + vo.getHeat_no() + "','"
						+ vo.getPlate_no() + "','" + vo.getWarehouseInwardId() + "')");

		return sql.toString();
	}

	private String prepareUpdateSql(List<Double[]> coordinatesList, Integer isRectangular, Long stockBalId,
			double area) {
		StringBuilder sql = new StringBuilder();

		// For whatever weired reasons, ST_AsText does not work on server.
		// AsText works.
		// In fact any function prefixed with ST_ does not work on server.
		// Hence removing ST_ prefix from the queries.
		// sql.append("update ").append("stock_balance").append(" set ")
		// .append(" is_rectangular = ").append(isRectangular).append(", ")
		// .append(" plate_shape= ").append("(ST_GeomFromText('POLYGON((");

		sql.append("update ").append("stock_balance").append(" set ").append(" is_rectangular = ").append(isRectangular)
				.append(", ").append(" plate_shape= ").append("(GeomFromText('POLYGON((");

		for (Double[] coords : coordinatesList) {
			sql.append(coords[0]).append(" ").append(coords[1]).append(",");
		}

		// Add first coordinate as last coordinate. As required by MySql syntax
		Double[] firstCoords = coordinatesList.get(0);
		sql.append(firstCoords[0]).append(" ").append(firstCoords[1]);

		sql.append("))'))").append(", plate_area= " + area).append(" where stock_balance_id=" + stockBalId);

		// StockBalDaoImpl impl=new StockBalDaoImpl();
		// impl.updateStockBalanceShape(sql.toString());

		return sql.toString();
	}

	/*
	 * private String prepareInsertSql(List<Double[]> coordinatesList) {
	 * StringBuilder sql = new StringBuilder(); sql.append("INSERT INTO "
	 * ).append(TABLE_1).append(" ").append(" VALUES "
	 * ).append("(GeomFromText('POLYGON((");
	 * 
	 * for (Double[] coords : coordinatesList) { sql.append(coords[0]).append(
	 * " ").append(coords[1]).append(","); }
	 * 
	 * // Add first coordinate as last coordinate. As required by MySql syntax
	 * Double[] firstCoords = coordinatesList.get(0);
	 * sql.append(firstCoords[0]).append(" ").append(firstCoords[1]);
	 * 
	 * sql.append("))'));");
	 * 
	 * return sql.toString(); }
	 */

	public List<Double[]> getCoordinatesList(Shape s) {
		List<Double[]> coordinatesList = new LinkedList<Double[]>();
		PathIterator pi = s.getPathIterator(null);
		while (pi.isDone() == false) {
			double[] coordinates = getCoordinate(pi);
			// sb.append(coordinates[0]).append("
			// ").append(coordinates[1]).append(",");
			Double[] coordss = new Double[] { coordinates[0], coordinates[1] };
			coordinatesList.add(coordss);

			pi.next();
		}

		return coordinatesList;
	}

	private double[] getCoordinate(PathIterator pi) {
		double[] coordinates = new double[6];
		int type = pi.currentSegment(coordinates);
		switch (type) {
		case PathIterator.SEG_MOVETO:
			System.out.println("move to " + coordinates[0] + ", " + coordinates[1]);
			break;
		case PathIterator.SEG_LINETO:
			System.out.println("line to " + coordinates[0] + ", " + coordinates[1]);
			break;
		case PathIterator.SEG_QUADTO:
			System.out.println("quadratic to " + coordinates[0] + ", " + coordinates[1] + ", " + coordinates[2] + ", "
					+ coordinates[3]);
			break;
		case PathIterator.SEG_CUBICTO:
			System.out.println("cubic to " + coordinates[0] + ", " + coordinates[1] + ", " + coordinates[2] + ", "
					+ coordinates[3] + ", " + coordinates[4] + ", " + coordinates[5]);
			break;
		case PathIterator.SEG_CLOSE:
			System.out.println("close");
			break;
		default:
			break;

		}
		return coordinates;
	}

}
