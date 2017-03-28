package ru.kanifol_group.geomarkers;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
        Integer[] MassRoboto = new Integer[]{R.id.title_text, R.id.description_text, R.id.map_text, R.id.description_edit, R.id.title_edit};
        for (Integer i = 0; i < MassRoboto.length; i++) {
            TextView lay = (TextView) findViewById(MassRoboto[i]);
            lay.setTypeface(Typeface.createFromAsset(getAssets(), "Roboto.ttf"));
        }

        startStatement();// Заполнение полей
    }

    private void startStatement(){
        int idintent = getIntent().getIntExtra("id", 0);
        DBHelper dbHelper = new DBHelper(this);
        String[] allNotes = dbHelper.getDatabyId(idintent);
            //тут характеристики для каждого элемента
            if (idintent!=0) {
                //Ввод названия
                ((EditText) findViewById(R.id.title_edit)).setText(allNotes[0]);
                //Ввод описания
                ((EditText) findViewById(R.id.description_edit)).setText(allNotes[1]);
            }
        }

    public void okButton(View v){
        final EditText nametext = (EditText) findViewById(R.id.title_edit);
        final EditText descriptiontext = (EditText) findViewById(R.id.description_edit);//обьявляеем поля ввода

        int idintent = getIntent().getIntExtra("id",0);// Получение айди элемента на который было совершено покушение

        if (idintent == 0) {

            if (nametext.getText().toString().length() < 1) {
                Toast toast = Toast.makeText(getApplicationContext(), //проверяем полноту 1-ого поля ввода
                        "Заполните первое поле", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                //подготавливаем данные к отправке
                DBHelper updater = new DBHelper(this);
                updater.appendBase(nametext.getText().toString(), descriptiontext.getText().toString(), latitudeFromMap, longitudeFromMap, 30, 1);
                Intent intent = new Intent(this, menu.class);
                startActivity(intent);
            }
        } else {
            DBHelper updater = new DBHelper(this);
            updater.updateDatabase(idintent, nametext.getText().toString(), descriptiontext.getText().toString(), latitudeFromMap, longitudeFromMap, 30, 1);
            Intent intent = new Intent(this, menu.class);
            startActivity(intent);


        }


    }
    public void startMap(View v){
        Intent mapint = new Intent(this, map.class);
        startActivity(mapint);
    }
    public void delete(View v){
        int idintent = getIntent().getIntExtra("id",0);
        DBHelper deleter = new DBHelper(this);
        deleter.deleteMarker(idintent);
        Intent intent = new Intent(this, menu.class);
        startActivity(intent);

    }
}
