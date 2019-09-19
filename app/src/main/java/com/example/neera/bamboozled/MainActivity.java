package com.example.neera.bamboozled;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.neera.bamboozled.activities.Activity_History;
import com.example.neera.bamboozled.activities.Activity_Instructions;
import com.example.neera.bamboozled.activities.Activity_LR;
import com.example.neera.bamboozled.activities.Activity_Media;
import com.example.neera.bamboozled.activities.Activity_Quiz;

public class MainActivity extends AppCompatActivity {
    Button btnStart, btnLR, btnInstructions, btnHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        startActivity(new Intent(this, Activity_Media.class));

        btnStart = (Button) findViewById(R.id.btnStart);
        btnLR = (Button) findViewById(R.id.btnLR);
        btnInstructions = (Button) findViewById(R.id.btnInstructions);
        btnHistory = (Button) findViewById(R.id.btnHistory);

        View.OnClickListener onButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = null;
                switch (view.getId()) {
                    case R.id.btnStart:
                        i = new Intent(MainActivity.this, Activity_Quiz.class);
                        break;
                    case R.id.btnLR:
                        i = new Intent(MainActivity.this, Activity_LR.class);
                        break;
                    case R.id.btnInstructions:
                        i = new Intent(MainActivity.this, Activity_Instructions.class);
                        break;
                    case R.id.btnHistory:
                        i = new Intent(MainActivity.this, Activity_History.class);
                        break;
                }
                startActivity(i);
            }
        };

        btnStart.setOnClickListener(onButtonClickListener);
        btnLR.setOnClickListener(onButtonClickListener);
        btnInstructions.setOnClickListener(onButtonClickListener);
        btnHistory.setOnClickListener(onButtonClickListener);
    }

}

