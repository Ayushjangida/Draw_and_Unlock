package com.example.drawandunlock;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ImageDatabase extends SQLiteOpenHelper {

    public ImageDatabase(@Nullable Context context) {
        super(context, ImageDataConstants.DATABASE_NAME, null, ImageDataConstants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_IMAGE_TABLE = "CREATE TABLE " + ImageDataConstants.TABLE_IMAGE + "("+
                ImageDataConstants.KEY_NAME + " TEXT," +
                ImageDataConstants.KEY_IMAGE + " BLOB);";

        db.execSQL(CREATE_IMAGE_TABLE);

    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + ImageDataConstants.TABLE_IMAGE);

        // create new table
        onCreate(db);
    }

    public void addEntry( String name, byte[] image) throws SQLiteException {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cv = new  ContentValues();
        cv.put(ImageDataConstants.KEY_NAME,    name);
        cv.put(ImageDataConstants.KEY_IMAGE,   image);
        database.insert( ImageDataConstants.TABLE_IMAGE, null, cv );
    }

    public byte[] getEntry()  {
        byte[] array;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(ImageDataConstants.TABLE_IMAGE,
                new String[]{
                        ImageDataConstants.KEY_NAME,
                        ImageDataConstants.KEY_IMAGE},
                null,
                null,
                null,
                null,
                null);

        if (cursor != null)     cursor.moveToFirst();
        if (cursor.moveToFirst())   {
            do {
                return cursor.getBlob(1);
            }while (cursor.moveToNext());
        }

       return cursor.getBlob(1);
    }

    public boolean getSize()    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(ImageDataConstants.TABLE_IMAGE,
                new String[]{
                        ImageDataConstants.KEY_NAME,
                        ImageDataConstants.KEY_IMAGE},
                null,
                null,
                null,
                null,
                null);

        if (cursor != null)     return true;

        return false;
    }
}
