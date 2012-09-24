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
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;

public class ActivityStart extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);
        
        ((Button)findViewById(R.id.start_startGame)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ActivityStart.this, ActivityMenu.class);
                finish();
                startActivity(i);
            }
        });

        ((Button)findViewById(R.id.start_howToPlay)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
               Uri uriUrl = Uri.parse("http://well-of-souls.com/tower/index.html");
               Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
               startActivity(launchBrowser);
            }
        });
    }
}