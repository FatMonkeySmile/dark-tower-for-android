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

import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class DarkTowerView extends View {
   public static HashMap<Integer, Bitmap> imageCache = new HashMap<Integer, Bitmap>();
   private String label = null;
   private Bitmap bitmap = null;
   private boolean flash = false;
   private int flashInterval = 0;
   private FlashThread flashThread = null;
   private boolean enabled = false;
   Paint backgroundP;
   Paint textP;
   Paint imageP;
   Paint darkenP;
   Paint inventoryTextP;

   ActivityGame activity;

   public DarkTowerView(Context context, AttributeSet attributes) {
      super(context, attributes);
      activity = (ActivityGame) context;

      textP = new Paint();
      textP.setTextSize(32);
      textP.setFakeBoldText(true);
      textP.setAntiAlias(true);
      // TODO Don't use pure red, use more pleasing colors (change in Territory too)
      textP.setColor(Color.RED);

      imageP = new Paint();
      imageP.setAntiAlias(true);
      // TODO set this for all images
      imageP.setFilterBitmap(true);
      imageP.setDither(true);
      imageP.setARGB(255, 0, 0, 0);

      backgroundP = new Paint();
      backgroundP.setARGB(255, 0, 0, 0);

      darkenP = new Paint();
      darkenP.setAntiAlias(true);
      darkenP.setFilterBitmap(true);
      darkenP.setDither(true);
      darkenP.setARGB(125, 0, 0, 0);
      
      inventoryTextP = new Paint();
      inventoryTextP.setTextSize(12);
      inventoryTextP.setFakeBoldText(true);
      inventoryTextP.setAntiAlias(true);
      
      label = "1";
      // TODO (?)
      // image = Image.getImageIcon(Image.BLACK);

      //TODO Only run thread when flashing
      startThread();
      
      // TODO stop thread when app is paused
   }

   @Override
   protected void onVisibilityChanged(View changedView, int visibility) {
      super.onVisibilityChanged(changedView, visibility);
      if (visibility == View.VISIBLE) {
         if (flashThread == null) {
            startThread();
         }
      } else {
         stopThread();
      }
   }

   public void setColor(int color) {
      textP.setColor(color);
   }

   public void setLabel(String label) {
      this.label = label;
      postInvalidate();
   }

   public void setBitmap(Bitmap bitmap) {
      this.bitmap = bitmap;
      postInvalidate();
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

      if (flash)
         flashInterval++;
      else
         flashInterval = 0;

      canvas.drawRect(0, 0, width, height, backgroundP);
      if (flashInterval % 2 == 0) {
         // g.setFont(font);
         float textWidth = textP.measureText(label);
         int labelx = 120; //(width - (int) textWidth) / 2;
         int labely = 215;
         canvas.drawText(label, labelx, labely, textP);
      }
      if (enabled && bitmap != null) {
         int imagex = (getWidth() - bitmap.getWidth()) / 2;
         int imagey = 26;
         Rect src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
         Rect dest = new Rect(imagex, imagey, imagex + (int) (bitmap.getWidth() * 1.13), imagey + (int) (bitmap.getHeight() * 1.13));
         canvas.drawBitmap(bitmap, src, dest, imageP);
      }
      
      int x = 250;
      int y = 20;
      for(int i=0; i < 4; i++) {
         Player player = (Player) activity.darkTower.getPlayerList().get(i);
         //TODO color the same as player...
         int color = Color.WHITE;
         drawInventory(canvas, x, y, player, color);
         y += 100;
      }
   }
   
   protected void drawInventory(Canvas canvas, int x, int y, Player player, int color) {
      inventoryTextP.setColor(color);
      canvas.drawText("Player " + player.getPlayerNo(), x, y, inventoryTextP);
      x += 2;
      y += 12;
      canvas.drawText("Gold: " + player.getGold(), x, y, inventoryTextP);
      y += 12;
      canvas.drawText("Warriors: " + player.getWarriors(), x, y, inventoryTextP);
      y += 12;
      canvas.drawText("Food: " + player.getFood(), x, y, inventoryTextP);
      y += 12;
      Bitmap bitmap;
      Rect src;
      Rect dest;
      int sizex = 25;
      int sizey = 18;
      
      //TODO: Create a single, sized image for this and just do the darken...
      bitmap = getImage(activity, R.drawable.beast);
      src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
      dest = new Rect(x, y, x + sizex, y + sizey);
      canvas.drawBitmap(bitmap, src, dest, imageP);
      //TODO: can we just use darkenP for the image?
      if(!player.hasBeast()) {
         canvas.drawRect(dest, darkenP);
      }
      
      x += sizex;
      bitmap = getImage(activity, R.drawable.scout);
      src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
      dest = new Rect(x, y, x + sizex, y + sizey);
      canvas.drawBitmap(bitmap, src, dest, imageP);
      //TODO: can we just use darkenP for the image?
      if(!player.hasScout()) {
         canvas.drawRect(dest, darkenP);
      }
      
      x += sizex;
      bitmap = getImage(activity, R.drawable.healer);
      src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
      dest = new Rect(x, y, x + sizex, y + sizey);
      canvas.drawBitmap(bitmap, src, dest, imageP);
      //TODO: can we just use darkenP for the image?
      if(!player.hasHealer()) {
         canvas.drawRect(dest, darkenP);
      }

      x += sizex;
      bitmap = getImage(activity, R.drawable.sword);
      src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
      dest = new Rect(x, y, x + sizex, y + sizey);
      canvas.drawBitmap(bitmap, src, dest, imageP);
      //TODO: can we just use darkenP for the image?
      if(!player.hasDragonSword()) {
         canvas.drawRect(dest, darkenP);
      }
      
      y += sizey;
      x -= sizex * 3;
      bitmap = getImage(activity, R.drawable.brasskey);
      src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
      dest = new Rect(x, y, x + sizex, y + sizey);
      canvas.drawBitmap(bitmap, src, dest, imageP);
      //TODO: can we just use darkenP for the image?
      if(!player.hasBrassKey()) {
         canvas.drawRect(dest, darkenP);
      }

      x += sizex;
      bitmap = getImage(activity, R.drawable.silverkey);
      src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
      dest = new Rect(x, y, x + sizex, y + sizey);
      canvas.drawBitmap(bitmap, src, dest, imageP);
      //TODO: can we just use darkenP for the image?
      if(!player.hasSilverKey()) {
         canvas.drawRect(dest, darkenP);
      }

      x += sizex;
      bitmap = getImage(activity, R.drawable.goldkey);
      src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
      dest = new Rect(x, y, x + sizex, y + sizey);
      canvas.drawBitmap(bitmap, src, dest, imageP);
      //TODO: can we just use darkenP for the image?
      if(!player.hasGoldKey()) {
         canvas.drawRect(dest, darkenP);
      }

      x += sizex;
      bitmap = getImage(activity, R.drawable.pegasus);
      src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
      dest = new Rect(x, y, x + sizex, y + sizey);
      canvas.drawBitmap(bitmap, src, dest, imageP);
      //TODO: can we just use darkenP for the image?
      if(!player.hasPegasus()) {
         canvas.drawRect(dest, darkenP);
      }      
   }

   @Override
   protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
      setMeasuredDimension(260, 260);
   }

   public void startThread() {
      stopThread();
      flashThread = new FlashThread();
      flashThread.start();
   }

   public void stopThread() {
      if (flashThread != null) {
         synchronized (flashThread) {
            flashThread.kill = true;
            flashThread = null;
         }
      }
   }

   class FlashThread extends Thread {
      boolean kill = false;

      public void run() {
         try {
            while (!kill) {
               postInvalidate();
               sleep(300);
            }
         } catch (Exception e) {
            e.printStackTrace();
         }
      }
   }
   
   public static Bitmap getImage(Activity activity, int id) {
      synchronized(activity) {
         Bitmap bitmap = imageCache.get(id);
         if(bitmap == null) {
            bitmap = BitmapFactory.decodeResource(activity.getResources(), id);
            if(bitmap != null) {
               imageCache.put(id, bitmap);
            }
         }
         return bitmap;
      }
   }
}