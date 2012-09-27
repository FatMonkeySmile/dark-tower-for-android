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

public class InventoryView extends View {
   Paint backgroundP;
   Paint imageP;
   Paint darkenP;
   Paint inventoryTextP;

   ActivityGame activity;
   int lineY;

   public InventoryView(Context context, AttributeSet attributes) {
      super(context, attributes);
      activity = (ActivityGame) context;

      imageP = new Paint();
//      imageP.setAntiAlias(true);
      imageP.setFilterBitmap(true);
      imageP.setDither(true);
      imageP.setARGB(255, 0, 0, 0);

      backgroundP = new Paint();
      backgroundP.setARGB(255, 0, 0, 0);

      darkenP = new Paint();
//      darkenP.setAntiAlias(true);
//      darkenP.setFilterBitmap(true);
//      darkenP.setDither(true);
      darkenP.setARGB(175, 0, 0, 0);
      
      inventoryTextP = new Paint();
      inventoryTextP.setTextSize(18);
      inventoryTextP.setFakeBoldText(true);
      inventoryTextP.setAntiAlias(true);
   }

   @Override
   protected void onDraw(Canvas canvas) {
      int x = 11;
      int y = 20;
      for(int i=0; i < 4; i++) {
         Player player = (Player) activity.darkTower.getPlayerList().get(i);
         y = drawInventory(canvas, x, y, player, Territory.COLORLIST[player.getPlayerNo()]);
         y += 40;
      }
   }
   
   protected int drawInventory(Canvas canvas, int x, int y, Player player, int color) {
      boolean draw = player.isEnable();
      Rect rect = new Rect();
      if(lineY == 0) {
          inventoryTextP.getTextBounds("Player", 0, 5, rect);
          lineY = -rect.top + rect.bottom + 2;
      }
      if(draw) {
          inventoryTextP.setColor(color);
          canvas.drawText("Player " + (player.getPlayerNo() + 1), x, y, inventoryTextP);
          inventoryTextP.setColor(Color.LTGRAY);      
      }
      y += lineY;
      if(draw) {
          canvas.drawText("Warriors-" + player.getWarriors() + "  Food-" + player.getFood() + "  Gold-" + player.getGold(), x, y, inventoryTextP);
      }
//    canvas.drawText("Gold: " + player.getGold(), x, y, inventoryTextP);
//    y += lineY;
//    canvas.drawText("Warriors: " + player.getWarriors(), x, y, inventoryTextP);
//    y += lineY;
//    canvas.drawText("Food: " + player.getFood(), x, y, inventoryTextP);
      
//      canvas.drawText("Player " + (player.getPlayerNo() + 1), x, y, inventoryTextP);
//      y += lineY;
//      canvas.drawText("Gold: " + player.getGold(), x, y, inventoryTextP);
//      y += lineY;
//      canvas.drawText("Warriors: " + player.getWarriors(), x, y, inventoryTextP);
//      y += lineY;
//      canvas.drawText("Food: " + player.getFood(), x, y, inventoryTextP);
      y += lineY * 0.5;
      Bitmap bitmap;
      Rect dest;
      int sizex = 60;
      int sizey = 42;
      x += 3;
      
      //TODO: Create a single, sized image for this and just do the darken...
      if(draw) {      
          bitmap = DarkTowerView.getImage(activity, R.drawable.beast);
          dest = new Rect(x, y, x + sizex, y + sizey);
          canvas.drawBitmap(bitmap, null, dest, imageP);
          if(!player.hasBeast()) {
             canvas.drawRect(dest, darkenP);
          }
          
          x += sizex;
          bitmap = DarkTowerView.getImage(activity, R.drawable.scout);
          dest = new Rect(x, y, x + sizex, y + sizey);
          canvas.drawBitmap(bitmap, null, dest, imageP);
          if(!player.hasScout()) {
             canvas.drawRect(dest, darkenP);
          }
          
          x += sizex;
          bitmap = DarkTowerView.getImage(activity, R.drawable.healer);
          dest = new Rect(x, y, x + sizex, y + sizey);
          canvas.drawBitmap(bitmap, null, dest, imageP);
          if(!player.hasHealer()) {
             canvas.drawRect(dest, darkenP);
          }
    
          x += sizex;
          bitmap = DarkTowerView.getImage(activity, R.drawable.sword);
          dest = new Rect(x, y, x + sizex, y + sizey);
          canvas.drawBitmap(bitmap, null, dest, imageP);
          if(!player.hasDragonSword()) {
             canvas.drawRect(dest, darkenP);
          }
      }
      
      y += sizey;
      if(draw) {
          x -= sizex * 3;
          bitmap = DarkTowerView.getImage(activity, R.drawable.pegasus);
          dest = new Rect(x, y, x + sizex, y + sizey);
          canvas.drawBitmap(bitmap, null, dest, imageP);
          //TODO: can we just use darkenP for the image?
          if(!player.hasPegasus()) {
             canvas.drawRect(dest, darkenP);
          }      
          
          x += sizex;
          bitmap = DarkTowerView.getImage(activity, R.drawable.brasskey);
          dest = new Rect(x, y, x + sizex, y + sizey);
          canvas.drawBitmap(bitmap, null, dest, imageP);
          //TODO: can we just use darkenP for the image?
          if(!player.hasBrassKey()) {
             canvas.drawRect(dest, darkenP);
          }
    
          x += sizex;
          bitmap = DarkTowerView.getImage(activity, R.drawable.silverkey);
          dest = new Rect(x, y, x + sizex, y + sizey);
          canvas.drawBitmap(bitmap, null, dest, imageP);
          //TODO: can we just use darkenP for the image?
          if(!player.hasSilverKey()) {
             canvas.drawRect(dest, darkenP);
          }
    
          x += sizex;
          bitmap = DarkTowerView.getImage(activity, R.drawable.goldkey);
          dest = new Rect(x, y, x + sizex, y + sizey);
          canvas.drawBitmap(bitmap, null, dest, imageP);
          //TODO: can we just use darkenP for the image?
          if(!player.hasGoldKey()) {
             canvas.drawRect(dest, darkenP);
          }
      }

      y += sizey;
      return y;
   }

//   @Override
//   protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//      setMeasuredDimension(260, 260);
//   }
}