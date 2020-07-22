package com.example.batteryview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class MainActivity extends AppCompatActivity {
    private BatteryView batteryView;
    /** 电量值，用0-100表示 */
    private int powerLevel;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (powerLevel > 100) {
                handler.removeMessages(0);
            } else {
                batteryView.setPowerLevel(powerLevel);
                powerLevel++;

                handler.sendEmptyMessageDelayed(0, 30);
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        batteryView = findViewById(R.id.batteryview);

        handler.sendEmptyMessageDelayed(0, 100);
    }
}