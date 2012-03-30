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

import android.graphics.Path;

import com.ridgelineapps.darktower.java.Point;
import com.ridgelineapps.darktower.java.Polygon;

public class Territory
{
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
	public static int[] CITADELLIST = { 34, 64, 94, 124 };
	public static int[] SANCTUARYLIST = { 29, 59, 89, 119 };
	public static int[] TOMBLIST = { 25, 55, 85, 115 };
	public static int[] RUINLIST = { 21, 51, 81, 111 };
	public static int[] BAZAARLIST = { 14, 44, 74, 104 };

	//TODO
//	public static final Color[] COLORLIST = 
//		{ new Color(255, 0, 0), new Color(0, 255, 0),
//		  new Color(0, 0, 255), new Color(255, 255, 0) };

	private int territoryNo = 0;
	private int kingdomNo = 0;
	private int type = STANDARD;
	private int color;
	private Polygon polygon = null;
	private Path path = null;
	private Point centre = null;
	private List neigborList = null;

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

	public void calcCentre()
	{
		int x = 0;
		int y = 0;

		for (int i = 0; i < polygon.npoints; i++)
		{
			x += polygon.xpoints[i];
			y += polygon.ypoints[i];
		}
		
		centre = new Point(x / polygon.npoints, y / polygon.npoints);
	}

	public Point getCentre()
	{
		return centre;
	}

	public void setNeigborList(List neigborList)
	{
		this.neigborList = neigborList;
	}

	public List getNeigborList()
	{
		return neigborList;
	}
}
