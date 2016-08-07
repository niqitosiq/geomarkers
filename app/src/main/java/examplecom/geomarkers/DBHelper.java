package examplecom.geomarkers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


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
                + "latitude double,"
                + "longitude double,"
                + "signal integer" + ");");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS + TABLE_NAME");
        onCreate(db);                                       //метод обновления при устаревании версии (фактически не нужен)
    }
}
