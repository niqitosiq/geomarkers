package examplecom.geomarkers;


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
        import com.google.android.gms.maps.*;

public class SecondActivity extends AppCompatActivity {
    int id = 0;

    GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        Integer[] MassIntro = new Integer[]{R.id.mainText};
        for (Integer i = 0; i < MassIntro.length; i++) {
            TextView lay = (TextView) findViewById(MassIntro[i]);
            lay.setTypeface(Typeface.createFromAsset(getAssets(), "Intro.otf"));
        }
        Integer[] MassRoboto = new Integer[]{R.id.title_text, R.id.description_text, R.id.map_text, R.id.alarm_text};
        for (Integer i = 0; i < MassRoboto.length; i++) {
            TextView lay = (TextView) findViewById(MassRoboto[i]);
            lay.setTypeface(Typeface.createFromAsset(getAssets(), "Roboto.ttf"));
        }
        TextView deletebutton = (TextView)findViewById(R.id.deletebutton);
        deletebutton.setClickable(false);


        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor cursor = db.query("geomarkers", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            int columnIdIndex = cursor.getColumnIndex("id");
            int columnNameIndex = cursor.getColumnIndex("name");
            int columnDescriptionIndex = cursor.getColumnIndex("description");
            int columnGeolocationIndex = cursor.getColumnIndex("geolocation");
            int columnSignalIndex = cursor.getColumnIndex("signal");
            int idintent = getIntent().getIntExtra("id", 0);
            do {
                id = cursor.getInt(columnIdIndex);
                if (id==idintent){
                    String name = cursor.getString(columnNameIndex);
                    String description = cursor.getString(columnDescriptionIndex);
                    int signalint = cursor.getInt(columnSignalIndex);
                    boolean signal = false;
                    if(signalint==1){signal = true;}
                    TextView namev = (TextView)findViewById(R.id.title_edit);
                    TextView descv = (TextView)findViewById(R.id.description_edit);
                    Switch switchv = (Switch)findViewById(R.id.alarm_switch);
                    namev.setText(name);
                    descv.setText(description);
                    switchv.setChecked(signal);
                    deletebutton.setClickable(true);

                }
                else{
                    db.close();
                }
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();


    }
    public  void delete(View v){
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("geomarkers","id ="+id,null);
        db.close();
        Log.d("db", "delete");
        Intent intObj = new Intent(this, MainLayoutActivity.class);
        startActivity(intObj);

    }
    public void comeback(View v){

        Intent intObj = new Intent(this, MainLayoutActivity.class);
        startActivity(intObj);

    }




    public void okButton(View v){
        int idintent = getIntent().getIntExtra("id",0);
        if (idintent == 0) {

            ContentValues cv = new ContentValues();
            EditText nametext = (EditText) findViewById(R.id.title_edit);
            EditText descriptiontext = (EditText) findViewById(R.id.description_edit);//обьявляеем поля ввода и свитч
            Switch sw = (Switch) findViewById(R.id.alarm_switch);
            if (nametext.getText().toString().length() < 1) {
                Toast toast = Toast.makeText(getApplicationContext(), //проверяем полность 1-ого поля ввода
                        "Заполните первое поле", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                cv.put("name",nametext.getText().toString());
                //подготавливаем данные к отправке
                cv.put("description", descriptiontext.getText().toString());
                cv.put("signal", sw.isChecked());
                DBHelper dbHelper = new DBHelper(this);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.insert("geomarkers", null, cv);
                db.close();
                Log.d("db","insert");
                Intent intent = new Intent(this, MainLayoutActivity.class);
                startActivity(intent);
            }
        }
        else {
            EditText nametext = (EditText) findViewById(R.id.title_edit);
            EditText descriptiontext = (EditText) findViewById(R.id.description_edit);//обьявляеем поля ввода и свитч
            Switch sw = (Switch) findViewById(R.id.alarm_switch);
            ContentValues cv = new ContentValues();
            cv.put("name", nametext.getText().toString());
            cv.put("description", descriptiontext.getText().toString());
            cv.put("signal", sw.isChecked());
            DBHelper dbHelper = new DBHelper(this);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.update("geomarkers", cv, "id = ?", new String[]{Integer.toString(idintent)});
            db.close();
            Log.d("db", "update");
            Intent intent = new Intent(this, MainLayoutActivity.class);
            startActivity(intent);


        }
    }


}