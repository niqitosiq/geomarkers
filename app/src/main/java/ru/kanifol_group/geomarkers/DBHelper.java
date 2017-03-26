package ru.kanifol_group.geomarkers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by danila on 26.03.17.
 */

public class DBHelper extends SQLiteOpenHelper{
    private SQLiteDatabase geomarkersReadableDatabase = getReadableDatabase();

    private SQLiteDatabase geomarkersWritableDatabase = getWritableDatabase();

    private int longOfAnswerMassive = 128;


    public DBHelper(Context context){
        super(context, "database", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db){

        db.execSQL("create table geomarkers ("
                + "id integer primary key autoincrement,"
                + "name text,"
                + "description text,"
                + "latitude double,"
                + "longitude double,"
                + "radius integer,"
                + "signal integer" + ");");

        geomarkersWritableDatabase = db;

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int v1, int v2){
        db.execSQL("DROP TABLE IF EXISTS + TABLE_NAME");
        onCreate(db);
    }


    public String[][] getListMarkers(){
        String[][] answer = new String[longOfAnswerMassive][3];
        String str = "SELECT id,name,description FROM `geomarkers`";
        Cursor readebleDB = geomarkersReadableDatabase.rawQuery(str,null);
        int idIndex = readebleDB.getColumnIndex("id");
        int nameIndex = readebleDB.getColumnIndex("name");
        int descriptionIndex = readebleDB.getColumnIndex("description");
        /*
        int latitude = readebleDB.getColumnIndex("latitude");
        int longitude = readebleDB.getColumnIndex("longitude");
        int radius = readebleDB.getColumnIndex("radius"); // раскомитить как нада буент
        int signal = readebleDB.getColumnIndex("signal");
        */
        int j=0;
        while(readebleDB.moveToNext()) {
            answer[j][0] = String.valueOf(readebleDB.getInt(idIndex));
            answer[j][1] = readebleDB.getString(nameIndex);
            answer[j][2] = readebleDB.getString(descriptionIndex);
            j++;
        }
        return(answer); //возвращает двумерный массив подобный следующему [["айди1", "название1", "описание1"],["айди2", "название2", "описание2"],["айди3", "название3", "описание3"]]
    }


    public Cursor getMarkerInfo(int id){
        Cursor readebleDB = geomarkersReadableDatabase.rawQuery("SELECT * FROM `geomarkers` WHERE id="+id,null);
        return(readebleDB); //возвращается Cursor, который ПАРСИТСЯ подобно в методе DBHelper.getListMarkers()
    }


    public void updateDatabase(int id,String name,String description,double latitude,double longitude,int radius,int signal){ //для обновления маркера вызывать этот метод передовая всю информацию о маркере(изменяется маркер с id = передоваемому)

        geomarkersWritableDatabase.execSQL("UPDATE `geomarkers` SET name='"+name
            +"', description='"+description
            +"', latitude="+latitude
            +", longitude="+longitude
            +", radius="+radius
            +", signal="+signal
            +" WHERE `geomarkers`.id="+id);

    }

    public void deleteMarker(int id){ //для удаления маркера вызывать этот метод передовая в него id удаляемого маркера
        geomarkersWritableDatabase.execSQL("DELETE FROM `geomarkers` WHERE `geomarkers`.id="+id);
    }


}
