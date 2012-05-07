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
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;


public class DarkTowerView extends View
{
	private String label = null;
//	private ImageIcon image = null;
	private int color; // = new Color(255, 0, 0);
	private boolean flash = false;
	private int flashInterval = 0;
	private boolean enabled = false;
	
   DarkTowerActivity context;

   public DarkTowerView(Context context, AttributeSet attributes)
   {
      this((DarkTowerActivity) context);
   }
   
	public DarkTowerView(DarkTowerActivity context)
   {
      super(context);
      this.context = context;
      
		label = "1";
//		image = Image.getImageIcon(Image.BLACK);
	}

	public void setColor(int color)
	{
		this.color = color;
	}

	//TODO
//	public void setLabel(String label)
//	{
//		this.label = label;
//		repaint();
//	}
//	
//	public void setImage(ImageIcon image)
//	{
//		this.image = image;
//		repaint();
//	}

	public void setFlash(boolean flash)
	{
		this.flash = flash;
	}

	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}

   @Override
   protected void onDraw(Canvas canvas) {

//      int width = canvas.getWidth();
//      int height = canvas.getHeight();
//      float dx = width / 2;
//      float dy = height / 2;
//      canvas.translate(dx, dy);
      Paint paint = new Paint();
      paint.setARGB(255, 0, 255, 210);
      canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
//		Font font = new Font("Arial", Font.BOLD, 20);
//		Rectangle2D fontrec = font.getStringBounds(label, g2D.getFontRenderContext());
////		Rectangle2D standardrec = font.getStringBounds("00", g2D.getFontRenderContext());
//
////		int labelx = ( getWidth() + (int) standardrec.getWidth() ) / 2 - (int) fontrec.getWidth() + 1;
//		int labelx = ( getWidth() - (int) fontrec.getWidth() ) / 2;
//		int labely = 20;
//		int imagex = ( getWidth() - image.getIconWidth() ) / 2;
//		int imagey = 26;
//
//		if ( flash )
//			flashInterval++;
//		else
//			flashInterval = 0;
//		
//		g.clearRect(0, 0, getWidth(), getHeight());
//		g.setColor(new Color(0, 0, 0));
//		g.fillRect(0, 0, getWidth() - 2, getHeight());
//		g.setFont(font);
//		g.setColor(color);
//		if ( flashInterval % 2 == 0 )
//			g.drawString(label, labelx, labely);
//		if ( enabled )
//			g.drawImage(image.getImage(), imagex, imagey, this);
	}

   @Override
   protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
      setMeasuredDimension(100, 100);
   }
}