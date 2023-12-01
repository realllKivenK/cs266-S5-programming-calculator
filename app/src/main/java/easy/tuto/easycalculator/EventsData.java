package easy.tuto.easycalculator;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EventsData extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "events";
    public static final String _ID = "_id";
    public static final String TIME = "time";
    public static final String SOLUTION = "solution";
    public static final String  RESULT = "result";
    public EventsData(Context ctx){
        super(ctx, "events.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " ("
                + _ID + " INTEGER PRIMARY KEY,"
                + TIME + " INTEGER,"
                + SOLUTION + " TEXT,"
                + RESULT + " TEXT"
                + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS events");
        onCreate(db);
    }
}