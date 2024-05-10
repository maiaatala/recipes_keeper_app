package br.com.app.recipeskeeper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "recipes_keeper";
    private static final String TABLE_NAME = "recipes";
    private static final int DATABASE_VERSION = 2;

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_STEPS = "steps";
    private static final String COLUMN_TYPE = "type";
    private static final String COLUMN_LACTOSE = "has_lactose";
    private static final String COLUMN_GLUTEN = "has_gluten";
    private static final String COLUMN_PHOTO = "photo";

    public Database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                "( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                COLUMN_PHOTO + " varchar not null,"+
                COLUMN_NAME + " varchar not null," +
                COLUMN_STEPS + " varchar," +
                COLUMN_TYPE + " varchar," +
                COLUMN_LACTOSE + " boolean," +
                COLUMN_GLUTEN + " boolean )";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    boolean addRecipe(String name, String steps, Uri photo, String type, Boolean lactose, Boolean gluten){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_GLUTEN, gluten);
        cv.put(COLUMN_LACTOSE, lactose);
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_STEPS, steps);
        cv.put(COLUMN_TYPE, type);
        cv.put(COLUMN_PHOTO, photo.toString());

        long result = db.insert(TABLE_NAME,null, cv);
        return result != -1;
    }

    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
}
