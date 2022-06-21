package com.example.final_shooting;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.final_shooting.databinding.ActivityMainBinding;

public class GameStart extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //-------------- 스타트 화면에서 불러온 변수 -------------------
        int mode = getIntent().getExtras().getInt("mode");
        int score = getIntent().getExtras().getInt("score");
        //Toast.makeText(this, String.valueOf(mode), Toast.LENGTH_SHORT).show();
        //---------------------------------------------------------
        DrawFrame game = new DrawFrame(this, mode);
        game.score = score;
        game.setfndscore(); // 이전 스코어를 넣어줬기에 바로 출력시켜준다.
        setContentView(game);
    }
}