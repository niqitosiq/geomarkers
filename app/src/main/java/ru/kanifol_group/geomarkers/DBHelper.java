package ru.kanifol_group.geomarkers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by danila on 26.03.17.
 */

public class DBHelper extends SQLiteOpenHelper{
    private SQLiteDatabase geomarkersDatabase;
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

        geomarkersDatabase = db;

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int v1, int v2){
        db.execSQL("DROP TABLE IF EXISTS + TABLE_NAME");
        onCreate(db);
    }


    public String[][] getListMarkers(){
        String[][] answer = {new String[longOfAnswerMassive],new String[longOfAnswerMassive],new String[longOfAnswerMassive]};
        Cursor readebleDB = geomarkersDatabase.rawQuery("SELECT id,name,description FROM `geomarkers`",null);
        int idIndex = readebleDB.getColumnIndex("id");
        int nameIndex = readebleDB.getColumnIndex("name");
        int descriptionIndex = readebleDB.getColumnIndex("description");
        int j=0;
        do{
            answer[0][j]=String.valueOf(readebleDB.getInt(idIndex));
            answer[1][j]=readebleDB.getString(nameIndex);
            answer[2][j]=readebleDB.getString(descriptionIndex);
            j++;
        }while(readebleDB.moveToNext());

        return(answer); //возвращает двумерный массив подобный следующему [["1","2"],["купить кофе","блабла"],["описание1","описание2"]]
    }


    public Cursor getMarkerInfo(int id){
        Cursor readebleDB = geomarkersDatabase.rawQuery("SELECT * FROM `geomarkers` WHERE id="+id,null);
        return(readebleDB); //возвращается Cursor, который ПАРСИТСЯ подобно в методе DBHelper.getListMarkers()
    }


    public void updateDatabase(int id,String name,String description,double latitude,double longitude,int radius,int signal){ //для обновления маркера вызывать этот метод передовая всю информацию о маркере(изменяется маркер с id = передоваемому)

    geomarkersDatabase.execSQL("UPDATE `geomarkers` SET name='"+name
            +"', description='"+description
            +"', latitude="+latitude
            +", longitude="+longitude
            +", radius="+radius
            +", signal="+signal
            +"WHERE `geomarkers`.id="+id);

    }


    public void deleteMarker(int id){ //для удаления маркера вызывать этот метод передовая в него id удаляемого маркера
    geomarkersDatabase.execSQL("DELETE FROM `geomarkers` WHERE `geomarkers`.id="+id);
    }


}
