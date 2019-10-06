package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    Chronometer chronometer;
    ImageButton btStart,btStop;

    private boolean isResume;
    Handler handler;
    long tMilliSec,tStart,tBuff,tUpdate;
    int sec,min,millisec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chronometer = findViewById(R.id.chronometer);
        btStart = findViewById(R.id.bt_start);
        btStop = findViewById(R.id.bt_stop);

        handler = new Handler();

        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isResume){
                    tStart = SystemClock.uptimeMillis();
                    handler.postDelayed(runnable,0);
                    chronometer.start();
                    isResume = true;
                    btStop.setVisibility(View.GONE);
                    btStart.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
                } else {
                    tBuff+=tMilliSec;
                    handler.removeCallbacks(runnable);
                    chronometer.stop();
                    isResume = false;
                    btStop.setVisibility(View.VISIBLE);
                    btStart.setImageDrawable(getResources().getDrawable(R.drawable.ic_play));
                }
            }
        });

        btStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isResume){
                    btStart.setImageDrawable(getResources().getDrawable(R.drawable.ic_play));
                    tMilliSec = 0;
                    tStart = 0;
                    tBuff = 0;
                    tUpdate = 0;
                    sec = 0;
                    min = 0;
                    millisec = 0;
                    chronometer.setText("00:00:00");
                }
            }
        });

    }

    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            tMilliSec = SystemClock.uptimeMillis() - tStart;
            tUpdate = tBuff + tMilliSec;
            sec = (int) (tUpdate/1000);
            min = sec/60;
            sec = sec%60;
            millisec = (int) (tUpdate%100);
            chronometer.setText(String.format("%02d",min)+":"+String.format("%02d",sec)+":"+String.format("%02d",millisec));
            handler.postDelayed(this,60);
        }
    };
}
