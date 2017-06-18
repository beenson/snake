package com.example.beenson.snake;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    int level = 3;
    int headX = 10, headY = 9;
    int FoodX, FoodY;
    int direction = 2;
    int score, point, highscore = 0;
    int time = 1;
    ArrayList<Integer> mapinfo = new ArrayList<Integer>();
    ArrayList<TextView> textViews = new ArrayList<>();
    Timer timer;
    Timer setcolor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button Up = (Button) findViewById(R.id.up);
        Button Down = (Button) findViewById(R.id.down);
        Button Left = (Button) findViewById(R.id.left);
        Button Right = (Button) findViewById(R.id.right);
        Button Easy = (Button) findViewById(R.id.easy);
        Button Normal = (Button) findViewById(R.id.normal);
        Button Hard = (Button) findViewById(R.id.hard);
        LinearLayout Difficulty = (LinearLayout) findViewById(R.id.difficulty);
        LinearLayout Snake = (LinearLayout) findViewById(R.id.snake);

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

        Snake.setVisibility(View.INVISIBLE);

        for(int i = 0; i < 18 * 24; i++) {
            TextView txtTemp = (TextView) findViewById(getResources().getIdentifier("textview"+i,"id",getPackageName()));
            textViews.add(txtTemp);
        }

        Easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                init();
                printmap();
                timer = new Timer(true);
                timer.schedule(new MyTimerTask(), 0, 800);
                point = 1;
            }
        });

        Normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                init();
                printmap();
                timer = new Timer(true);
                timer.schedule(new MyTimerTask(), 0, 600);
                point = 5;
            }
        });

        Hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                init();
                printmap();
                timer = new Timer(true);
                timer.schedule(new MyTimerTask(), 0, 300);
                point = 10;
            }
        });
    }

    public class MyTimerTask extends TimerTask {
        public void run() {
            update();
            printmap();
        }
    }

    public class MyTimerTask1 extends TimerTask {
        public void run() {
            time = 1 - time;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(time == 0) {
                        textViews.get(FoodY * 24 + FoodX).setTextColor(Color.parseColor("#FF0000"));
                    }
                    else {
                        textViews.get(FoodY * 24 + FoodX).setTextColor(Color.parseColor("#000000"));
                    }
                }
            });
        }
    }

    public String text(int i) {
        if(i == level)
        {
            return "■";
        }
        else {
            switch (i) {
                case 0:
                    return "　";
                case -1:
                    return "─";
                case -2:
                    return "|";
                case -3:
                    return "┌─";
                case -4:
                    return "┐";
                case -5:
                    return "└─";
                case -6:
                    return "┘";
                case -7:
                    return "●";
                default:
                    return "□";
            }
        }
    }

    public void food() {
        do {
            FoodX = (int)(Math.random()* 24);
            FoodY = (int)(Math.random()* 18);
        }while (mapinfo.get(FoodY * 24 + FoodX) != 0);
        mapinfo.set(FoodY * 24 + FoodX, -7);
    }

    public void update() {

        switch (direction) {
            case 1:
                move(0, -1);
                break;
            case 2:
                move(1, 0);
                break;
            case 3:
                move(0, 1);
                break;
            case 4:
                move(-1, 0);
                break;
        }
    }

    public void move(int x, int y) {
        headX = headX + x;
        headY = headY + y;
        int temp = headX + headY * 24;
        int temp1 = mapinfo.get(temp);
        switch (temp1) {
            case -7:
                food();
                level++;
                break;
            case 0:
                for(int i = 0; i < 18 * 24; i++) {
                    int temp2 = mapinfo.get(i);
                    if(temp2 > 0) {
                        mapinfo.set(i, temp2 - 1);
                    }
                }
                break;
            default:
                if(score > highscore) {
                    highscore = score;
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LinearLayout Difficulty = (LinearLayout) findViewById(R.id.difficulty);
                        LinearLayout Snake = (LinearLayout) findViewById(R.id.snake);
                        TextView HighScore = (TextView) findViewById(R.id.high_score);

                        HighScore.setText("High Score:" + highscore);
                        Difficulty.setVisibility(View.VISIBLE);
                        Snake.setVisibility(View.INVISIBLE);
                    }
                });
                timer.cancel();
                break;
        }

        mapinfo.set(temp, level);
    }

    public void printmap() {
        score = (level - 3) * point;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < 24; i++) {
                    for(int j = 0; j < 18; j++){
                        int temp = j * 24 + i;
                        String temp1 = text(mapinfo.get(temp));
                        textViews.get(temp).setText(temp1);
                        if(temp1 != "●")
                        textViews.get(temp).setTextColor(Color.parseColor("#000000"));
                    }
                }
                TextView Score = (TextView) findViewById(R.id.score);
                Score.setText("Score:" + score);
            }
        });
    }

    public void init() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LinearLayout Difficulty = (LinearLayout) findViewById(R.id.difficulty);
                LinearLayout Snake = (LinearLayout) findViewById(R.id.snake);

                Snake.setVisibility(View.VISIBLE);
                Difficulty.setVisibility(View.INVISIBLE);
            }
        });

        level = 3;
        headX = 10;
        headY = 9;
        direction = 2;

        setcolor = new Timer(true);
        setcolor.schedule(new MyTimerTask1(), 0, 1000);

        try {
            for(int i = 0; i < 18 * 24; i++) {
                if(i / 24 == 0 || i / 24 == 17) {
                    mapinfo.set(i, -1);
                }
                else if(i % 24 == 0 || i % 24 == 23) {
                    mapinfo.set(i,-2);
                }
                else {
                    mapinfo.set(i, 0);
                }
            }
        }
        catch (Exception err) {
            for(int i = 0; i < 18 * 24; i++) {
                if(i / 24 == 0 || i / 24 == 17) {
                    mapinfo.add(-1);
                }
                else if(i % 24 == 0 || i % 24 == 23) {
                    mapinfo.add(-2);
                }
                else {
                    mapinfo.add(0);
                }
            }
        }
        mapinfo.set(0, -3);
        mapinfo.set(23, -4);
        mapinfo.set(408, -5);
        mapinfo.set(431, -6);
        mapinfo.set(10 + 9 * 24, 3);
        mapinfo.set(9 + 9 * 24, 2);
        mapinfo.set(8 + 9 * 24, 1);
        food();
    }
}
