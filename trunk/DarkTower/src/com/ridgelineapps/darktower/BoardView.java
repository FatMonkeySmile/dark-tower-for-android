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
 * BoardPanel.java
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

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Region;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.ridgelineapps.darktower.java.Point;
import com.ridgelineapps.darktower.java.Polygon;

public class BoardView extends View implements OnTouchListener
{
   Resources res;
   Paint paint;
   Paint highlightP1;
   Paint highlightP2;
   Paint borderP;
   Paint bitmapP;
   Paint debugP;

   int width;
   int height;
   
   private static final int HIGHLIGHTED_DRAGON = 100;
	private List territoryList = null;
	private Bitmap boardBitmap = null;
	private Bitmap grayBoardBitmap = null;
	private List playerList = null;
	private int playerNo = 0;
	private int highlightedTerritoryNo = 0;
   private int highlightedPlayerOrDragon = -1;
	private Dragon dragon = null;
	
	// 8-bit bitmaps
	private Bitmap classmImageIcon = null;
	private Bitmap dragonImageIcon = null;
	private Bitmap isoImageIcon = null;
	private Bitmap kingdomBitmap[] = null;
	private Bitmap frontierBitmap = null;
	private Bitmap darkTowerBitmap = null;
	
	// Orig bitmaps
	private Bitmap[][] playerBitmaps = null;
	private Bitmap dragonBitmap = null;
	
	private boolean inited = false;
	private boolean originalBoard = true;

	enum DrawType {
      NORMAL(0),
      HIGHLIGHT(1),
      SELECTED(2);
      
      int index;
      DrawType(int index) {
         this.index = index;
      }
   }
	
	ActivityGame activity;

   public BoardView(Context context, AttributeSet attributes)
   {
      super(context, attributes);
	  activity = (ActivityGame) context;
	  init(true);
   }
   
   public void init(boolean originalBoard) {
      this.originalBoard = originalBoard;
	   res = activity.getResources();
	   paint = new Paint();
	   paint.setStyle(Paint.Style.FILL);
	   
      highlightP1 = new Paint();
//      highlightP1.setStyle(Paint.Style.FILL);
      //TODO temp, until translucent bitmap (and gray image) can be finished
      highlightP1.setStyle(Paint.Style.STROKE);
      highlightP1.setStrokeWidth(4);
      
      highlightP2 = new Paint();
      highlightP2.setAlpha(128);
      highlightP2.setStyle(Paint.Style.FILL);
      
      borderP = new Paint();
      borderP.setStyle(Paint.Style.STROKE);
      
      bitmapP = new Paint();
      //TODO: turning all three on causes painting to be very slow...
//      bitmapP.setDither(true);
//      bitmapP.setFilterBitmap(true);
//      bitmapP.setAntiAlias(true);
      
      debugP = new Paint();
      debugP.setStyle(Paint.Style.STROKE);
      debugP.setStrokeWidth(1);
      debugP.setColor(Color.rgb(255, 0, 0));
    
      if(!originalBoard) {
      	classmImageIcon = MultiImage.getBitmap(res, MultiImage.CLASSM);
      	dragonImageIcon = MultiImage.getBitmap(res, MultiImage.DRAGON);
      	isoImageIcon = MultiImage.getBitmap(res, MultiImage.ISO);
      }
      else {
         dragonBitmap = BitmapFactory.decodeResource(res, R.drawable.orig_dragon);
         
         playerBitmaps = new Bitmap[4][3];
         playerBitmaps[0][0] = BitmapFactory.decodeResource(res, R.drawable.orig_p1);
         playerBitmaps[0][1] = BitmapFactory.decodeResource(res, R.drawable.orig_p1);
         playerBitmaps[0][2] = BitmapFactory.decodeResource(res, R.drawable.orig_p1);

         playerBitmaps[1][0] = BitmapFactory.decodeResource(res, R.drawable.orig_p2);
         playerBitmaps[1][1] = BitmapFactory.decodeResource(res, R.drawable.orig_p2);
         playerBitmaps[1][2] = BitmapFactory.decodeResource(res, R.drawable.orig_p2);

         playerBitmaps[2][0] = BitmapFactory.decodeResource(res, R.drawable.orig_p3);
         playerBitmaps[2][1] = BitmapFactory.decodeResource(res, R.drawable.orig_p3);
         playerBitmaps[2][2] = BitmapFactory.decodeResource(res, R.drawable.orig_p3);
         
         playerBitmaps[3][0] = BitmapFactory.decodeResource(res, R.drawable.orig_p4);
         playerBitmaps[3][1] = BitmapFactory.decodeResource(res, R.drawable.orig_p4);
         playerBitmaps[3][2] = BitmapFactory.decodeResource(res, R.drawable.orig_p4);
      }

      DisplayMetrics dm = new DisplayMetrics();
      activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
      width = dm.widthPixels;
      height = dm.heightPixels;
      
      width = Math.min(width, height);
      height = width;
		
		territoryList = newTerritoryList();
		createTexture();
		createTerritories();
		createNeighbors();
		createTerritoryPlaces(true);
      createBoard();
      
      setOnTouchListener(this);
      inited = true;
	}
   
