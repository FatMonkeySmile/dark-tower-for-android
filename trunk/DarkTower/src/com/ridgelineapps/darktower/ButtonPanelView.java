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
 * DarkTowerPanel.java
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

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class ButtonPanelView extends View {
   private Bitmap bitmap = null;
   Paint imageP;
   Paint darkenP;

   ActivityGame activity;

   public ButtonPanelView(Context context, AttributeSet attributes) {
      super(context, attributes);
      activity = (ActivityGame) context;

      imageP = new Paint();
      imageP.setAntiAlias(true);
      imageP.setFilterBitmap(true);
      imageP.setDither(true);
      imageP.setARGB(255, 0, 0, 0);

      darkenP = new Paint();
      darkenP.setARGB(128, 0, 0, 0);
      
      bitmap = BitmapFactory.decodeResource(activity.getResources(), R.drawable.panel);
   }

   @Override
   protected void onDraw(Canvas canvas) {
      canvas.drawBitmap(bitmap,  0, 0, imageP);
      
      //***

      Paint p = new Paint();
      p.setARGB(255, 0, 255, 0);
      int startX = 0;
      int startY = 0;
      int endX = bitmap.getWidth();
      int endY = bitmap.getHeight();
      int incX = endX - startX / 3;
      int incY = endY - startY / 4;
      
      for(int x=startX; x <= endX; x += incX) {
         canvas.drawLine(x, 0, x, bitmap.getHeight(), p);
      }
      
      for(int y=startY; y <= endY; y += incY) {
         canvas.drawLine(0, y, bitmap.getWidth(), y, p);
      }         

      
      //***
   }

   @Override
   public boolean onTouchEvent(MotionEvent event) {
      int startX = 0;
      int startY = 0;
      int endX = bitmap.getWidth();
      int endY = bitmap.getHeight();
      int incX = endX - startX / 3;
      int incY = endY - startY / 4;

      int buttonX = 0;
      int buttonY = 0;
      boolean found = false;
      int touchX = (int) event.getX();
      int touchY = (int) event.getY();
      
      for(int y=startY; y <= endY; y += incY) {
         for(int x=startX; x <= endX; x += incX) {
            if(touchX >= x && touchX < x + incX && touchY >= y && touchY < y + incY) {
               found = true;
               break;
            }
            buttonY++;
         }         
         if(found) {
            break;
         }
         buttonX++;
      }
      
      if(found) {
         buttonPressed(buttonX, buttonY);
         return true;
      }
      
      return super.onTouchEvent(event);
   }
   
   void buttonPressed(int x, int y) {
      if(x == 0 && y == 0) {
         activity.yesBuyButton(null);
      }
      else if(x == 1 && y == 0) {
         activity.repeatButton(null);
      }
      else if(x == 2 && y == 0) {
         activity.noEndButton(null);
      }
      else if(x == 0 && y == 1) {
         activity.haggleButton(null);
      }
      else if(x == 1 && y == 1) {
         activity.bazaarButton(null);
      }
      else if(x == 2 && y == 1) {
         activity.clearButton(null);
      }
      else if(x == 0 && y == 2) {
         activity.ruinButton(null);
      }
      else if(x == 1 && y == 2) {
         activity.moveButton(null);
      }
      else if(x == 2 && y == 2) {
         activity.sanctuaryButton(null);
      }
      else if(x == 0 && y == 3) {
         activity.darktowerButton(null);
      }
      else if(x == 1 && y == 3) {
         activity.frontierButton(null);
      }
      else if(x == 2 && y == 3) {
         activity.inventoryButton(null);
      }      
   }
   
//   @Override
//   protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//      setMeasuredDimension(260, 240);
//   }
}