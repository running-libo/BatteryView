package com.example.batteryview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class MainActivity extends AppCompatActivity {
    private BatteryView batteryView;
    private int powerLevel;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            batteryView.setPowerLevel(powerLevel%3);
            powerLevel++;
            handler.sendEmptyMessageDelayed(0, 1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        batteryView = findViewById(R.id.batteryview);

        batteryView.setPowerLevel(BatteryView.TYPE_MEDIUM);
        handler.sendEmptyMessageDelayed(0, 1000);
    }
}