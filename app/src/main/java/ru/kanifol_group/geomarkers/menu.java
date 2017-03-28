package ru.kanifol_group.geomarkers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
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
import android.widget.Toast;

public class menu extends AppCompatActivity {
    int id = 0;
    static int[][] switchstate = new int[128][2];
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

                if (Integer.parseInt(allNotes[i][3])==1) {
                    ((ImageView) custom_layout.findViewById(R.id.checkstate)).setImageResource(R.drawable.logo_checked);
                    ((ImageView) custom_layout.findViewById(R.id.checkstate)).setTag(R.drawable.logo_checked);
                } else {
                    ((ImageView) custom_layout.findViewById(R.id.checkstate)).setImageResource(R.drawable.logo_not_checked);
                    ((ImageView) custom_layout.findViewById(R.id.checkstate)).setTag(R.drawable.logo_not_checked);
                }

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
                mainblock.setMinimumHeight(height.getHeight());
            }
        });
    }
    public void switchfund(View v){
        int parentid = ((LinearLayout) v.getParent().getParent().getParent().getParent()).getId();
        DBHelper dbHelper = new DBHelper(this);
        ImageView switchimg = (ImageView) v;
        assert(R.id.checkstate == switchimg.getId());
        Integer integer = (Integer) switchimg.getTag();
        integer = integer == null ? 0 : integer;
        switch(integer) {
            case R.drawable.logo_checked:
                switchimg.setImageResource(R.drawable.logo_not_checked);
                switchimg.setTag(R.drawable.logo_not_checked);
                dbHelper.updateparamselem("id", Integer.toString(parentid), "signal", "0");
                break;
            case R.drawable.logo_not_checked:
                switchimg.setImageResource(R.drawable.logo_checked);
                switchimg.setTag(R.drawable.logo_checked);
                dbHelper.updateparamselem("id", Integer.toString(parentid), "signal", "1");
            default:
                break;
        }
    }
    public void startSettings(View v) {
        Intent intObj = new Intent(this, settings.class);
        intObj.putExtra("id", 0);
        startActivity(intObj);

    }
    public void settingselem(View v){
        LinearLayout layoutparent = (LinearLayout) v.getParent().getParent().getParent().getParent();
        id = layoutparent.getId();
        Intent intObj = new Intent(this, settings.class);
        intObj.putExtra("id", id);
        startActivity(intObj); //стартуем СекондАктивити.класс для редактирования или удаления заметок
        v.getParent();

    }



}
