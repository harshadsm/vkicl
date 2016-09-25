package com.harshad.geometry;

import java.awt.Polygon;
import java.awt.geom.Area;
//STEP 1. Import required packages
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FirstExample {
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/vkicl";

	// Database credentials
	static final String USER = "root";
	static final String PASS = "Newuser123";

	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		try {
			// STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// STEP 4: Execute a query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT ST_AsText(g) as gg FROM geom";
			ResultSet rs = stmt.executeQuery(sql);

			// STEP 5: Extract data from result set
			while (rs.next()) {

				String gg = rs.getString("gg");
				System.out.println(gg);

				processPointsIntoPolygon(gg);
			}
			// STEP 6: Clean-up environment
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			} // nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
		System.out.println("Goodbye!");
	}// end main

	private static void processPointsIntoPolygon(String points) {
		
		if (points != null && !points.isEmpty()) {
			int endIndex = points.length() - 2;
			String p = points.substring(9, endIndex);
			Polygon poly = formPolygon(p);
			Area polygonArea = new Area(poly);
			System.out.println("MySQL = "+p);
			
			System.out.println("Area = "+polygonArea.isRectangular());

		}

	}

	private static Polygon formPolygon(String p) {
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
}// end FirstExample