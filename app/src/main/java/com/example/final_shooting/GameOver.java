package com.example.final_shooting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameOver extends AppCompatActivity {

    TextView tvPoints;
    int score;
    // Used to load the 'final_shooting' library on application startup.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameover);
        score = getIntent().getExtras().getInt("score");
        tvPoints = findViewById(R.id.tvPoints);
        tvPoints.setText("" + score);
   }

    public void restart(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("score", score);
        startActivity(intent);
        finish();
    }

    public void exit(View view) {
        finish();
    }
    /**
     * A native method that is implemented by the 'final_shooting' native library,
     * which is packaged with this application.
     */
}