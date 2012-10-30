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
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class ActivityMenu extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        
        ((Button)findViewById(R.id.menu_start)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ActivityMenu.this, ActivityGame.class);
                i.putExtra("player1", getPlayer1());
                i.putExtra("player2", getPlayer2());
                i.putExtra("player3", getPlayer3());
                i.putExtra("player4", getPlayer4());
                i.putExtra("fast_display_computer", getFastDisplayComputer());
                i.putExtra("mute_sound", getMuteSound());
                i.putExtra("original_board", getOriginalBoard());
                startActivity(i);
            }
        });

        ((Button)findViewById(R.id.menu_exit)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    
    public String getPlayer1() {
        RadioGroup rGroup = (RadioGroup)findViewById(R.id.player1RadioGroup);
        RadioButton checkedRadioButton = (RadioButton)rGroup.findViewById(rGroup.getCheckedRadioButtonId());
        if(checkedRadioButton.getId() == R.id.player1_computer) {
            return "computer";
        }
        else if(checkedRadioButton.getId() == R.id.player1_human) {
            return "human";
        }
        return "none";
    }
    
    public String getPlayer2() {
        RadioGroup rGroup = (RadioGroup)findViewById(R.id.player2RadioGroup);
        RadioButton checkedRadioButton = (RadioButton)rGroup.findViewById(rGroup.getCheckedRadioButtonId());
        if(checkedRadioButton.getId() == R.id.player2_computer) {
            return "computer";
        }
        else if(checkedRadioButton.getId() == R.id.player2_human) {
            return "human";
        }
        return "none";
    }
    
    public String getPlayer3() {
        RadioGroup rGroup = (RadioGroup)findViewById(R.id.player3RadioGroup);
        RadioButton checkedRadioButton = (RadioButton)rGroup.findViewById(rGroup.getCheckedRadioButtonId());
        if(checkedRadioButton.getId() == R.id.player3_computer) {
            return "computer";
        }
        else if(checkedRadioButton.getId() == R.id.player3_human) {
            return "human";
        }
        return "none";
    }
    
    public String getPlayer4() {
        RadioGroup rGroup = (RadioGroup)findViewById(R.id.player4RadioGroup);
        RadioButton checkedRadioButton = (RadioButton)rGroup.findViewById(rGroup.getCheckedRadioButtonId());
        if(checkedRadioButton.getId() == R.id.player4_computer) {
            return "computer";
        }
        else if(checkedRadioButton.getId() == R.id.player4_human) {
            return "human";
        }
        return "none";
    }
    
    public boolean getFastDisplayComputer() {
        CheckBox cb = (CheckBox) findViewById(R.id.fast_display_computer);
        return cb.isChecked();
    }
    
    public boolean getOriginalBoard() {
       return true;
//        CheckBox cb = (CheckBox) findViewById(R.id.original_board);
//        return cb.isChecked();        
    }
    
    public boolean getMuteSound() {
        CheckBox cb = (CheckBox) findViewById(R.id.mute_sound);
        return cb.isChecked();        
    }
}