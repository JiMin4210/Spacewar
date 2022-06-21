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
        DrawFrame game = new DrawFrame(this);
        //-------------- 스타트 화면에서 불러온 변수 -------------------
        game.score = getIntent().getExtras().getInt("score");
        //---------------------------------------------------------
        setContentView(game);
    }
}