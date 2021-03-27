package com.example.drawandunlock;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.Nullable;

//import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class Data extends SQLiteOpenHelper {
//    private static final String END_POINT_X = "end_point_X";
//    private static final String END_POINT_Y = "end_point_Y";

    private final Context context;

    private final String TAG = "DB";
    private String fileName = "Data";
    private String csv = (Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyCsvFile.csv"); // Here csv file name is MyCsvFile.csv


    public Data(@Nullable Context context) {
        super(context, SwipeConstants.DATABASE_NAME, null, SwipeConstants.DATABASE_VERSION);
        this.context = context;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create Table

        String CREATE_SWIPE_TABLE = "CREATE TABLE " + SwipeConstants.TABLE_SWIPES + "("
                + SwipeConstants.START_POINT_X + " FLOAT,"
                + SwipeConstants.START_POINT_Y + " FLOAT,"
                + SwipeConstants.DURATION + " FLOAT,"
                + SwipeConstants.PRESSURE + " FLOAT,"
                + SwipeConstants.END_POINT_X + " FLOAT,"
                + SwipeConstants.END_POINT_Y + " FLOAT);";
        db.execSQL(CREATE_SWIPE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SwipeConstants.TABLE_SWIPES);

        onCreate(db);
    }

    void addSwipe(Swipe swipe, Context context)  {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SwipeConstants.START_POINT_X, swipe.getStartPointX());
        values.put(SwipeConstants.START_POINT_Y, swipe.getStartPointY());
        values.put(SwipeConstants.DURATION, swipe.getDuration());
        values.put(SwipeConstants.PRESSURE, swipe.getPressure());
        values.put(SwipeConstants.END_POINT_X, swipe.getEndPointX());
        values.put(SwipeConstants.END_POINT_Y, swipe.getEndPointY());

        db.insert(SwipeConstants.TABLE_SWIPES, null, values);
        String string = swipe.getStartPointX() + "," + swipe.getStartPointY() + "," + swipe.getDuration() + "," + swipe.getPressure()
        + "," + swipe.getEndPointX() + "," + swipe.getEndPointY();
        try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fos);
            outputStreamWriter.write(string);
            outputStreamWriter.close();
            Log.d(TAG, "File saved successfully");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "Swipe info added to the database successfully");
        Log.d(TAG, String.valueOf(context.getDatabasePath(SwipeConstants.DATABASE_NAME)));
        db.close();

//        convertToCSV();
    }

//    public void convertToCSV()  {
//        CSVWriter writer = null;
//
//        try {
//            File file = new File("res/example.csv");
//            file.setWritable(true);
//            file.setReadable(true);
//            file.getAbsolutePath();
//            FileWriter fw = new FileWriter(file);
//
//            writer = new CSVWriter(new FileWriter(file));
//            List<String[]> data = new ArrayList<String[]>();
//
//            data.add(new String[]{"start_X", "start_Y", "duration", "pressure", "end_X", "end_Y"});
//
//            List<Swipe> list = getSwipeInfo();
//            for (int i = 0; i < list.size(); i++)   {
//                String[] string = toString(list.get(i));
//                data.add(string);
//            }
//
//            writer.writeAll(data); // data is adding to csv
//            writer.close();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }



    private String[] toString(Swipe swipe) {
        String[] stringArr = new String[6];
//        StringBuilder builder = new StringBuilder();
//        builder.append(swipe.getStartPointX());
//        builder.append(swipe.getStartPointY());
//        builder.append(swipe.getDuration());
//        builder.append(swipe.getPressure());
//        builder.append(swipe.getEndPointX());
//        builder.append(swipe.getEndPointY());

        for(int i = 0; i < 6; i++)  {
            stringArr[0] = String.valueOf(swipe.getStartPointX());
            stringArr[1] = String.valueOf(swipe.getStartPointY());
            stringArr[2] = String.valueOf(swipe.getDuration());
            stringArr[3] = String.valueOf(swipe.getPressure());
            stringArr[4] = String.valueOf(swipe.getEndPointX());
            stringArr[5] = String.valueOf(swipe.getEndPointY());
        }

        return stringArr;
    }


    public List<Swipe> getSwipeInfo()   {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Swipe> swipeList =  new ArrayList<>();

        Cursor cursor = db.query(SwipeConstants.TABLE_SWIPES,
                new String[]{
                        SwipeConstants.START_POINT_X,
                        SwipeConstants.START_POINT_Y,
                        SwipeConstants.DURATION,
                        SwipeConstants.PRESSURE,
                        SwipeConstants.END_POINT_X,
                        SwipeConstants.END_POINT_Y},
                null,
                null,
                null,
                null,
                null);

        if(cursor != null)  cursor.moveToFirst();

        if (cursor.moveToFirst())   {
            do {
                Swipe swipe = new Swipe();
                swipe.setStartPointX(Float.parseFloat(cursor.getString(cursor.getColumnIndex(SwipeConstants.START_POINT_X))));
                swipe.setStartPointY(Float.parseFloat(cursor.getString(cursor.getColumnIndex(SwipeConstants.START_POINT_Y))));
                swipe.setDuration(Float.parseFloat(cursor.getString(cursor.getColumnIndex(SwipeConstants.DURATION))));
                swipe.setPressure(Float.parseFloat(cursor.getString(cursor.getColumnIndex(SwipeConstants.PRESSURE))));
                swipe.setEndPointX(Float.parseFloat(cursor.getString(cursor.getColumnIndex(SwipeConstants.END_POINT_X))));
                swipe.setEndPointY(Float.parseFloat(cursor.getString(cursor.getColumnIndex(SwipeConstants.END_POINT_Y))));

                swipeList.add(swipe);
            }while (cursor.moveToNext());
        }
        return swipeList;
    }
}
