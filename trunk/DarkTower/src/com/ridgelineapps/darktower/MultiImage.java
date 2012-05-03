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
 * MultiImage.java
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

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class MultiImage {
   public static final int NA = 0;
   public static final int CLASSM = 1;
   public static final int DRAGON = 2;
   public static final int ISO = 3;

//   public static final String[] IMAGE = { "dg_classm32", "dg_dragon32", "dg_iso32" };
   public static final int[][] DIMENSION = { { 32, 32 }, { 32, 32 }, { 54, 49 } };
   public static final int[][] INDEX = { { 16, 17, 18 }, { 0, 1, 33 }, { 39, 40, 43 }, { 9, 11, 12 }, { 10, 32 }, { 13, 14, 15 } };

   public static final int DARKTOWER = 221;
   public static final int[] PLAYER = { 11, 3, 19, 35 };
   public static final int[] CITADEL = { 210, 211, 212, 213 };
   public static final int[] BAZAAR = { 182, 184, 183, 216 };
   public static final int[] SANCTUARY = { 217, 220, 219, 218 };
   public static final int[] RUIN = { 168, 168, 168, 168 };
   public static final int[] TOMB = { 166, 164, 167, 165 };

   public static Bitmap getBitmap(Resources res, int imageNo) {
      switch(imageNo) {
         case CLASSM:
            return BitmapFactory.decodeResource(res, R.drawable.dg_classm32);
         case DRAGON:
            return BitmapFactory.decodeResource(res, R.drawable.dg_dragon32);
         case ISO:
            return BitmapFactory.decodeResource(res, R.drawable.dg_iso32);
         default:
            return null;
      }
   }

   public static Bitmap getTexture(Bitmap bitmap, int imageNo, int indexNo, int dx, int dy) {
      int width = DIMENSION[imageNo - 1][0];
      int height = DIMENSION[imageNo - 1][1] - 23;

      int imagesPerRow = dx / width + 1;
      int imagesPerCol = dy / height * 2 + 2;

      int index = 0;
      double random = 0.0;

      Bitmap b = Bitmap.createBitmap(dx, dy, Config.ARGB_8888);
      Canvas c = new Canvas(b);

      for (int row = -1; row < imagesPerCol; row++) {
         for (int col = -1; col < imagesPerRow; col++) {
            random = Math.random();
            index = INDEX[indexNo][(int) (random * INDEX[indexNo].length)];
            drawTexture(c, bitmap, imageNo, index, row / 2 + col, (row - 1) / 2 - col);
         }
      }

      return b;
   }

   public static void drawTexture(Canvas c, Bitmap bitmap, int imageNo, int index, int col, int row) {
      int width = DIMENSION[imageNo - 1][0];
      int height = DIMENSION[imageNo - 1][1];

      int x = (col - row) * width / 2;
      int y = (col + row) * (height - 23) / 2 - 23;

      int imagesPerRow = bitmap.getWidth() / width;
      int srcX = (index % imagesPerRow) * width;
      int srcY = (index / imagesPerRow) * height;

      //TODO paint...
      Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
      Rect dest = new Rect(x, y, x + width, y + height);
      c.drawBitmap(bitmap, src, dest, null);
   }

   public static Bitmap getSubImage(Bitmap bitmap, int imageNo, int index) {
      int width = DIMENSION[imageNo - 1][0];
      int height = DIMENSION[imageNo - 1][1];

      int imagesPerRow = bitmap.getWidth() / width;
      int srcX = (index % imagesPerRow) * width;
      int srcY = (index / imagesPerRow) * height;

      Bitmap b = Bitmap.createBitmap(width, height, Config.ARGB_8888);
      Canvas c = new Canvas(b);

      //TODO paint...
      Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
      Rect dest = new Rect(0, 0, width, height);
      c.drawBitmap(bitmap, src, dest, null);

      return b;
   }

   public static void drawSubImage(Canvas c, Bitmap bitmap, int imageNo, int index, int x, int y) {
      int width = DIMENSION[imageNo - 1][0];
      int height = DIMENSION[imageNo - 1][1];

      int imagesPerRow = bitmap.getWidth() / width;
      int srcX = (index % imagesPerRow) * width;
      int srcY = (index / imagesPerRow) * height;

      //TODO paint...
      Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
      Rect dest = new Rect(x - width / 2, y - height / 2, x + width / 2, y + height / 2);
      c.drawBitmap(bitmap, src, dest, null);
   }
}
