package ru.kanifol_group.geomarkers;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

public class menu extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Integer[] MassIntro = new Integer[]{R.id.TodoList};
        for (Integer i = 0; i < MassIntro.length; i++) {
            TextView lay = (TextView) findViewById(MassIntro[i]);
            lay.setTypeface(Typeface.createFromAsset(getAssets(), "Intro.otf"));
        }

        makestartheight();// make start min height for note-block

    }


    private void makestartheight(){
        final LinearLayout mainblock = (LinearLayout)findViewById(R.id.maincontainer);
        final LinearLayout height = (LinearLayout) findViewById(R.id.others_rect);
        height.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                height.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                Log.i("Height", Integer.toString(height.getHeight()));
                mainblock.setMinimumHeight(height.getHeight());
            }
        });
    }
    public void startSettings(View v) {
        Intent intObj = new Intent(this, settings.class);startActivity(intObj);

    }



}
