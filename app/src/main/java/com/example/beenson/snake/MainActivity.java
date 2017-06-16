package com.example.beenson.snake;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    int headX = 0, headY = 0;
    int direction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button Up = (Button) findViewById(R.id.up);
        Button Down = (Button) findViewById(R.id.down);
        Button Left = (Button) findViewById(R.id.left);
        Button Right = (Button) findViewById(R.id.right);
        Button Start = (Button) findViewById(R.id.start);
        TextView txt = (TextView)findViewById(R.id.textview);
        Timer timer;

        Up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(direction != 3)
                {
                    direction = 1;
                }
            }
        });
        Right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(direction != 4)
                {
                    direction = 2;
                }
            }
        });
        Down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(direction != 1)
                {
                    direction = 3;
                }
            }
        });
        Left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(direction != 2)
                {
                    direction = 4;
                }
            }
        });


        Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer = new Timer(true);
                timer.schedule(new MyTimerTask(), 0, 1000);
            }
        });
    }
    public class MyTimerTask extends TimerTask
    {
        public void run()
        {

        }
    };
}
