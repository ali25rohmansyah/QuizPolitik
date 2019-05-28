package com.example.quizpolitik;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.quizpolitik.ui.activity.ScoreDetail;
import com.example.quizpolitik.ui.activity.SignIn;

public class MainActivity extends AppCompatActivity {
    TextView sl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sl = findViewById(R.id.sl);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this, SignIn.class));
                finish();
            }
        }, 2000L);



    }
}
