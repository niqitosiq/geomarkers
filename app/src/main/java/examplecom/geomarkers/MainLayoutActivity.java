package examplecom.geomarkers;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ActionMenuView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainLayoutActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
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


        //layout.removeAllViewsInLayout(); ////если присутствуют баги разкоммитить это и запустить один раз
        //дальше происходят страшные вещи которые я сам не понимаю однако попытался прокоментировать;


        //заполнение базыданных (далее БД) из СекондАктивити.класс
        String nametext = getIntent().getStringExtra("nametext");//получаю данные с СекондАктивити.класс
        String description = getIntent().getStringExtra("descriptiontext");//получаю данные с СекондАктивити.класс
        //       скр
        Boolean swpos = getIntent().getBooleanExtra("boolswitch",false);//получаю данные с СекондАктивити.класс
        ContentValues cv = new ContentValues();//временная таблица данных ,которые получаем из СекондАктивити.класс
        cv.put("name",nametext);
        cv.put("description", description);
        int signal=0;
        if(swpos){signal=1;}//переврдим булеан тип в интеджер для хранения в БД
        cv.put("signal",signal);
        db.insert("geomarkers",null,cv);
        //запросы к БД + построение списка
        Cursor cursor = db.query("geomarkers",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            int columnIdIndex = cursor.getColumnIndex("id");
            int columnNameIndex = cursor.getColumnIndex("name");
            do{
                int id = cursor.getInt(columnIdIndex);
                String name = cursor.getString(columnNameIndex);
                LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                LinearLayout marketbutton = new LinearLayout(this);
                marketbutton.setBackgroundResource(R.drawable.default_rectangle);//создание меток в списке
                int gravity = Gravity.CENTER;
                lParams.gravity = gravity;
                layout.addView(marketbutton, lParams);
                TextView tv = new TextView(this);
                tv.setText(name);
                tv.setTextSize(15);//пишем текст на метке
                tv.setGravity(Gravity.CENTER);
                marketbutton.addView(tv);






              /*  int id = cursor.getInt(columnIdIndex);
                String name = cursor.getString(columnNameIndex);
                LinearLayout layout = (LinearLayout) findViewById(R.id.others);
                LinearLayout addedbutton = new LinearLayout(this);
                addedbutton.setBottom(5);                                                           //создание меток в списке
                addedbutton.setClickable(false);//позже расширить функциОНАЛ
                addedbutton.setBackgroundResource(R.drawable.default_rectangle);
                addedbutton.setOnClickListener(this);                                                   //мертвый кусок кода потом убрать если не нужен будет
                addedbutton.setHorizontalGravity(View.TEXT_ALIGNMENT_CENTER);
                // Добавление
                try {
                    layout.addView(addedbutton, id);
                }catch (Exception e){}
                TextView tv = new TextView(this);                                                   //пишем текст на метке
                tv.setText(name);
                tv.setTextSize(15);
                addedbutton.addView(tv);//Никита, кастомизируй текст ну там белый цвет,посерединки    */







            }while(cursor.moveToNext());
        }
        else{
            //нихера не делать
        }
        cursor.close();
        dbHelper.close();

       //LinearLayout layout = (LinearLayout) findViewById(R.id.others);
        LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout addbutton = new LinearLayout(this);
        int gravity = Gravity.CENTER;
        lParams.gravity = gravity;
        addbutton.setBottom(5);
        addbutton.setBackgroundResource(R.drawable.default_rectangle);
        addbutton.setOnClickListener(this);
        layout.addView(addbutton,lParams);
        ImageView imgv = new ImageView(this);
        imgv.setBackgroundResource(R.drawable.plus);
        layout.addView(imgv);
    }


     public void addMarker(View v) {
        Intent intObj = new Intent(this, SecondActivity.class);
        startActivity(intObj);
       // String params = "хер";
       // addWithParams(params);
    }

   @SuppressLint("LongLogTag") // для вывода в лог большего объема текста.
    private void addWithParams(String params) {
        LinearLayout layout = (LinearLayout) findViewById(R.id.others);
        int inputNumber = (layout.getChildCount() - 1);
        //вывод кол-ва дочерних элементов в логи
        Log.i("количество дочерних элементов", String.valueOf(inputNumber));
        // Инициализация вида и параметров будущей кнопки                                           //позже убрать (в случае ненадобности)
        LinearLayout addedbutton = new LinearLayout(this);
        addedbutton.setBottom(5);
        addedbutton.setClickable(true);
        addedbutton.setBackgroundResource(R.drawable.default_rectangle);
        addedbutton.setOnClickListener(this);
        addedbutton.setHorizontalGravity(View.TEXT_ALIGNMENT_CENTER);
        // Добавление
        layout.addView(addedbutton, inputNumber);

    }


    @Override
    public void onClick(View v) {
        Intent intObj = new Intent(this, SecondActivity.class); //стартуем СекондАктивити.класс
        startActivity(intObj);
    }
}





class DBHelper extends SQLiteOpenHelper {                                           //класс описывающий работу БД
    public DBHelper(Context context) {
        super(context, "database", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table geomarkers ("
                + "id integer primary key autoincrement,"
                + "name text,"                                  //метод создания БД
                + "description text,"
                + "geolocation text,"
                + "signal integer" + ");");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS + TABLE_NAME");
                onCreate(db);                                       //метод обновления при устаревании версии (фактически не нужен)
    }
}






