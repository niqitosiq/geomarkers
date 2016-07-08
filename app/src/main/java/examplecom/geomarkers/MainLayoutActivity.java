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
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainLayoutActivity extends AppCompatActivity implements View.OnClickListener {
int id = 0;

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
                marketbutton.setOnClickListener(this);
                marketbutton.addView(tv);
                marketbutton.setId(id);
            }while(cursor.moveToNext());
        }
        else{
            //нихера не делать
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
        plusParams.setMargins(10,10,10,10);
        plusParams.gravity = Gravity.CENTER;
        addplus.addView(imgv, plusParams);
    }




    @Override
    public void onClick(View v) {


    if(v.getId()==R.id.addbutton) {
        Intent intObj = new Intent(this, SecondActivity.class); //стартуем СекондАктивити.класс
        startActivity(intObj);
    }
    else{
        Intent intObj = new Intent(this, SecondActivity.class); //стартуем СекондАктивити.класс для редактирования или удаления заметок
        id = v.getId();
        intObj.putExtra("id",id);
        startActivity(intObj);
    }
    }
}











