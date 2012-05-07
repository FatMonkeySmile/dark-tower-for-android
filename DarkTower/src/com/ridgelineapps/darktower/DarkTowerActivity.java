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
 */
package com.ridgelineapps.darktower;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

//TODO
//
// Classic vs. 8 bit look (switchable in prefs)

public class DarkTowerActivity extends Activity {
   public DarkTower darkTower;
   
   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.main);
      // BoardView view = new BoardView(this);
      // setContentView(view);
      darkTower = new DarkTower(this);
   }
   
   public BoardView getBoardView() {
      return (BoardView) findViewById(R.id.boardview);
   }
   
   public DarkTowerView getDarkTowerView() {
      return (DarkTowerView) findViewById(R.id.darktowerview);
   }
   
   public void yesBuyButton(View target) {
      System.out.println("yes/buy");
   }
   
   public void repeatButton(View target) {
      System.out.println("repeat");
   }
}