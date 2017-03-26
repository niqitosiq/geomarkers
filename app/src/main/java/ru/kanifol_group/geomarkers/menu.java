package ru.kanifol_group.geomarkers;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class menu extends AppCompatActivity {
    int id = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Integer[] MassIntro = new Integer[]{R.id.TodoList};
        for (Integer i = 0; i < MassIntro.length; i++) {
            TextView lay = (TextView) findViewById(MassIntro[i]);
            lay.setTypeface(Typeface.createFromAsset(getAssets(), "Intro.otf"));
        }
        LinearLayout layout = (LinearLayout) findViewById(R.id.maincontainer);
        layout.removeAllViewsInLayout();
        makestartheight();// make start min height for note-block



        DBHelper dbHelper = new DBHelper(this);
        String[][] allNotes = dbHelper.getListMarkers();
        for (int i = 0; i < allNotes.length; i++) {
            //тут характеристики для каждого элемента
            LayoutInflater inflater = LayoutInflater.from(this);
            LinearLayout custom_layout = (LinearLayout) inflater.inflate(R.layout.layout_default, null, false);//Выборка шаблона для стандартного элемента.
            if (allNotes[i][0] != null) {
                //Айди
                custom_layout.setId(Integer.parseInt(allNotes[i][0]));
                //Название
                ((TextView) custom_layout.findViewById(R.id.name)).setText(allNotes[i][1]);
                //Описание
                ((TextView) custom_layout.findViewById(R.id.descriptionview)).setText(allNotes[i][2]);
                layout.addView(custom_layout); //Добавление элемента
                //Добавление шрифтов
                Integer[] MassRoboto = new Integer[]{R.id.name, R.id.descriptionview};
                for (Integer j = 0; j < MassRoboto.length; j++) {
                    TextView lay = (TextView) custom_layout.findViewById(MassRoboto[j]);
                    lay.setTypeface(Typeface.createFromAsset(getAssets(), "Roboto.ttf"));
                }
            }

        }



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
