package com.example.drawandunlock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SwipeActivity extends AppCompatActivity {
    ImageView imageView;
    ImageDatabase imageDB;

    private long pressTime = -1l;
    private long releaseTime = 1l;
    private long duration = -1l;
    float oldX, oldY;
    float newX, newY;
    private VelocityTracker mVelocityTracker = null;
    private SeekBar seekBar;
    private long startTime, endTime;

    private Swipe swipe;

    private Data db;

    private static final String TAG = "SWIPE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);

        imageDB = new ImageDatabase(this);

        byte[] byteArray = imageDB.getEntry();

        imageView = findViewById(R.id.image);


//        Bundle extras = getIntent().getExtras();
//        byte[] byteArray = extras.getByteArray("picture");
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        imageView.setImageBitmap(bmp);




        seekBar = (SeekBar) findViewById(R.id.seekBar);

        swipe = new Swipe();

        db = new Data(SwipeActivity.this);
        List<Swipe> list = db.getSwipeInfo();
        for (int i = 0; i < list.size(); i++)   {
            Swipe sw = list.get(i);
            Log.d(TAG, swipe.getStartPointX() + swipe.getStartPointY() + "");
        }


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                seekBar.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        int action = event.getAction();
                        if (action == MotionEvent.ACTION_DOWN)    {
                            float x = event.getX();
                            float y = event.getY();
                            float pressure = event.getPressure();
                            swipe.setStartPointX(x);
                            swipe.setStartPointY(y);
                            swipe.setPressure(pressure);
                            startTime = System.currentTimeMillis();
                        }

                        if (action == MotionEvent.ACTION_UP)    {
                            float x = event.getX();
                            float y = event.getY();
                            endTime = System.currentTimeMillis();
                            long duration = endTime - startTime;
                            swipe.setEndPointX(x);
                            swipe.setEndPointY(y);
                            swipe.setDuration(duration);
                            Toast.makeText(SwipeActivity.this, "Password has been recorded", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "start  point: " + swipe.getStartPointX() + " " + swipe.getStartPointY());
                            Log.d(TAG, "Duration and Pressure: " + swipe.getDuration() + " " + swipe.getPressure());
                            Log.d(TAG, "End Points: " + swipe.getEndPointX() + " " + swipe.getEndPointY());
                            if (swipe.getPressure() != 0)   db.addSwipe(swipe, getApplicationContext());
                        }

                        return false;
                    }
                });
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

        });
    }
}