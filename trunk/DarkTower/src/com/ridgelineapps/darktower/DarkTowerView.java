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
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class DarkTowerView extends View {
   private String label = null;
   private Bitmap bitmap = null;
   private boolean flash = false;
   private int flashInterval = 0;
   private FlashThread flashThread = null;
   private boolean enabled = false;
   Paint backgroundP;
   Paint textP;
   Paint imageP;

   DarkTowerActivity context;

   public DarkTowerView(Context context, AttributeSet attributes) {
      this((DarkTowerActivity) context);
      textP = new Paint();
      textP.setTextSize(20);
      textP.setFakeBoldText(true);
      textP.setAntiAlias(true);
      // TODO Don't use pure red, use more pleasing colors...
      textP.setARGB(255, 255, 0, 0);

      imageP = new Paint();
      imageP.setAntiAlias(true);
      // TODO set this for all images
      imageP.setFilterBitmap(true);
      imageP.setDither(true);
      imageP.setARGB(255, 0, 0, 0);

      backgroundP = new Paint();
      backgroundP.setARGB(255, 0, 0, 0);
   }

   public DarkTowerView(DarkTowerActivity context) {
      super(context);
      this.context = context;

      label = "1";
      // TODO (?)
      // image = Image.getImageIcon(Image.BLACK);
      
      flashThread = new FlashThread();
   }
   
   @Override
   protected void onVisibilityChanged(View changedView, int visibility) {
      super.onVisibilityChanged(changedView, visibility);
      if(visibility == View.VISIBLE) {
         if(flashThread == null) {
            flashThread = new FlashThread();
         }
      }
      else if(flashThread != null) {
         flashThread.kill = true;
         flashThread = null;
      }
   }

   public void setColor(int color) {
      textP.setColor(color);
   }

   public void setLabel(String label) {
      this.label = label;
      invalidate();
   }

   public void setBitmap(Bitmap bitmap) {
      this.bitmap = bitmap;
      invalidate();
   }

   public void setFlash(boolean flash) {
      this.flash = flash;
   }

   public void setEnabled(boolean enabled) {
      this.enabled = enabled;
   }

   @Override
   protected void onDraw(Canvas canvas) {
      int width = canvas.getWidth();
      int height = canvas.getHeight();
      float dx = width / 2;
      float dy = height / 2;
      canvas.translate(dx, dy);

      if (flash)
         flashInterval++;
      else
         flashInterval = 0;

      canvas.drawRect(0, 0, width, height, backgroundP);
      if (flashInterval % 2 == 0) {
         // g.setFont(font);
         float textWidth = textP.measureText(label);
         int labelx = (width - (int) textWidth) / 2;
         int labely = 20;
         canvas.drawText(label, labelx, labely, textP);
      }
      if (enabled && bitmap != null) {
         int imagex = (getWidth() - bitmap.getWidth()) / 2;
         int imagey = 26;
         canvas.drawBitmap(bitmap, imagex, imagey, imageP);
      }
   }

   @Override
   protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
      setMeasuredDimension(255, 255);
   }
   
   class FlashThread extends Thread {
      boolean kill = false;
 
      public void run()
      {
         try
         {
            while (!kill)
            {
               invalidate();
               sleep(300);
            }
         }
         catch (Exception e)
         {
            e.printStackTrace();
         }
      }      
   }
}