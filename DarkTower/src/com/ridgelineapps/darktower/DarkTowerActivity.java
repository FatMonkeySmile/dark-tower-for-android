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


public class DarkTowerActivity extends Activity {
   public DarkTower darkTower;
   
   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.main);
      darkTower = new DarkTower(this);
   }

   public BoardView getBoardView() {
      return (BoardView) findViewById(R.id.boardview);
   }
   
   public DarkTowerView getDarkTowerView() {
      return (DarkTowerView) findViewById(R.id.darktowerview);
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