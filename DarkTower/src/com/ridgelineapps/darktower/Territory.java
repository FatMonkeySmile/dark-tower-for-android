/*
 * Copyright (C) 2012 Dark Tower for Android
 *   (http://code.google.com/p/dark-tower-for-android)
 * 
 * This program is free software: you can redistribute it and/or 
 * modify it under the terms of the GNU General Public License as published 
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *   
 * This source code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Originally from --
 * 07/24/2002 - 20:37:20
 *
 * Territory.java
 * Copyright (C) 2002 Michael Bommer
 * m_bommer@yahoo.de
 * www.well-of-souls.com/tower
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package com.ridgelineapps.darktower;

import java.util.List;

import android.graphics.Color;
import android.graphics.Path;

import com.ridgelineapps.darktower.java.Point;
import com.ridgelineapps.darktower.java.Polygon;

public class Territory
{
    boolean centreValid = false;
    
	public static final int NA = 0;
	public static final int STANDARD = 1;
	public static final int CITADEL = 2;
	public static final int SANCTUARY = 3;
	public static final int TOMB = 4;
	public static final int RUIN = 5;
	public static final int BAZAAR = 6;
	public static final int FRONTIER = 7;
	public static final int DARKTOWER = 8;
	
	public static final int[] KINGDOMLIST = { 9, 39, 69, 99 };
	public static final int[] FRONTIERLIST = { 5, 6, 7, 8 };
	public static final int[] DARKTOWERLIST = { 1, 2, 3, 4 };
	public static int[] CITADELLIST = new int[4]; //{ 34, 64, 94, 124 };
	public static int[] SANCTUARYLIST = new int[4]; //{ 29, 59, 89, 119 };
	public static int[] TOMBLIST = new int[4]; //{ 25, 55, 85, 115 };
	public static int[] RUINLIST = new int[4]; //{ 21, 51, 81, 111 };
	public static int[] BAZAARLIST = new int[4]; //{ 14, 44, 74, 104 };

   public static int[] COLORLIST = new int[] { 
       Color.rgb(238, 20, 10), 
       Color.rgb(51, 180, 20), 
       Color.rgb(20, 102, 225), 
       Color.rgb(255, 193, 0), 
        }; 

	private int territoryNo = 0;
	private int kingdomNo = 0;
	private int type = STANDARD;
	private int color;
	private Polygon polygon = null;
	private Path path = null;
	private Point centre = null;
	private List neighborList = null;

	public Territory()
	{
	}

	public Territory(int territoryNo, int kingdomNo, int type, int color)
	{
		this.territoryNo = territoryNo;
		this.kingdomNo = kingdomNo;
		this.color = color;
		this.type = type;
	}

	public void setTerritoryNo(int territoryNo)
	{
		this.territoryNo = territoryNo;
	}

	public int getTerritoryNo()
	{
		return territoryNo;
	}

	public void setKingdomNo(int kingdomNo)
	{
		this.kingdomNo = kingdomNo;
	}

	public int getKingdomNo()
	{
		return kingdomNo;
	}

	public void setType(int type)
	{
		this.type = type;
	}

	public int getType()
	{
		return type;
	}

	public void setColor(int color)
	{
		this.color = color;
	}

	public int getColor()
	{
		return color;
	}
	
	public void setCenter(int x, int y) {
	    centre = new Point(x, y);
        centreValid = true;
	}

	public void setPolygon(Polygon polygon)
	{
		this.polygon = polygon;
		calcCentre();
	}

	public Polygon getPolygon()
	{
		return polygon;
	}
	
	public Path getPath() {
	   if(path == null && polygon != null) {
	      path = new Path();
	      for(int i=0; i < polygon.npoints; i++) {
	         int x = polygon.xpoints[i];
	         int y = polygon.ypoints[i];

	         if(i == 0) {
	            path.moveTo(x, y);
	         }
	         else {
               path.lineTo(x, y);	            
	         }
	      }
	      path.close();
	   }
	   
	   return path;
	}

	public boolean intersects(Territory territory)
	{
		Polygon poly = territory.getPolygon();
		
		for (int i = 0; i < polygon.npoints; i++)
		{
			for (int j = 0; j < poly.npoints; j++)
			{
				if ( ( polygon.xpoints[i] == poly.xpoints[j] ) &&
					 ( polygon.ypoints[i] == poly.ypoints[j] ) )
				{
					return true;
				}
			}
		}

		return false;
	}
	
	public boolean overlaps(Territory territory) {
	    for(int i=0; i < territory.polygon.npoints; i++) {
	        if(polygon.contains(territory.polygon.xpoints[i], territory.polygon.ypoints[i])) {
	            return true;
	        }
	    }
        for(int i=0; i < polygon.npoints; i++) {
            if(territory.polygon.contains(polygon.xpoints[i], polygon.ypoints[i])) {
                return true;
            }
        }
        return false;
	}

	public void calcCentre()
	{
	    if(!centreValid) {
    		int x = 0;
    		int y = 0;
    
    		for (int i = 0; i < polygon.npoints; i++)
    		{
    			x += polygon.xpoints[i];
    			y += polygon.ypoints[i];
    		}
		
    		centre = new Point(x / polygon.npoints, y / polygon.npoints);
    		centreValid = true;
	    }
	}

	public Point getCentre()
	{
		return centre;
	}

	public void setNeighborList(List neighborList)
	{
		this.neighborList = neighborList;
	}

	public List getNeighborList()
	{
		return neighborList;
	}
}