   @Override
   public boolean onTouch(View v, MotionEvent event) {
      if(activity.darkTower.thread != null) {
         if(event.getAction() == MotionEvent.ACTION_CANCEL || event.getAction() == MotionEvent.ACTION_OUTSIDE) {
            activity.darkTower.thread.addMouseAction(new MouseAction(MouseAction.EXITED, (int) event.getX(), (int) event.getY())); 
            return true;
         }
         else if(event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
            activity.darkTower.thread.addMouseAction(new MouseAction(MouseAction.MOVED, (int) event.getX(), (int) event.getY()));
            return true;
         }
         else if(event.getAction() == MotionEvent.ACTION_UP) {
            activity.darkTower.thread.addMouseAction(new MouseAction(MouseAction.CLICKED, (int) event.getX(), (int) event.getY()));
            return true;
         }
      }
      return false;
   }

   @Override
   protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
      setMeasuredDimension(width, height);
   }
	
   @Override
   protected void onDraw(Canvas canvas) {

      if (!inited) {
          //TODO: figure out how to call from activity
//          init(activity.originalBoard);
//         return;
      }

      // int width = canvas.getWidth();
      // int height = canvas.getHeight();
      // float dx = width / 2;
      // float dy = height / 2;
      // canvas.translate(dx, dy);
      Player player = null;
      Territory territory = null;
      int color;
      Point point = null;
      int r = (int) (getTerritoryRadius() * 0.7);

      canvas.drawBitmap(boardBitmap, 0, 0, paint);

      if (!originalBoard) {
         // draw highlighted territory
         try {
            if (highlightedTerritoryNo > 0) {
               territory = (Territory) territoryList.get(highlightedTerritoryNo - 1);
               highlightP1.setColor(Territory.COLORLIST[playerNo]);
               canvas.drawPath(territory.getPath(), highlightP1);
               // highlightP2.setColor(Territory.COLORLIST[playerNo]);
               // canvas.drawPath(territory.getPath(), highlightP1);
               //
               // canvas.clipPath(territory.getPath(), Region.Op.REPLACE);
               // canvas.drawBitmap(grayBoardBitmap, 0, 0, highlightP2);
               // canvas.clipRect(0, 0, width, height, Region.Op.REPLACE);
               //
               // canvas.drawPath(territory.getPath(), borderP);
            }
         } catch (Throwable e) {
            e.printStackTrace();
         }

         // draw buildings
         for (int i = 0; i < territoryList.size(); i++) {
            territory = (Territory) territoryList.get(i);
            point = territory.getCentre();
            switch (territory.getType()) {
            case Territory.BAZAAR:
               MultiImage.drawSubImage(canvas, isoImageIcon, MultiImage.ISO, MultiImage.BAZAAR[territory.getKingdomNo()], point.x, point.y);
               break;
            case Territory.SANCTUARY:
               MultiImage.drawSubImage(canvas, isoImageIcon, MultiImage.ISO, MultiImage.SANCTUARY[territory.getKingdomNo()], point.x, point.y);
               break;
            case Territory.RUIN:
               MultiImage.drawSubImage(canvas, isoImageIcon, MultiImage.ISO, MultiImage.RUIN[territory.getKingdomNo()], point.x, point.y);
               break;
            case Territory.TOMB:
               MultiImage.drawSubImage(canvas, isoImageIcon, MultiImage.ISO, MultiImage.TOMB[territory.getKingdomNo()], point.x, point.y);
               break;
            default:
               break;
            }
         }
         MultiImage.drawSubImage(canvas, isoImageIcon, MultiImage.ISO, MultiImage.DARKTOWER, width / 2, height / 2);

         // draw citadel
         for (int i = 0; i < 4; i++) {
            territory = (Territory) territoryList.get(Territory.CITADELLIST[i] - 1);
            point = territory.getCentre();
            MultiImage.drawSubImage(canvas, isoImageIcon, MultiImage.ISO, MultiImage.CITADEL[i], point.x, point.y);
         }
      }
      
      if(originalBoard) {
          try {
              if (highlightedTerritoryNo > 0) {
                 territory = (Territory) territoryList.get(highlightedTerritoryNo - 1);
                 point = territory.getCentre();
                 drawPlayer(canvas, point, playerNo, DrawType.HIGHLIGHT);
              }
           } catch (Throwable e) {
              e.printStackTrace();
           }

//          
//          if (highlightedTerritoryNo > 0) {
//            territory = (Territory) territoryList.get(highlightedTerritoryNo - 1);
//            point = territory.getCentre();
//            if(highlightedPlayerOrDragon == HIGHLIGHTED_DRAGON) {
//               drawDragon(canvas, point, DrawType.HIGHLIGHT);
//            }
//            else {
//               drawPlayer(canvas, point, highlightedPlayerOrDragon, DrawType.HIGHLIGHT);
//            }
//         }
         
//         for(int i=0; i < territoryList.size(); i++) {
//             territory = (Territory) territoryList.get(i);
//             point = territory.getCentre();
//             canvas.drawText("" + territory.getTerritoryNo(), point.x, point.y, debugP);
//          }         
      }

//      boolean movingDragon = false;
      
      // draw player
      if (playerList != null) {
         for (int i = 0; i < playerList.size(); i++) {
            player = (Player) playerList.get(i);
            point = nextCoord(player);
            DrawType drawType = DrawType.NORMAL;
            if(player.isPerformAction()) {
               drawType = DrawType.SELECTED;
            }
            drawPlayer(canvas, point, i, drawType);
//            if(player.isPlaceDragon()) {
//               movingDragon = true;
//            }
         }
      }

      // draw dragon
      if (dragon != null) {
         if (dragon.getStartTerritoryNo() != 0) {
            point = nextCoord(dragon);
            drawDragon(canvas, point);
         } else if (dragon.getEndTerritoryNo() != 0) {
            territory = (Territory) territoryList.get(dragon.getEndTerritoryNo() - 1);
            point = territory.getCentre();
            drawDragon(canvas, point);
            dragon.setStartTerritoryNo(dragon.getEndTerritoryNo());
         }
      }
   }

   public void createTexture() {
      if(!originalBoard) {
         kingdomBitmap = new Bitmap[4];
         for (int i = 0; i < 4; i++)
            kingdomBitmap[i] = MultiImage.getTexture(isoImageIcon, MultiImage.ISO, i, width, height);
   
         frontierBitmap = MultiImage.getTexture(isoImageIcon, MultiImage.ISO, 4, width, height);
         darkTowerBitmap = MultiImage.getTexture(isoImageIcon, MultiImage.ISO, 5, width, height);
      }
   }

	public void createTerritories()
	{
	   if(!originalBoard) {
   		int number = 8;
   		Polygon polygon = null;
   		Territory territory = null;
   		List aboveList = null;
   		List belowList = null;
   		int r = getTerritoryRadius();
   
   		for (int i = 0; i < 4; i++)
   		{
   			polygon = getDarkTowerPolygon(4, r, 225 + (i * 90));
   			territory = (Territory) territoryList.get(i);
   			territory.setPolygon(polygon);
   		}
   
   		for (int i = 0; i < 4; i++)
   		{
   			polygon = getFrontierPolygon(5, r, 310 + (i * 90));
   			territory = (Territory) territoryList.get(i + 4);
   			territory.setPolygon(polygon);
   		}
   
   		for (int i = 0; i < 4; i++)
   		{
   			for (int j = 0; j < 5; j++)
   			{
   				aboveList = getPointList(4 + j, r + (j * r), 230 + (i * 90), 310 + (i * 90));
   				belowList = getPointList(5 + j, (2 * r) + (j * r), 230 + (i * 90), 310 + (i * 90));
   				for (int k = 0; k < 4 + j; k++)
   				{
   					polygon = getTerritoryPolygon(k, aboveList, belowList);
   					territory = (Territory) territoryList.get(number);
   					territory.setPolygon(polygon);
   					number += 1;
   				}
   			}
   		}
	   }
	   else {
	      Territory territory;
	      for(int i=0; i < OrigTerritories.terr.length; i++) {
	         Polygon polygon = new Polygon();
	         for(int j=0; j < OrigTerritories.terr[i].length; j++) {
	            polygon.addPoint(OrigTerritories.terr[i][j][0], OrigTerritories.terr[i][j][1]);
	         }
            territory = (Territory) territoryList.get(i);
            territory.setPolygon(polygon);
	      }
	   }
	}

	public void createNeighbors()
	{
		Territory srcTerritory = null;
		ArrayList srcNeighborList = null;
		Territory destTerritory = null;
		Integer destNumber = null;

		for (int i = 0; i < territoryList.size(); i++)
		{
			srcTerritory = (Territory) territoryList.get(i);
			srcNeighborList = new ArrayList();
			for (int j = 0; j < territoryList.size(); j++)
			{
				if ( i != j )
				{
					destTerritory = (Territory) territoryList.get(j);
					destNumber = new Integer( destTerritory.getTerritoryNo() );
					boolean add = false;
                    if(!originalBoard) {
    					if ( srcTerritory.intersects(destTerritory) )
    					{
    					    add = true;
    					}
                    }
                    else {
                        if ( srcTerritory.overlaps(destTerritory) )
                        {
                            add = true;
                        }
                    }
                    
                    if(add && !srcNeighborList.contains(destNumber) )
					{
						srcNeighborList.add(destNumber);
					}
				}
			}
			srcTerritory.setNeighborList(srcNeighborList);
		}
	}

	public void createTerritoryPlaces(boolean placeBuildingsRandomly)
	{
		Territory territory = null;

		do
		{
			for (int i = 8; i < territoryList.size(); i++)
			{
				territory = (Territory) territoryList.get(i);
				territory.setType(Territory.STANDARD);
			}
		}
		while (	!createTerritoryTypes(placeBuildingsRandomly) );
	}

	public boolean createTerritoryTypes(boolean placeBuildingsRandomly)
	{
		Territory territory = null;
		int territoryNo = 0;
		int count = 0;

		if ( placeBuildingsRandomly && !originalBoard)
		{
			for (int i = 0; i < 4; i++)
			{
				for (int j = 0; j < 5; j++)
				{
					count = 0;
					do
					{
						territoryNo = (int) (Territory.KINGDOMLIST[i] + Math.random() * 30);
						count++;
						if ( count > 256 )
							return false;
					}
					while ( isOccupied(territoryNo) );

					territory =	(Territory) territoryList.get(territoryNo - 1);
					switch ( j )
					{
						case 0:
							Territory.CITADELLIST[i] = territoryNo;
							territory.setType(Territory.CITADEL);
							break;
						case 1:
							Territory.SANCTUARYLIST[i] = territoryNo;
							territory.setType(Territory.SANCTUARY);
							break;
						case 2:
							Territory.BAZAARLIST[i] = territoryNo;
							territory.setType(Territory.BAZAAR);
							break;
						case 3:
							Territory.RUINLIST[i] = territoryNo;
							territory.setType(Territory.RUIN);
							break;
						case 4:
							Territory.TOMBLIST[i] = territoryNo;
							territory.setType(Territory.TOMB);
							break;
						default:
							break;
					}
				}
			}
		}
		else
		{
		    if(originalBoard) {
                Territory.CITADELLIST = new int[] { 12, 58, 79, 116 };
                Territory.SANCTUARYLIST = new int[] { 22, 62, 87, 119 };
                Territory.TOMBLIST = new int[] { 17, 42, 72, 109 };
                Territory.RUINLIST = new int[] { 123, 55, 84, 102 };
                Territory.BAZAARLIST = new int[] { 34, 45, 77, 98 };
    
                for (int i = 0; i < 4; i++)
                {
                    territory = (Territory) territoryList.get(Territory.CITADELLIST[i] - 1);
                    territory.setType(Territory.CITADEL);
                    territory = (Territory) territoryList.get(Territory.SANCTUARYLIST[i] - 1);
                    territory.setType(Territory.SANCTUARY);
                    territory = (Territory) territoryList.get(Territory.TOMBLIST[i] - 1);
                    territory.setType(Territory.TOMB);
                    territory = (Territory) territoryList.get(Territory.RUINLIST[i] - 1);
                    territory.setType(Territory.RUIN);
                    territory = (Territory) territoryList.get(Territory.BAZAARLIST[i] - 1);
                    territory.setType(Territory.BAZAAR);
                }		        
		    }
		    else {
                Territory.CITADELLIST = new int[] { 34, 64, 94, 118 };
    			Territory.SANCTUARYLIST = new int[] { 29, 59, 89, 119 };
    			Territory.TOMBLIST = new int[] { 25, 55, 85, 115 };
    			Territory.RUINLIST = new int[] { 21, 51, 81, 111 };
    			Territory.BAZAARLIST = new int[] { 14, 44, 74, 104 };
    
    			for (int i = 0; i < 4; i++)
    			{
    				territory =	(Territory) territoryList.get(Territory.CITADELLIST[i] - 1);
    				territory.setType(Territory.CITADEL);
    				territory =	(Territory) territoryList.get(Territory.SANCTUARYLIST[i] - 1);
    				territory.setType(Territory.SANCTUARY);
    				territory =	(Territory) territoryList.get(Territory.TOMBLIST[i] - 1);
    				territory.setType(Territory.TOMB);
    				territory =	(Territory) territoryList.get(Territory.RUINLIST[i] - 1);
    				territory.setType(Territory.RUIN);
    				territory =	(Territory) territoryList.get(Territory.BAZAARLIST[i] - 1);
    				territory.setType(Territory.BAZAAR);
    			}
		    }
		}
		return true;
	}
	
	public boolean isOccupied(int territoryNo)
	{
		Territory territory = (Territory) territoryList.get(territoryNo - 1);
		List neigborList = territory.getNeighborList();
		Territory neigborTerritory = null;
		int neigborTerritoryNo = 0;

		if ( territory.getType() != Territory.STANDARD )
			return true;

		for (int i = 0; i < neigborList.size(); i++)
		{
			neigborTerritoryNo = ((Integer) neigborList.get(i)).intValue();
			neigborTerritory = (Territory) territoryList.get(neigborTerritoryNo - 1);

			if ( neigborTerritory.getType() != Territory.STANDARD )
				return true;
		}

		return false;
	}

	public void createBoard()
	{
	    if(!originalBoard) {
    	    
    	   //TODO: anti-alias and dither?
          Paint p = new Paint();
          p.setDither(true);
          p.setAntiAlias(true);
          p.setStyle(Paint.Style.FILL);
          p.setARGB(255, 0, 0, 0);
          
    //      Paint textP = new Paint();
    //      textP.setDither(true);
    //      textP.setAntiAlias(true);
    //      textP.setStyle(Paint.Style.FILL);
    //      textP.setARGB(255, 255, 255, 255);
          
          Paint outlineP = new Paint();
          outlineP.setDither(true);
          outlineP.setAntiAlias(true);
          outlineP.setStyle(Paint.Style.STROKE);
          outlineP.setARGB(255, 0, 0, 0);
          
          Paint shadeP = new Paint();
          shadeP.setDither(true);
          shadeP.setAntiAlias(true);
          shadeP.setStyle(Paint.Style.FILL);
          shadeP.setARGB(135, 120, 120, 120);
          
    //      Paint debugP = new Paint();
    //      debugP.setStyle(Paint.Style.STROKE);
    //      debugP.setARGB(255, 0, 255, 0);
    //      
    //      Paint debugFillP = new Paint();
    //      debugP.setStyle(Paint.Style.FILL);
    //      debugP.setARGB(255, 0, 0, 255);
          
    		boardBitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
    		
    		Territory territory = null;
    		Canvas canvas = new Canvas(boardBitmap);
    //      canvas.drawRect(0, 0, width, height, p);
    		
    		// draw territories
    		for (int i = 0; i < territoryList.size(); i++)
    		{
    			territory = (Territory) territoryList.get(i);
    			switch ( territory.getType() )
    			{
    				case Territory.DARKTOWER:
    					canvas.clipPath(territory.getPath(), Region.Op.REPLACE);
    					canvas.drawBitmap(darkTowerBitmap, 0, 0, p);
    					break;
    				case Territory.FRONTIER:
                   canvas.clipPath(territory.getPath(), Region.Op.REPLACE);
                   canvas.drawBitmap(frontierBitmap, 0, 0, p);
                   canvas.drawPath(territory.getPath(), shadeP);
                   canvas.drawPath(territory.getPath(), outlineP);
    					break;
    				default:
                   canvas.clipPath(territory.getPath(), Region.Op.REPLACE);
                   canvas.drawBitmap(kingdomBitmap[territory.getKingdomNo()], 0, 0, p);
    
                   canvas.drawPath(territory.getPath(), shadeP);
                   
                   canvas.drawPath(territory.getPath(), outlineP);
    					break;
    			}
    			
    //       canvas.clipRect(0, 0, width, height, Region.Op.REPLACE);
    //			Point point = territory.getCentre();
    //			canvas.drawText(Integer.toString(territory.getTerritoryNo()), point.x - 4, point.y + 4, textP);
    //			canvas.drawText(Integer.toString(territory.getTerritoryNo()), point.x - 3, point.y + 3, p);
    		}
    
    		//TODO
    		grayBoardBitmap = boardBitmap;
    		
    //		RenderingHints renderingHints = g.getRenderingHints();
    //		ColorSpace colorSpace = ColorSpace.getInstance(ColorSpace.CS_GRAY);
    //		ColorConvertOp colorConvertOp = new ColorConvertOp(colorSpace, renderingHints);
    //		grayBoardImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    //		colorConvertOp.filter(boardImage, grayBoardImage);
	    }
	    else {
	        
	        boardBitmap = BitmapFactory.decodeResource(res, R.drawable.original_board);
//	        Bitmap scaledBitmap = Bitmap.createScaledBitmap(boardBitmap, width, height, false);
//	        boardBitmap.recycle();
//	        boardBitmap = scaledBitmap;
	        
	        grayBoardBitmap = boardBitmap;
	    }
	}

	public Point getCartCoord(int radius, double angDeg)
	{
		double angRad = Math.toRadians(angDeg);
		int x = (int) ( width / 2 + radius * Math.cos(angRad) );
		int y = (int) ( height / 2 - radius * Math.sin(angRad) );
		return new Point(x, y);
	}

	public List getPointList(int territoryCount, int radius, double startAngDeg, double endAngDeg)
	{
		double angEvenDeg = startAngDeg;
		double angOddDeg = startAngDeg;
		int listSize = territoryCount * 2 - 1;
		ArrayList list = new ArrayList();

		for (int i = 0; i < listSize; i++)
		{
			if ( (i % 2) == 0 )
			{
				list.add( getCartCoord(radius, angEvenDeg) );
				angEvenDeg += ( (endAngDeg - startAngDeg) / (territoryCount - 1) );
			}
			else
			{
				angOddDeg += ( (endAngDeg - startAngDeg) / territoryCount );
				list.add( getCartCoord(radius, angOddDeg) );
			}
		}

		return list;
	}

	public int getStepExtension()
	{
		return (int) (getMinExtension() * getMinExtension() / 40000);
	}

	public int getMinExtension()
	{
		return ( width < height ) ? width : height;
	}

	public int getTerritoryRadius()
	{
		return (int) (getMinExtension() * 0.08) + 1;
	}

	public List newTerritoryList()
	{
		int number = 9;
		int type = Territory.STANDARD;
		Territory territory = null;
		int color;
		ArrayList territoryList = new ArrayList();

		for (int i = 0; i < 4; i++)
		{
			color = Color.rgb(0,  0, 0);
			territory = new Territory(i + 1, i, Territory.DARKTOWER, color);
			territoryList.add(territory);
		}

		for (int i = 0; i < 4; i++)
		{
			color = Color.rgb(250, 190, 110);
			territory = new Territory(i + 5, i, Territory.FRONTIER, color);
			territoryList.add(territory);
		}

		if(originalBoard) {
		    int player = 0;
		    for(int i=8; i < OrigTerritories.terr.length; i++) {
		        if(i == 30 || i == 60 || i == 90) {
		            player++;
		        }
                type = Territory.STANDARD;
                color = Color.rgb(0, (int) (100 + Math.random() * 30), 0);
//                 if ( Util.contains(Territory.SANCTUARYLIST, number) )
//                  type = Territory.SANCTUARY;
//                 else if ( Util.contains(Territory.TOMBLIST, number) )
//                  type = Territory.TOMB;
//                 else if ( Util.contains(Territory.RUINLIST, number) )
//                  type = Territory.RUIN;
//                 else if ( Util.contains(Territory.BAZAARLIST, number) )
//                  type = Territory.BAZAAR;
//                 else if ( Util.contains(Territory.CITADELLIST, number) )
//                  type = Territory.CITADEL;
                territory = new Territory(i + 1, player, type, color);
                territoryList.add(territory);
		    }
		}
		else {
    		for (int i = 0; i < 4; i++)
    		{
    			for (int j = 0; j < 5; j++)
    			{
    				for (int k = 0; k < 4 + j; k++)
    				{
    					type = Territory.STANDARD;
    					color = Color.rgb(0, (int) (100 + Math.random() * 30), 0);
    					// if ( Util.contains(Territory.SANCTUARYLIST, number) )
    					// 	type = Territory.SANCTUARY;
    					// else if ( Util.contains(Territory.TOMBLIST, number) )
    					// 	type = Territory.TOMB;
    					// else if ( Util.contains(Territory.RUINLIST, number) )
    					// 	type = Territory.RUIN;
    					// else if ( Util.contains(Territory.BAZAARLIST, number) )
    					// 	type = Territory.BAZAAR;
    					// else if ( Util.contains(Territory.CITADELLIST, number) )
    					// 	type = Territory.CITADEL;
    
    					territory = new Territory(number, i, type, color);
    					territoryList.add(territory);
    					number += 1;
    				}
    			}
    		}
		}
		return territoryList;
	}

	public Polygon getTerritoryPolygon(int territoryNo, List abovePointList, 
		List belowPointList)
	{
		int territoryCount = (abovePointList.size() + 1) / 2;
		int aboveIndex = territoryNo;
		int belowIndex = territoryNo * 2;

		if ( territoryNo > 1 )
			aboveIndex = territoryNo * 2 - 1;

		Point point = null;
		Polygon polygon = new Polygon();
		point = (Point) abovePointList.get(aboveIndex);
		polygon.addPoint(point.x, point.y);
		point = (Point) abovePointList.get(aboveIndex + 1);
		polygon.addPoint(point.x, point.y);
		if ( ( territoryNo > 0 ) && ( territoryNo < territoryCount - 1) )
		{
			point = (Point) abovePointList.get(aboveIndex + 2);
			polygon.addPoint(point.x, point.y);
		}

		point = (Point) belowPointList.get(belowIndex + 2);
		polygon.addPoint(point.x, point.y);
		point = (Point) belowPointList.get(belowIndex + 1);
		polygon.addPoint(point.x, point.y);
		point = (Point) belowPointList.get(belowIndex);
		polygon.addPoint(point.x, point.y);

		return polygon;
	}

	public Polygon getFrontierPolygon(int territoryCount, int radius, double startAngDeg)
	{
		int territoryHeight = getTerritoryRadius();
		double frontierAngDeg = 10.0;
		Point point = null;
		Polygon polygon = new Polygon();

		for (int i = 0; i < territoryCount + 1; i++)
		{
			point = getCartCoord(radius + (i * territoryHeight), startAngDeg + frontierAngDeg);
			polygon.addPoint(point.x, point.y);
		}
		for (int i = territoryCount; i >= 0; i--)
		{
			point = getCartCoord(radius + (i * territoryHeight), startAngDeg);
			polygon.addPoint(point.x, point.y);
		}

		return polygon;
	}

	public Polygon getDarkTowerPolygon(int territoryCount, int radius,
		double startAngDeg)
	{
		List belowPointList = getPointList(territoryCount + 1, radius,
			startAngDeg + 5, startAngDeg + 90 - 5);
		List extraPointList = getPointList(territoryCount + 1, radius,
			startAngDeg, startAngDeg + 90);
		Point point = null;
		Polygon polygon = new Polygon();

		point = getCartCoord(0, 0.0);
		polygon.addPoint(point.x, point.y);
		point = (Point) extraPointList.get(extraPointList.size() - 1);
		polygon.addPoint(point.x, point.y);
		for (int i = belowPointList.size() - 1; i >= 0; i--)
		{
			point = (Point) belowPointList.get(i);
			polygon.addPoint(point.x, point.y);
		}
		point = (Point) extraPointList.get(0);
		polygon.addPoint(point.x, point.y);

		return polygon;
	}

	public List getTerritoryList()
	{
		return territoryList;
	}

	public void setPlayerList(List playerList)
	{
		this.playerList = playerList;
	}

	public void setPlayerNo(int playerNo)
	{
		this.playerNo = getPlayerNo(playerNo);
	}

	public int getPlayerNo()
	{
		return playerNo;
	}

	public int getPlayerNo(int playerNo)
	{
		if ( playerNo < 0 )
			return getPlayerNo(playerNo + playerList.size());
		if ( playerNo >= playerList.size() )
			return getPlayerNo(playerNo - playerList.size());
		Player player = (Player) playerList.get(playerNo);
		if ( !player.isEnable() )
			return getPlayerNo(playerNo + 1);
		return playerNo;
	}

	public void setHighlightedTerritoryNo(int territoryNo)
	{
		highlightedTerritoryNo = territoryNo;
	}

	public void setDragon(Dragon dragon)
	{
		this.dragon = dragon;
	}

	public Point nextCoord(Figure figure)
	{
		Point point = null;
		Territory territory = null;
		
		int step = figure.getStep();
		territory = (Territory) territoryList.get(figure.getStartTerritoryNo() - 1);
		int x1 = territory.getCentre().x;
		int y1 = territory.getCentre().y;
		territory = (Territory) territoryList.get(figure.getEndTerritoryNo() - 1);
		int x2 = territory.getCentre().x;
		int y2 = territory.getCentre().y;
		point = new Point(x2, y2);
		
		if ( figure.getStartTerritoryNo() != figure.getEndTerritoryNo() )
		{
			int dx = Math.abs(x2 - x1);
			int dy = Math.abs(y2 - y1);
			int incx = (x1 > x2) ? -1 : 1;
			int incy = (y1 > y2) ? -1 : 1;

			if (dx > dy)
			{
				int e = dx / 2;
				while (Math.abs(x2 - x1) > 0)
				{
					x1 += incx;
					e += dy;
					if (e >= dx)
					{
						y1 += incy;
						e -= dx;
					}
					if ( step == 0 )
					{
						point = new Point(x1, y1);
						break;
					}
					step--;
				}
			}
			else
			{
				int e = dy / 2;
				while (Math.abs(y2 - y1) > 0)
				{
					y1 += incy;
					e += dx;
					if (e >= dy)
					{
						x1 += incx;
						e -= dy;
					}
					if ( step == 0 )
					{
						point = new Point(x1, y1);
						break;
					}
					step--;
				}
			}

			figure.setStep(figure.getStep() + getStepExtension());
			if ( step > 0 )
			{
				figure.setStep(0);
				figure.setStartTerritoryNo(figure.getEndTerritoryNo());
			}
		}
		
		return point;
	}

	public void deepFirstSearch(Search search, int fromTerritoryNo, int toTerritoryNo)
	{
		Player player = (Player) playerList.get(playerNo);
		Territory fromTerritory = (Territory) territoryList.get(fromTerritoryNo - 1);
		Territory toTerritory = (Territory) territoryList.get(toTerritoryNo - 1);
		Territory neigborTerritory = null;
		List neigborList = fromTerritory.getNeighborList();
		int neigborTerritoryNo = 0;
		int dragonTerritoryNo = 0;

		if ( dragon != null )
			dragonTerritoryNo = dragon.getEndTerritoryNo();

		if ( fromTerritoryNo != toTerritoryNo )
		{
			search.setDistance(Integer.MAX_VALUE);
			for (int i = 0; i < neigborList.size(); i++)
			{
				neigborTerritoryNo = ((Integer) neigborList.get(i)).intValue();
				neigborTerritory = (Territory) territoryList.get(neigborTerritoryNo - 1);
				if ( ( neigborTerritoryNo != dragonTerritoryNo ) &&
					 ( ( neigborTerritory.getType() != Territory.FRONTIER ) ||
					   ( toTerritory.getType() == Territory.FRONTIER ) ) &&
					 ( ( neigborTerritory.getType() != Territory.DARKTOWER ) ||
					   ( toTerritory.getType() == Territory.DARKTOWER ) ) &&
					 ( ( neigborTerritory.getType() != Territory.CITADEL ) ||
					   ( player.getRelKingdomNo() == 0 ) ) &&
					 ( ( neigborTerritory.getType() == Territory.STANDARD ) ||
					   ( neigborTerritoryNo == toTerritoryNo ) ) )
				{
					int dist = search(neigborTerritoryNo, toTerritoryNo, 0);
					if ( ( dist < search.getDistance() ) ||
						 ( dist == search.getDistance() ) && ( Math.random() > 0.5 ) )
					{
						search.setTerritoryNo(neigborTerritoryNo);
						search.setDistance(dist);
					}
				}
			}
		}
		else
		{
			search.setTerritoryNo(toTerritoryNo);
			search.setDistance(0);
		}
	}

	public int search(int fromTerritoryNo, int toTerritoryNo, int deep)
	{
		Player player = (Player) playerList.get(playerNo);
		Territory fromTerritory = (Territory) territoryList.get(fromTerritoryNo - 1);
		Territory toTerritory = (Territory) territoryList.get(toTerritoryNo - 1);
		Territory neigborTerritory = null;
		List neigborList = fromTerritory.getNeighborList();
		int neigborTerritoryNo = 0;
		int dragonTerritoryNo = 0;

		if ( dragon != null )
			dragonTerritoryNo = dragon.getEndTerritoryNo();

		if ( ( deep > 3 ) || ( fromTerritoryNo == toTerritoryNo ) )
			return getDistance(fromTerritoryNo, toTerritoryNo) + deep;

		int minDist = Integer.MAX_VALUE;
		for (int i = 0; i < neigborList.size(); i++)
		{
			neigborTerritoryNo = ((Integer) neigborList.get(i)).intValue();
			neigborTerritory = (Territory) territoryList.get(neigborTerritoryNo - 1);
			if ( ( neigborTerritoryNo != dragonTerritoryNo ) &&
				 ( ( neigborTerritory.getType() != Territory.FRONTIER ) ||
				   ( toTerritory.getType() == Territory.FRONTIER ) ) &&
				 ( ( neigborTerritory.getType() != Territory.DARKTOWER ) ||
				   ( toTerritory.getType() == Territory.DARKTOWER ) ) &&
				 ( ( neigborTerritory.getType() != Territory.CITADEL ) ||
				   ( player.getRelKingdomNo() == 0 ) ) &&
				 ( ( neigborTerritory.getType() == Territory.STANDARD ) ||
				   ( neigborTerritoryNo == toTerritoryNo ) ) )
			{
				int dist = search(neigborTerritoryNo, toTerritoryNo, deep + 1);
				if ( dist < minDist)
					minDist = dist;
			}
		}
		return minDist + deep;
	}

	public int getDistance(int fromTerritoryNo, int toTerritoryNo)
	{
		Territory fromTerritory = (Territory) territoryList.get(fromTerritoryNo - 1);
		Territory toTerritory = (Territory) territoryList.get(toTerritoryNo - 1);
		Point fromCentre = fromTerritory.getCentre();
		Point toCentre = toTerritory.getCentre();
		int dx = toCentre.x - fromCentre.x;
		int dy = toCentre.y - fromCentre.y;
		return (int) Math.sqrt(dx * dx + dy * dy);
	}
	
	public void drawDragon(Canvas canvas, Point point) {
	   if(!originalBoard) {
	      MultiImage.drawSubImage(canvas, dragonImageIcon, MultiImage.DRAGON, 28, point.x, point.y);
	   }
	   else {
         int offsetX = -dragonBitmap.getWidth() / 2;
         int offsetY = -dragonBitmap.getHeight() / 2;
         canvas.drawBitmap(dragonBitmap, point.x + offsetX, point.y + offsetY, bitmapP);
	   }
	}

   public void drawPlayer(Canvas canvas, Point point, int player, DrawType drawType) {
      if(!originalBoard) {
         MultiImage.drawSubImage(canvas, classmImageIcon, MultiImage.CLASSM, MultiImage.PLAYER[player], point.x, point.y);
      }
      else {
         Bitmap bitmap = playerBitmaps[player][drawType.index]; 
         int offsetX = -bitmap.getWidth() / 2;
         int offsetY = -bitmap.getHeight();
         canvas.drawBitmap(bitmap, point.x + offsetX, point.y + offsetY, bitmapP);
      }
   }
}
