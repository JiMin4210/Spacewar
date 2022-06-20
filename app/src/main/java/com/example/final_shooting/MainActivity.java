package com.example.final_shooting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.final_shooting.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'final_shooting' library on application startup.
    static {
        System.loadLibrary("final_shooting");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
   }

    public void GameStart(View view) {
        Intent it = new Intent(this, GameStart.class);
        startActivity(it);
        finish();
    }

    /**
     * A native method that is implemented by the 'final_shooting' native library,
     * which is packaged with this application.
     */
}