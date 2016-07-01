package examplecom.geomarkers;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DataBaseClass extends SQLiteOpenHelper {
    private static final String DATABASE_NAME_TABLE = "geomarkers.db";
    private static final String MARKER_NAME_COLUMN = "marker";
    private static final String DESCRIPTION_COLUMN = "deskription";
    private static final String GEOLOCATION_COLUMN = "location";
    private static final String SIGNAL_COLUMN = "yorn";
    private static final int DATABASE_VERSION = 1;
    public DataBaseClass(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME_TABLE, null, DATABASE_VERSION);
    }

    public DataBaseClass(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE + DATABASE_NAME_TABLE(_id INTEGER PRIMARY KEY AUTOINCREMENT,MARKER_NAME_COLUMN TEXT, DESCRIPTION_COLUMN TEXT, GEOLOCATION_COLUMN TEXT, SIGNAL_COLUMN INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tableName");
        onCreate(db);
        }

    public String read(SQLiteDatabase db){


        return null;
    }
    public void write(SQLiteDatabase db){



    }

}

