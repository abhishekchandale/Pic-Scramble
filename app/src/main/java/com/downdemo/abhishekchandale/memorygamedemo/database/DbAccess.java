package com.downdemo.abhishekchandale.memorygamedemo.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Abhishek
 */
public class DbAccess extends SQLiteOpenHelper {

    Context context_;
    private static String databaseName = "memorypuzzle";
    private static String table_puzzleimage = "puzzleimage";

    public DbAccess(Context context) {
        super(context, databaseName, null, 1);
        context_ = context;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        String puzzleimage_table = "CREATE TABLE IF NOT EXISTS   " + table_puzzleimage + " (mid INTEGER PRIMARY KEY autoincrement, imagetitle TEXT, imageurl TEXT)";
        db.execSQL(puzzleimage_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + table_puzzleimage);
        onCreate(db);
        db.close();
    }

    public void clearPuzzleImages() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + table_puzzleimage);
        db.close();
    }

    public void addPuzzleImages(String imagetitle, String imageurl) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO " + table_puzzleimage + "(imagetitle,imageurl) VALUES('" + imagetitle + "','" + imageurl + "')");
        db.close();
    }


    public Cursor getPuzzleImages() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + table_puzzleimage, null);
        return cursor;
    }
}

