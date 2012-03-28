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
 * Clear.java
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

public class Clear extends ActionEvent
{
	public Clear(DarkTowerThread thread)
	{
		super(thread);
	}
	
	public void run()
		throws InterruptedException, ResetException, DisableException
	{
//		Player player = (Player) thread.getPlayerList().get(
//			thread.getPlayerNo());
//		
//		thread.paintDarkTower("", Image.BLACK, Audio.CLEAR);
//		
//		// end turn
//		thread.sleep();
//		thread.endTurn();
		
		// end turn
		thread.endTurn(Button.CLEAR);
	}
}