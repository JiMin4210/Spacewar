package com.example.final_shooting;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class GameOver extends AppCompatActivity {

    TextView Points;
    int score;
    int shotname;
    Timer t = new Timer();
    // Used to load the 'final_shooting' library on application startup.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameover);
        score = getIntent().getExtras().getInt("score");
        shotname = getIntent().getExtras().getInt("shotname");
        Points = findViewById(R.id.Points);
        Points.setText("" + score);
        TimerTask task = new TimerTask() { // 스위치 관련 코드

            Handler mHandler = new Handler();

            @Override
            public void run() {
                mHandler.postDelayed(()->{

                    int value;
                    value = DeviceOpen();

                    if(value != -1)
                        value = ReceivePushSwitchValue();
                    if(value != -1)
                        DeviceClose();

                    if(value == 1) {
                        restart(Points);
                    }
                    else if(value == 4)
                    {
                        exit(Points);
                    }
                }, 100);
            }
        };
        t.schedule(task, 200, 200);
   }

    public void restart(View view) {
        t.cancel();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("score", score);
        intent.putExtra("shotname", shotname);
        startActivity(intent);
        finish();
    }

    public void exit(View view) {
        t.cancel();
        finish();
    }
    /**
     * A native method that is implemented by the 'final_shooting' native library,
     * which is packaged with this application.
     */
    public native int DeviceOpen(); // 이건 핸들러로 하기에 엑티비티 다시보기
    public native int DeviceClose();
    public native int ReceivePushSwitchValue();
}