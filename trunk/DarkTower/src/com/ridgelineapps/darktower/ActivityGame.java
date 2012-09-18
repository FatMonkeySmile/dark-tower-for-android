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
import android.widget.Toast;


public class ActivityGame extends Activity {
   long lastBackClick;
   public DarkTower darkTower;
   String player1;
   String player2;
   String player3;
   String player4;
   
   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.game);
      player1 = getIntent().getExtras().getString("player1");
      player2 = getIntent().getExtras().getString("player2");
      player3 = getIntent().getExtras().getString("player3");
      player4 = getIntent().getExtras().getString("player4");
      darkTower = new DarkTower(this);
   }
   
   @Override
   protected void onDestroy() {
       super.onDestroy();
       try {
           // Not great, but simplest way to stop AI threads
           android.os.Process.killProcess(android.os.Process.myPid());
       }
       catch(Exception e) {
           e.printStackTrace();
       }
   }
   
   @Override
   public void onBackPressed() {
      long now = System.currentTimeMillis();
      if (now - lastBackClick < 3000) // 3 seconds
         finish();
      else {
         lastBackClick = now;
         Toast.makeText(this, "Hit back again to quit.", Toast.LENGTH_SHORT).show();
      }
      return;
   }
   
   public BoardView getBoardView() {
      return (BoardView) findViewById(R.id.boardview);
   }
   
   public DarkTowerView getDarkTowerView() {
      return (DarkTowerView) findViewById(R.id.darktowerview);
   }
   
   public InventoryView getInventoryView() {
       return (InventoryView) findViewById(R.id.inventoryview);
   }
   
   public void yesBuyButton(View target) {
      darkTower.thread.addAction(Button.YES);
   }
   
   public void repeatButton(View target) {
      darkTower.thread.addAction(Button.REPEAT);
   }

   public void noEndButton(View target) {
      darkTower.thread.addAction(Button.NO);
   }
   
   public void haggleButton(View target) {
      darkTower.thread.addAction(Button.HAGGLE);
   }
   
   public void bazaarButton(View target) {
      darkTower.thread.addAction(Button.BAZAAR);
   }
   
   public void clearButton(View target) {
      darkTower.thread.addAction(Button.CLEAR);
   }
   
   public void ruinButton(View target) {
      darkTower.thread.addAction(Button.RUIN);
   }

   public void moveButton(View target) {
      darkTower.thread.addAction(Button.MOVE);
   }

   public void sanctuaryButton(View target) {
      darkTower.thread.addAction(Button.SANCTUARY);
   }

   public void darktowerButton(View target) {
      darkTower.thread.addAction(Button.DARKTOWER);
   }
   
   public void frontierButton(View target) {
      darkTower.thread.addAction(Button.FRONTIER);
   }

   public void inventoryButton(View target) {
      darkTower.thread.addAction(Button.INVENTORY);
   }
}