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
 * Audio.java
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
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;


public class Audio
{
	public static final int NA = 0;
	public static final int BATTLE = 1;
	public static final int BAZAAR = 2;
	public static final int BAZAARCLOSED = 3;
	public static final int BEEP = 4;
	public static final int CLEAR = 5;
	public static final int DARKTOWER = 6;
	public static final int DRAGON = 7;
	public static final int DRAGONKILL = 8;
	public static final int ENDTURN = 9;
	public static final int ENEMYHIT = 10;
	public static final int FRONTIER = 11;
	public static final int INTRO = 12;
	public static final int LOST = 13;
	public static final int PEGASUS = 14;
	public static final int PLAGUE = 15;
	public static final int PLAYERHIT = 16;
	public static final int SANCTUARY = 17;
	public static final int TOMB = 18;
	public static final int TOMBBATTLE = 19;
	public static final int TOMBNOTHING = 20;
	public static final int WRONG = 21;
	public static final int STARVING = 22;

	//TODO is there a better way, is creating a media player each time too expensive?
	public static MediaPlayer getAudioClip(Context context, int audioNo)
	{
		MediaPlayer player = null;
		
		int id;
		
		switch(audioNo) {
         case BATTLE:
            id = R.raw.battle;
            break;
         case BAZAAR:
            id = R.raw.bazaar;
            break;
         case BAZAARCLOSED:
            id = R.raw.bazaar_closed;
            break;
         case BEEP:
            id = R.raw.beep;
            break;
         case CLEAR:
            id = R.raw.clear;
            break;
         case DARKTOWER:
            id = R.raw.darktower;
            break;
         case DRAGON:
            id = R.raw.dragon;
            break;
         case DRAGONKILL:
            id = R.raw.dragon_kill;
            break;
         case ENDTURN:
            id = R.raw.end_turn;
            break;
         case ENEMYHIT:
            id = R.raw.enemy_hit;
            break;
         case FRONTIER:
            id = R.raw.frontier;
            break;
         case INTRO:
            id = R.raw.intro;
            break;
         case LOST:
            id = R.raw.lost;
            break;
         case PEGASUS:
            id = R.raw.pegasus;
            break;
         case PLAGUE:
            id = R.raw.plague;
            break;
         case PLAYERHIT:
            id = R.raw.player_hit;
            break;
         case SANCTUARY:
            id = R.raw.sanctuary;
            break;
         case TOMB:
            id = R.raw.tomb;
            break;
         case TOMBBATTLE:
            id = R.raw.tomb_battle;
            break;
         case TOMBNOTHING:
            id = R.raw.tomb_nothing;
            break;
         case WRONG:
            id = R.raw.wrong;
            break;
         case STARVING:
            id = R.raw.starving;
            break;
         case NA:
         default:
            id = -1;
            break;
		}
		
	   if(id != -1) {
	      player = MediaPlayer.create(context, id);
	   }
		
		return player;
	}

	public static void play(Context context, int audioNo)
	{
		final MediaPlayer player = getAudioClip(context, audioNo);
      if ( player != null ) {
   		player.setOnCompletionListener(new OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
               player.release();
            }
   		});
		   player.start();
		}
	}
}
