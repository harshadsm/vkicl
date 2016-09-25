package com.harshad.geometry;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.geom.Area;

public class Geom extends Applet{
	
	
	public void paint(Graphics g)
	  {
//	    int x[] = { 70, 150, 190, 80, 100 };
//	    int y[] = { 80, 110, 160, 190, 100 };
//	    g.drawPolygon (x, y, 5);
//	 
//	    int x1[] = { 210, 280, 330, 210, 230 };
//	    int y1[] = { 70, 110, 160, 190, 100 };
//	    g.fillPolygon (x1, y1, 5);
		
		polygon(g);
	  }
	
	public void polygon(Graphics g){
		int x[] = { 10, 300, 300, 10 };
	    int y[] = { 10, 10, 600, 600 };
	    Polygon p = new Polygon(x,y,4);
	    g.drawPolygon(p);
		
		
		int x2[] = { 10, 150, 150, 10 };
	    int y2[] = { 10, 10, 200, 200 };
	    Polygon p2 = new Polygon(x2,y2,4);
	    g.drawPolygon(p2);
	    
	    Area a1 = new Area(p);
	    Area a2 = new Area(p2);
	    a2.intersect(a1);
	    if(a1.equals(a2)){
	    	System.out.println("A2 accomodated in A1");
	    }else{
	    	System.out.println("A2 NOT accomodated in A1");
	    }
	    
	}
	 
}
