package examplecom.geomarkers;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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


    DBHelper dbHelper = new DBHelper(this);

 //   SQLiteDatabase db = dbHelper.getWritableDatabase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);


        Integer[] MassIntro = new Integer[]{R.id.TodoList};
        for (Integer i = 0; i < MassIntro.length; i++) {
            TextView lay = (TextView) findViewById(MassIntro[i]);
            lay.setTypeface(Typeface.createFromAsset(getAssets(), "Intro.otf"));
        }
        Integer[] MassRoboto = new Integer[]{R.id.name};
        for (Integer i = 0; i < MassRoboto.length; i++) {
            TextView lay = (TextView) findViewById(MassRoboto[i]);
            lay.setTypeface(Typeface.createFromAsset(getAssets(), "Roboto.ttf"));
        }


    }


    public void addMarker(View view) {
        Intent intObj = new Intent(this, SecondActivity.class);
        startActivity(intObj);

    }

    @SuppressLint("LongLogTag") // для вывода в лог большего объема текста.
    private void addWithParams(String params) {
        LinearLayout layout = (LinearLayout) findViewById(R.id.others);
        int inputNumber = (layout.getChildCount() - 1);
        //вывод кол-ва дочерних элементов в логи
        Log.i("количество дочерних элементов", String.valueOf(inputNumber));
        // Инициализация вида и параметров будущей кнопки
        LinearLayout addedbutton = new LinearLayout(this);
        addedbutton.setBottom(5);
        addedbutton.setClickable(true);
        addedbutton.setBackgroundResource(R.drawable.default_rectangle);
        addedbutton.setOnClickListener(this);
        addedbutton.setId(R.id.addbutton);
        addedbutton.setHorizontalGravity(View.TEXT_ALIGNMENT_CENTER);
        // Добавление
        layout.addView(addedbutton, inputNumber);

    }


    @Override
    public void onClick(View v) {
        LinearLayout layout = (LinearLayout) findViewById(R.id.others);
        LinearLayout.LayoutParams viewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout newlayout = new LinearLayout(this);
        newlayout.setBackgroundResource(R.drawable.default_rectangle);
        newlayout.setOrientation(LinearLayout.HORIZONTAL);
        newlayout.setBottom(5);
        layout.addView(newlayout);

    }
}




class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "database", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table geomarkers ("
                + "id integer primary key autoincrement,"
                + "name text,"
                + "description text,"
                + "geolocation text,"
                + "signal integer" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}




