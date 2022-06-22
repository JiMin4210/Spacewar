package com.example.final_shooting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.final_shooting.databinding.ActivityMainBinding;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'final_shooting' library on application startup.
    int score = 0; // 처음 스코어 설정
    int mode = 2; // 1 = 이지모드, 2 = 하드모드
    //int music = 0; // 배경음악 플래그
    int shotname = 1; // 기본 총
    int skinprice1 = 10; // 1번 총 스킨 가격
    int skinprice2 = 100; // 2번 총 스킨 가격
    int skinprice3 = 1000; // 3번 총 스킨 가격
    Timer t = new Timer();

    static {
        System.loadLibrary("final_shooting");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton imageButton = (ImageButton) findViewById(R.id.imageButton2);

        if(getIntent().getExtras() != null) // 첫 화면 인텐트 여부 체크하기
        {
            score = getIntent().getExtras().getInt("score");
            shotname = getIntent().getExtras().getInt("shotname"); // 총알 종류가 계속 이어지게함
            //Toast.makeText(this, "intent", Toast.LENGTH_SHORT).show();
        }
        ReceiveFndValue(String.valueOf(score)); // 처음화면에서 FND 점수 갱신
        Toast.makeText(MainActivity.this, "현재 모드 : "+String.valueOf(mode) +"\n1번 스위치 이지모드, 2번 스위치 하드모드 변경\n4,5,6번 스위치로 총알 구매 가능", Toast.LENGTH_LONG).show();
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
                        mode = 1; // 이지모드 버튼
                        Toast.makeText(MainActivity.this, "1번 스위치 이지모드, 총알 4발, 몹 느림, 몹 체력 낮음", Toast.LENGTH_SHORT).show();
                    }
                    else if(value == 2) {
                        mode = 2; // 하드모드 버튼
                        Toast.makeText(MainActivity.this, "2번 스위치 하드모드, 총알 2발, 몹 빠름, 몹 체력 높음", Toast.LENGTH_SHORT).show();
                    }
                    else if(value == 4)
                    {
                        GameStart(imageButton);
                    }
                    else if(value == 8) {
                        if(shotname == 1)
                        {
                            Toast.makeText(MainActivity.this, "이미 동일한 총알을 장착중입니다.", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            if(score >= skinprice1) {
                                score -= skinprice1;
                                shotname = 1;
                                Toast.makeText(MainActivity.this, String.valueOf(shotname) + "번 총알 구매 완료", Toast.LENGTH_SHORT).show();
                                ReceiveFndValue(String.valueOf(score));
                            }
                            else
                                Toast.makeText(MainActivity.this, "점수가 "+String.valueOf(skinprice1 -score) +"점 모자랍니다." , Toast.LENGTH_SHORT).show();
                        }
                    }
                    else if(value == 16) {
                        if(shotname == 2)
                        {
                            Toast.makeText(MainActivity.this, "이미 동일한 총알을 장착중입니다.", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            if(score >= skinprice2) {
                                score -= skinprice2;
                                shotname = 2;
                                Toast.makeText(MainActivity.this, String.valueOf(shotname) + "번 총알 구매 완료", Toast.LENGTH_SHORT).show();
                                ReceiveFndValue(String.valueOf(score));
                            }
                            else
                                Toast.makeText(MainActivity.this, "점수가 "+String.valueOf(skinprice2 -score) +"점 모자랍니다." , Toast.LENGTH_SHORT).show();
                        }
                    }
                    else if(value == 32) {
                        if(shotname == 3)
                        {
                            Toast.makeText(MainActivity.this, "이미 동일한 총알을 장착중입니다.", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            if(score >= skinprice3) {
                                score -= skinprice3;
                                shotname = 3;
                                Toast.makeText(MainActivity.this, String.valueOf(shotname) + "번 총알 구매 완료", Toast.LENGTH_SHORT).show();
                                ReceiveFndValue(String.valueOf(score));
                            }
                            else
                                Toast.makeText(MainActivity.this, "점수가 "+String.valueOf(skinprice3 -score) +"점 모자랍니다." , Toast.LENGTH_SHORT).show();
                        }
                    }


                }, 100);

            }
        };
        t.schedule(task, 200, 200);
   }

    public void GameStart(View view) {
        t.cancel(); // 스위치 타이머 종료
        Intent intent = new Intent(this, GameStart.class);
        intent.putExtra("score", score);
        intent.putExtra("mode", mode);
        intent.putExtra("shotname", shotname);
        startActivity(intent);
        finish();
    }
    public void infoshop(View view){
        Toast.makeText(this, "총알 스킨을 사고싶다면 4,5,6버튼중 하나를 누르세요", Toast.LENGTH_SHORT).show();
    }

    public native int DeviceOpen();
    public native int DeviceClose();
    public native int ReceivePushSwitchValue();
    public native int ReceiveFndValue(String a);
    /**
     * A native method that is implemented by the 'final_shooting' native library,
     * which is packaged with this application.
     */
}