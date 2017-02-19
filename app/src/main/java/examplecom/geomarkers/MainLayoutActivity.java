package examplecom.geomarkers;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class MainLayoutActivity extends AppCompatActivity implements View.OnClickListener {
    int id = 0;
    boolean[] openOrNot = new boolean[128];
    int[] ids=new int[128];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for(int i = 0; i<openOrNot.length;i++){openOrNot[i]=false;}
        //db.delete("geomarkers",null,null);            //если присутствуют баги разкоммитить это и запустить один раз (очищает Базу данных)
        Integer[] MassIntro = new Integer[]{R.id.TodoList};
        for (Integer i = 0; i < MassIntro.length; i++) {
            TextView lay = (TextView) findViewById(MassIntro[i]);
            lay.setTypeface(Typeface.createFromAsset(getAssets(), "Intro.otf"));
        }
        LinearLayout layout = (LinearLayout) findViewById(R.id.others);
        layout.removeAllViewsInLayout();
        //запросы к БД + построение списка
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
                LinearLayout custom_layout = (LinearLayout) inflater.inflate(R.layout.empty_marker, null, false);
                TextView textmarker = (TextView) custom_layout.findViewById(R.id.name);
                ImageView shesterenka = (ImageView) custom_layout.findViewById(R.id.settings);
                shesterenka.setOnClickListener(this);
                TextView desc = (TextView) custom_layout.findViewById(R.id.descriptionview);
                desc.setText(description);
                custom_layout.setId(id);
                ids[j]=id;
                textmarker.setText(name);
                custom_layout.setOnClickListener(this);
                layout.addView(custom_layout);
                Integer[] MassRoboto = new Integer[]{R.id.name, R.id.description_text};
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
        LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout addbutton = new LinearLayout(this);
        int gravity = Gravity.CENTER;
        lParams.gravity = gravity;
        addbutton.setOrientation(LinearLayout.VERTICAL);
        addbutton.setBottom(5);
        addbutton.setBackgroundResource(R.drawable.default_rectangle);
        addbutton.setOnClickListener(this);
        addbutton.setId(R.id.addbutton);
        layout.addView(addbutton, lParams);
        LinearLayout addplus = (LinearLayout) findViewById(R.id.addbutton);
        ImageView imgv = new ImageView(this);
        imgv.setBackgroundResource(R.drawable.plus);
        LinearLayout.LayoutParams plusParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        plusParams.gravity = gravity;
        plusParams.setMargins(10, 10, 10, 10);
        plusParams.gravity = Gravity.CENTER;
        addplus.addView(imgv, plusParams);
        final ScrollView mainscroll = (ScrollView)findViewById(R.id.scroll);
        mainscroll.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                LinearLayout parent = (LinearLayout) findViewById(R.id.others);
                int scrollY = mainscroll.getScrollY();
                int screen = mainscroll.getHeight();
                int childs = parent.getChildCount();
                int lenght = parent.getHeight();
                int heightoneelem = lenght/childs;
                offsetlay(scrollY, screen, lenght, childs, heightoneelem, parent);
            }
        });

        LinearLayout rects = (LinearLayout)findViewById(R.id.others_rect);
        rects.setOnTouchListener(new OnSwipeTouchListener(MainLayoutActivity.this) {
            public void onSwipeTop() {
                Toast.makeText(MainLayoutActivity.this, "top", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeRight() {
                Toast.makeText(MainLayoutActivity.this, "right", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeLeft() {
                Toast.makeText(MainLayoutActivity.this, "left", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeBottom() {
                Toast.makeText(MainLayoutActivity.this, "bottom", Toast.LENGTH_SHORT).show();
            }

        });
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev){
        super.dispatchTouchEvent(ev);
        return productGestureDetector.onTouchEvent(ev);
    }
    public void offsetlay(int scroll, int screen, int length, int childs, int normal_size, LinearLayout parent){
        int onscreen = Math.round((screen*childs)/length)-3; // количество одновременно показаных эл-тов на экране. 2 - количество скрываемых
        //Log.i("SCALE", Integer.toString(onscreen) + "," + Integer.toString(currentpos));
        int s_active = Math.round(scroll/normal_size) + 1; // Начало активной области
        int e_active = s_active + onscreen; // окончание активной области
        final LinearLayout main = parent;
        for (int i=s_active, procent = 100; i>-1; i--, procent= procent-10){
            final View toanim = main.getChildAt(i);
            if (procent > 0) {
                ScaleAnimation scale = new ScaleAnimation(
                        1.0f, (float) procent / 100,
                        1.0f, (float) procent / 100,
                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                TranslateAnimation translate = new TranslateAnimation(
                        0.0f, 0.0f, 0.0f, (((float) 100-procent) / 100)
                );
                Animation goneout = new AlphaAnimation(0.0f, 1.0f);
                goneout.setDuration(500);
                goneout.setFillAfter(false);
                final AnimationSet set = new AnimationSet(true);
                set.addAnimation(translate);
                set.setDuration(1000);
                set.addAnimation(scale);
                set.setFillAfter(true);
                Animation gonein = new AlphaAnimation(1.0f, 0.0f);
                gonein.setDuration(250);
                gonein.setFillAfter(true);
                gonein.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        toanim.startAnimation(set);
                    }
                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                toanim.startAnimation(gonein);
            }
        }

        for (int i=e_active+1, procent = 100; i<childs; i++, procent= procent-10) {
            if (procent > 0) {
                final View toanim = main.getChildAt(i);
                final ScaleAnimation scale = new ScaleAnimation(
                        1.0f, (float) procent / 100,
                        1.0f, (float) procent / 100,
                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                TranslateAnimation translate = new TranslateAnimation(
                        0.0f, 0.0f, 1.0f, -(((float) 100-procent) / 100)
                );
                Animation goneout = new AlphaAnimation(0.0f, 1.0f);
                goneout.setDuration(500);
                final AnimationSet set = new AnimationSet(true);
                set.addAnimation(translate);
                set.addAnimation(scale);
                set.addAnimation(goneout);
                set.setDuration(1000);
                set.setFillAfter(true);
                Animation gonein = new AlphaAnimation(1.0f, 0.0f);
                gonein.setDuration(250);
                gonein.setFillAfter(false);
                gonein.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        toanim.startAnimation(set);
                    }
                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                toanim.startAnimation(gonein);
            }
        }

    }
    @Override
    public void onClick(View v) {
    if(v.getId()==R.id.addbutton) {
        Intent intObj = new Intent(this, SecondActivity.class); //стартуем СекондАктивити.класс
        startActivity(intObj);
    }
    else if (v.getId()==R.id.settings) {
        Intent intObj = new Intent(this, SecondActivity.class);
        LinearLayout layoutparent = (LinearLayout)v.getParent().getParent().getParent().getParent().getParent().getParent();
        id =  layoutparent.getId();;
        intObj.putExtra("id",id);
        startActivity(intObj); //стартуем СекондАктивити.класс для редактирования или удаления заметок
        v.getParent();
    }
    else{
        int numberOfBooleanMassive = 0;
        int idOfParent = v.getId();
        for(int i =0;i<ids.length;i++){
            if(idOfParent==ids[i]){
                numberOfBooleanMassive = i;
                break;
            }
        }
        if(openOrNot[numberOfBooleanMassive]) {
            try {
                LinearLayout gonedlayout = (LinearLayout) v.findViewById(R.id.main_action);
                gonedlayout.setVisibility(View.GONE);
                ImageView imagegone = (ImageView) v.findViewById(R.id.more);
                imagegone.setVisibility(View.VISIBLE);
                openOrNot[numberOfBooleanMassive] = !openOrNot[numberOfBooleanMassive];
            }catch (Exception e){
            }
        }
        else{
            try {
                LinearLayout gonedlayout = (LinearLayout) v.findViewById(R.id.main_action);
                gonedlayout.setVisibility(View.VISIBLE);
                ImageView imagegone = (ImageView) v.findViewById(R.id.more);
                imagegone.setVisibility(View.GONE);
                openOrNot[numberOfBooleanMassive] = !openOrNot[numberOfBooleanMassive];
            }catch (Exception e){
            }
        }
    }
    }
}