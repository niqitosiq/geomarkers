package ru.kanifol_group.geomarkers;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.attr.id;

/**
 * Created by nikita on 12.03.17.
 */

public class settings extends AppCompatActivity {

    int id = 0;
    double longitude;
    double latitude;
    double longitudeFromMap;
    double latitudeFromMap;
    int radius;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        EditText nameField = (EditText)findViewById(R.id.title_edit);
        EditText descField = (EditText)findViewById(R.id.description_edit);
        //SettingUp custom fonts
        Integer[] MassIntro = new Integer[]{R.id.mainText};
        for (Integer i = 0; i < MassIntro.length; i++) {
            TextView lay = (TextView) findViewById(MassIntro[i]);
            lay.setTypeface(Typeface.createFromAsset(getAssets(), "Intro.otf"));
        }
        Integer[] MassRoboto = new Integer[]{R.id.title_text, R.id.description_text, R.id.map_text};
        for (Integer i = 0; i < MassRoboto.length; i++) {
            TextView lay = (TextView) findViewById(MassRoboto[i]);
            lay.setTypeface(Typeface.createFromAsset(getAssets(), "Roboto.ttf"));
        }

        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor cursor = db.query("geomarkers", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            int columnIdIndex = cursor.getColumnIndex("id");
            int columnNameIndex = cursor.getColumnIndex("name");
            int columnDescriptionIndex = cursor.getColumnIndex("description");
            int columnLatitudeIndex = cursor.getColumnIndex("latitude");
            int columnLongitudeIndex = cursor.getColumnIndex("longitude");
            int columnSignalIndex = cursor.getColumnIndex("signal");
            int idintent = getIntent().getIntExtra("id", 0);
            do {
                id = cursor.getInt(columnIdIndex);
                if (id==idintent){
                    String name = cursor.getString(columnNameIndex);
                    String description = cursor.getString(columnDescriptionIndex);
                    int signalint = cursor.getInt(columnSignalIndex);
                    latitude = cursor.getDouble(columnLatitudeIndex);
                    longitude = cursor.getDouble(columnLongitudeIndex);
                    boolean signal = false;
                    if(signalint==1){signal = true;}
                    TextView namev = (TextView)findViewById(R.id.title_edit);
                    TextView descv = (TextView)findViewById(R.id.description_edit);
                    namev.setText(name);
                    descv.setText(description);
                    //deletebutton.setClickable(true);

                }
                else{
                    db.close();
                }
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();

    }
    public void okButton(View v){
        int idintent = getIntent().getIntExtra("id",0);// Получение айди элемента на который было совершено покушение
        if (idintent == 0) {

            ContentValues cv = new ContentValues();
            EditText nametext = (EditText) findViewById(R.id.title_edit);
            EditText descriptiontext = (EditText) findViewById(R.id.description_edit);//обьявляеем поля ввода и свитч
            if (nametext.getText().toString().length() < 1) {
                Toast toast = Toast.makeText(getApplicationContext(), //проверяем полность 1-ого поля ввода
                        "Заполните первое поле", Toast.LENGTH_SHORT);
                toast.show();
            } else {

                cv.put("name", nametext.getText().toString());
                //подготавливаем данные к отправке
                cv.put("description", descriptiontext.getText().toString());
                cv.put("latitude", latitudeFromMap);
                cv.put("longitude", longitudeFromMap);
                cv.put("radius", radius);
                DBHelper dbHelper = new DBHelper(this);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.insert("geomarkers", null, cv);
                db.close();
                Log.d("db", "insert");
                Intent intent = new Intent(this, menu.class);
                startActivity(intent);
            }
        } else {
            EditText nametext = (EditText) findViewById(R.id.title_edit);
            EditText descriptiontext = (EditText) findViewById(R.id.description_edit);//обьявляеем поля ввода и свитч
            ContentValues cv = new ContentValues();
            cv.put("name", nametext.getText().toString());
            cv.put("description", descriptiontext.getText().toString());
            cv.put("latitude",latitudeFromMap);
            cv.put("longitude",longitudeFromMap);
            DBHelper dbHelper = new DBHelper(this);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.update("geomarkers", cv, "id = ?", new String[]{Integer.toString(idintent)});
            db.close();
            Log.d("db", "update"+idintent);
            Intent intent = new Intent(this, menu.class);
            startActivity(intent);


        }


    }
}
