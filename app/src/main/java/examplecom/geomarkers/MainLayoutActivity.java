package examplecom.geomarkers;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
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
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.Arrays;

public class MainLayoutActivity extends AppCompatActivity implements View.OnClickListener {
    int id = 0;
    int currentid;
    private GestureDetector detector;
    private View.OnTouchListener listner;
    int openid = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        //Intent intent = new Intent(this, MainLayoutActivity.class);
        //sendOrderedBroadcast(intent, "examplecom.geomarkers.GEOMARKERS_ON");


        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

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
        // Листнер для анимации
        /*
        final ScrollView mainscroll = (ScrollView)findViewById(R.id.scroll);
        mainscroll.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                LinearLayout parent = (LinearLayout) findViewById(R.id.others);
                int[] datamass = getdata();
                offsetlay(datamass[0], datamass[1], datamass[2], datamass[3], datamass[4], parent);
            }
        });
        */ // Анимациия убрана в целях повышения юзабилити
        // Листнер для автоскрытия блока "заметки"
        detector = new GestureDetector(new Swipelistner(){
            boolean showtrig = true;
            final LinearLayout tohide = (LinearLayout) findViewById(R.id.main_rect);
            final LinearLayout rectangels = (LinearLayout) findViewById(R.id.others_rect);
            @Override
            public void open() {
                if (showtrig == true){

                    TranslateAnimation translate = new TranslateAnimation(0.0f, 0.0f, 1.0f, -200.0f);
                    translate.setDuration(200);
                    translate.setFillAfter(true);
                    translate.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            rectangels.setLayoutParams(new TableLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, 0f));
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                        }
                    });
                    tohide.startAnimation(translate);
                    showtrig = false;
                }
            }

            @Override
            public void close() {
                if (showtrig == false) {
                    rectangels.setLayoutParams(new TableLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, 1f));
                    TranslateAnimation translate = new TranslateAnimation(0.0f, 0.0f, -200.0f, 0.0f);
                    translate.setDuration(200);
                    translate.setFillAfter(true);
                    tohide.startAnimation(translate);
                    showtrig = true;
                }
            }
        });
        listner = new View.OnTouchListener()
        {
            public boolean onTouch(View v, MotionEvent event)
            {
                if (detector.onTouchEvent(event))
                {
                    return true;
                }
                else{
                    return false;
                }
            }
        };
        //mainscroll.setOnTouchListener(listner);
    }

    // Описание анимации на главном экране
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
                final AnimationSet set = new AnimationSet(true);
                set.addAnimation(translate);
                set.setDuration(1000);
                set.addAnimation(scale);
                set.setFillAfter(true);
                toanim.startAnimation(set);
            }
        }

        for (int i=e_active, procent = 100; i<childs; i++, procent= procent-10) {
            if (procent > 0) {
                final View toanim = main.getChildAt(i);
                final ScaleAnimation scale = new ScaleAnimation(
                        1.0f, (float) procent / 100,
                        1.0f, (float) procent / 100,
                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                TranslateAnimation translate = new TranslateAnimation(
                        0.0f, 0.0f, 1.0f, -(((float) 100-procent) / 100)
                );
                final AnimationSet set = new AnimationSet(true);
                set.addAnimation(translate);
                set.addAnimation(scale);
                set.setDuration(1000);
                set.setFillAfter(true);
                toanim.startAnimation(set);
            }
        }
        for (int i = s_active; i<e_active; i++){
            final View toanim = main.getChildAt(i);
            final ScaleAnimation scale = new ScaleAnimation(
                    1.0f, 1.0f,
                    1.0f,  1.0f,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            toanim.startAnimation(scale);
        }

    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.addbutton) {
            Intent intObj = new Intent(this, SecondActivity.class); //стартуем СекондАктивити.класс
            startActivity(intObj);
        } else if (v.getId() == R.id.settings) {
            Intent intObj = new Intent(this, SecondActivity.class);
            LinearLayout layoutparent = (LinearLayout) v.getParent().getParent().getParent().getParent().getParent().getParent();
            id = layoutparent.getId();
            ;
            intObj.putExtra("id", id);
            startActivity(intObj); //стартуем СекондАктивити.класс для редактирования или удаления заметок
            v.getParent();
        } else {//Значит, текущее вью - блок заметки
            LinearLayout mainparent = (LinearLayout) findViewById(R.id.others);
            for (int i = 0; i < mainparent.getChildCount(); i++) {
                if (mainparent.getChildAt(i).getId() == v.getId()) {
                    currentid = i;
                }
            }
            tohidenotes(currentid);
        }
    }

    private void tohidenotes(Integer currentid){
        LinearLayout mainparent = (LinearLayout) findViewById(R.id.others);
        if (currentid != openid) {//Если элемент на который было произведено нажатие не был предварительно открыт
            //закрыть все кроме текущего
            for (int i = 0; i<mainparent.getChildCount()-1;i++){
                View currentelem = mainparent.getChildAt(i);
                if (i!=currentid){
                    open(currentelem, currentid);
                }
                else {
                    openid = currentid;
                    close(currentelem);
                }
                if (openid == -1) {//Если клик не первый. (начальное значение переменной -1)
                    openid = currentid;
                }
            }
        } else {
            openid = -1;
            for (int i = 0; i < mainparent.getChildCount() - 1; i++) {
                View currentelem = mainparent.getChildAt(i);
                open(currentelem, currentid);
            }
        }
    }
    private void close(View currentelem){//закрытие вкладки "заметки
        LinearLayout gonedlayout = (LinearLayout) currentelem.findViewById(R.id.main_action);
        gonedlayout.setVisibility(View.VISIBLE);
        ImageView imagegone = (ImageView) currentelem.findViewById(R.id.more);
        imagegone.setVisibility(View.GONE);
    }
    private void open(View currentelem, int numberof){//открытие вкладки "заметки

        //открытие
        LinearLayout gonedlayout = (LinearLayout) currentelem.findViewById(R.id.main_action);
        gonedlayout.setVisibility(View.GONE);
        ImageView imagegone = (ImageView) currentelem.findViewById(R.id.more);
        imagegone.setVisibility(View.VISIBLE);

        //анимация
        int[] maindata = getdata();//Получение данных из общего метода
        int toscrollheight = maindata[4]*numberof;//получение координаты для промотки
        ScrollView mainscroll = (ScrollView)findViewById(R.id.scroll);
        ObjectAnimator.ofInt(mainscroll, "scrollY",  (toscrollheight-300)).setDuration(500).start();
    }
    private int[] getdata(){// метод получения актуальных значений свойств элементов из любого участка кода (дабы не выделять эти переменные по сто раз)
        ScrollView mainscroll = (ScrollView)findViewById(R.id.scroll);
        LinearLayout parent = (LinearLayout) findViewById(R.id.others);
        int scrollY = mainscroll.getScrollY();
        int screen = mainscroll.getHeight();
        int lenght = parent.getHeight();
        int childs = parent.getChildCount();
        int heightoneelem = lenght/childs;
        int[] datamass = {scrollY, screen, lenght, childs, heightoneelem};
        return(datamass);
    }



    @Override
    public boolean dispatchTouchEvent(MotionEvent ev){
        super.dispatchTouchEvent(ev);
        return detector.onTouchEvent(ev);
    }
}
class Swipelistner extends GestureDetector.SimpleOnGestureListener {

    private static final int SWIPE_THRESHOLD = 20;
    private static final int SWIPE_VELOCITY_THRESHOLD = 20;
    public void close(){
    };
    public void open(){

    };

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        boolean result = false;
        try {
            float diffY = e2.getY() - e1.getY();
            float diffX = e2.getX() - e1.getX();
            if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                if (diffY > -250) {
                    close();
                } else {
                    open();
                }
            }
            result = true;

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result;
    }
}