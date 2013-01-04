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
import android.view.View.OnTouchListener;

public class ButtonPanelView extends View implements OnTouchListener {
   private Bitmap bitmap = null;
   Paint imageP;
   Paint darkenP;

   ActivityGame activity;
   
   int highlightX = -1;
   int highlightY = -1;
   
   int offsetX;
   int offsetY;
   
   int startFudge = 5;
   int endFudge = 8;

   public ButtonPanelView(Context context, AttributeSet attributes) {
      super(context, attributes);
      activity = (ActivityGame) context;

      imageP = new Paint();
      imageP.setAntiAlias(true);
      imageP.setFilterBitmap(true);
      imageP.setDither(true);
      imageP.setARGB(255, 0, 0, 0);

      darkenP = new Paint();
      darkenP.setAntiAlias(true);
      darkenP.setFilterBitmap(true);
      darkenP.setDither(true);      
      darkenP.setARGB(128, 0, 0, 0);
      
      bitmap = BitmapFactory.decodeResource(activity.getResources(), R.drawable.panel);
      setOnTouchListener(this);
   }

   @Override
   protected void onDraw(Canvas canvas) {
       if(offsetX == 0) {
           offsetX = (getWidth() - bitmap.getWidth()) / 2;
           offsetY = 5; //(getWidth() - bitmap.getWidth()) / 2;
       }
       
      canvas.drawBitmap(bitmap, offsetX, offsetY, imageP);

      if(highlightX != -1 && highlightY != -1) {
         int startX = offsetX + startFudge;
         int startY = offsetY + startFudge;
         int endX = startX + bitmap.getWidth() - endFudge;
         int endY = startY + bitmap.getHeight() - endFudge;
         int incX = (endX - startX) / 3;
         int incY = (endY - startY) / 4;
         
         int left = highlightX * incX + startX;
         int top = highlightY * incY + startY;
         int right = left + incX;
         int bottom = top + incY;
         
         // 1-off hacks
         if(highlightY == 3) {
             top--;
         }
         else if(highlightY == 2) {
             bottom--;
         }

         canvas.drawRect(left, top, right, bottom, darkenP);
      }
      
      //***

//      Paint p = new Paint();
//      p.setARGB(255, 0, 255, 0);
//      int startX = offsetX + startFudge;
//      int startY = offsetY + startFudge;
//      int endX = startX + bitmap.getWidth() - endFudge;
//      int endY = startY + bitmap.getHeight() - endFudge;
//      int incX = (endX - startX) / 3;
//      int incY = (endY - startY) / 4;
//      
//      for(int x=startX; x <= endX; x += incX) {
//         canvas.drawLine(x, startY, x, endY, p);
//      }
//      
//      for(int y=startY; y <= endY; y += incY) {
//         canvas.drawLine(startX, y, endX, y, p);
//      }    
      
      //***
   }

   @Override
   public boolean onTouch(View v, MotionEvent event) {
      if(event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
         int startX = offsetX + startFudge;
         int startY = offsetY + startFudge;
         int endX = startX + bitmap.getWidth() - endFudge;
         int endY = startY + bitmap.getHeight() - endFudge;
         int incX = (endX - startX) / 3;
         int incY = (endY - startY) / 4;
   
         int buttonX = 0;
         int buttonY = 0;
         boolean found = false;
         int touchX = (int) event.getX();
         int touchY = (int) event.getY();
         
         for(int y=startY; y <= endY; y += incY) {
             buttonX = 0;
            for(int x=startX; x <= endX; x += incX) {
               if(touchX >= x && touchX < x + incX && touchY >= y && touchY < y + incY) {
                  found = true;
                  break;
               }
               buttonX++;
            }         
            if(found) {
               break;
            }
            buttonY++;
         }
         
         if(found) {
            if(event.getAction() == MotionEvent.ACTION_UP) {
               highlightX = -1;
               highlightY = -1;
               buttonPressed(buttonX, buttonY);
               postInvalidate();
               return true;
            }
            if(event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
               highlightX = buttonX;
               highlightY = buttonY;
               postInvalidate();
               return true;
            }            
         }
      }
      
      if(event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL || event.getAction() == MotionEvent.ACTION_OUTSIDE) {
         highlightX = -1;
         highlightY = -1;
         postInvalidate();
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

   @Override
   protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
      setMeasuredDimension(260, 240);
   }
}