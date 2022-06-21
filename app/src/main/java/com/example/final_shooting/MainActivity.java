package com.example.final_shooting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.final_shooting.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'final_shooting' library on application startup.
    int score = 0;
    int mode = 1; // 1 = 이지모드, 2 = 하드모드

    static {
        System.loadLibrary("final_shooting");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(getIntent().getExtras() != null) // 첫 화면 인텐트 여부 체크하기
        {
            score = getIntent().getExtras().getInt("score");
            //Toast.makeText(this, "intent", Toast.LENGTH_SHORT).show();
        }
   }

    public void GameStart(View view) {
        Intent intent = new Intent(this, GameStart.class);
        intent.putExtra("score", score);
        intent.putExtra("mode", mode);
        startActivity(intent);
        finish();
    }

    /**
     * A native method that is implemented by the 'final_shooting' native library,
     * which is packaged with this application.
     */
}