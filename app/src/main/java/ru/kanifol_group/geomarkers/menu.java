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
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("geomarkers",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            int columnIdIndex = cursor.getColumnIndex("id");
            int columnNameIndex = cursor.getColumnIndex("name");
            int columnDescIndex = cursor.getColumnIndex("description");
            int j =0;
            do{
                int id = cursor.getInt(columnIdIndex);
                String name = cursor.getString(columnNameIndex);
                String description = cursor.getString(columnDescIndex);
                LayoutInflater inflater = LayoutInflater.from(this);
                LinearLayout custom_layout = (LinearLayout) inflater.inflate(R.layout.layout_default, null, false);
                TextView textmarker = (TextView) custom_layout.findViewById(R.id.name);
                TextView desc = (TextView) custom_layout.findViewById(R.id.descriptionview);
                desc.setText(description);
                custom_layout.setId(id);
                textmarker.setText(name);
                layout.addView(custom_layout);
                Integer[] MassRoboto = new Integer[]{R.id.name, R.id.descriptionview};
                for (Integer i = 0; i < MassRoboto.length; i++) {
                    TextView lay = (TextView) custom_layout.findViewById(MassRoboto[i]);
                    lay.setTypeface(Typeface.createFromAsset(getAssets(), "Roboto.ttf"));
                }
                j++;
            }while(cursor.moveToNext());
        }
        else{
        }
        cursor.close();
        dbHelper.close();

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
