package com.example.neera.bamboozled.activities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.neera.bamboozled.Data.Questions;
import com.example.neera.bamboozled.HistoryDB.HistoryDBHelper;
import com.example.neera.bamboozled.HistoryDB.Model.HistoryData;
import com.example.neera.bamboozled.HistoryDB.Tables.HistoryTable;
import com.example.neera.bamboozled.R;
import com.github.lzyzsd.circleprogress.DonutProgress;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Random;

public class Activity_LR extends AppCompatActivity {

    public static ArrayList<HistoryData> HD = new ArrayList<>();

    Button btnChoice1, btnChoice2, btnChoice3, btnChoice4, btnPlayAgain, btnQuit, btn, btnCorrect;
    DonutProgress pbScore;
    TextView tvScore, tvQuestion, tvProgressText;
    Random r;
    Handler handler = new Handler();
    private Questions questions = new Questions();
    private int Counter = 0;
    private int Score = 0;
    private boolean isCorrect = false;
    private String Answer;
    private int QuestionLength = questions.Question.length;
    private ArrayList<Integer> random = new ArrayList<>();
    private ArrayList<Integer> ranQ = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lr);
        for (int i = 0; i < 4; i++) {
            ranQ.add(i);
        }

        r = new Random();

        btnChoice1 = (Button) findViewById(R.id.btnChoice1);
        btnChoice2 = (Button) findViewById(R.id.btnChoice2);
        btnChoice3 = (Button) findViewById(R.id.btnChoice3);
        btnChoice4 = (Button) findViewById(R.id.btnChoice4);
        tvQuestion = (TextView) findViewById(R.id.tvQuestion);
        tvScore = (TextView) findViewById(R.id.tvScore);

        tvScore.setText("Score: " + Score);

        updateQuestion(URandom(QuestionLength));

        final CountDownTimer cdt = new CountDownTimer(300000, 1000) {

            @Override
            public void onTick(long l) {
                if (l > 60000) {
                    tvScore.setText("Time remaining: " + ((l / 60000) + 1) + " mins");
                } else {
                    tvScore.setText("Time remaining: " + l / 1000 + "s");
                }
            }

            @Override
            public void onFinish() {
                random = new ArrayList<>();
                gameOver();
            }
        }.start();

        View.OnClickListener onButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (view.getId()) {
                    case R.id.btnChoice1:
                        if (btnChoice1.getText() == Answer) {
                            Score++;
                            tvScore.setText("Score: " + Score);
                            isCorrect = true;
                        } else {
                            isCorrect = false;
                        }
                        btn = btnChoice1;
                        Counter++;
                        break;
                    case R.id.btnChoice2:
                        if (btnChoice2.getText() == Answer) {
                            Score++;
                            tvScore.setText("Score: " + Score);
                            isCorrect = true;
                        } else {
                            isCorrect = false;
                        }
                        btn = btnChoice2;
                        Counter++;
                        break;
                    case R.id.btnChoice3:
                        if (btnChoice3.getText() == Answer) {
                            Score++;
                            tvScore.setText("Score: " + Score);
                            isCorrect = true;
                        } else {
                            isCorrect = false;
                        }
                        btn = btnChoice3;
                        Counter++;
                        break;
                    case R.id.btnChoice4:
                        if (btnChoice4.getText() == Answer) {
                            Score++;
                            tvScore.setText("Score: " + Score);
                            isCorrect = true;
                        } else {
                            isCorrect = false;
                        }
                        btn = btnChoice4;
                        Counter++;
                        break;
                }
                setButton(btn, isCorrect);
                handler.postDelayed(new Runnable() {
                    public void run() {
                        if (Counter < QuestionLength) {
                            if (isCorrect) {
                                btn.setBackground(getResources().getDrawable(R.drawable.button_normal));
                                updateQuestion(URandom(QuestionLength));
                            } else {
                                random = new ArrayList<>();
                                btn.setBackground(getResources().getDrawable(R.drawable.button_normal));
                                cdt.cancel();
                                gameOver();
                            }
                        } else if (Counter > QuestionLength) {
                            gameOver();
                        } else {
                            random = new ArrayList<>();
                            btn.setBackground(getResources().getDrawable(R.drawable.button_normal));
                            cdt.cancel();
                            gameOver();
                        }


                    }
                }, 500);
            }
        };

        btnChoice1.setOnClickListener(onButtonClickListener);
        btnChoice2.setOnClickListener(onButtonClickListener);
        btnChoice3.setOnClickListener(onButtonClickListener);
        btnChoice4.setOnClickListener(onButtonClickListener);

    }

    private void updateQuestion(int num) {
        btnChoice1.setClickable(true);
        btnChoice2.setClickable(true);
        btnChoice3.setClickable(true);
        btnChoice4.setClickable(true);
        tvQuestion.setText(questions.getQuestion(num));
        Collections.shuffle(ranQ);
        btnChoice1.setText(questions.getChoices(num, ranQ.get(0)));
        btnChoice2.setText(questions.getChoices(num, ranQ.get(1)));
        btnChoice3.setText(questions.getChoices(num, ranQ.get(2)));
        btnChoice4.setText(questions.getChoices(num, ranQ.get(3)));
        Answer = questions.getAnswer(num);
    }

    private void gameOver() {

        updateHistory("" + Score);

        setContentView(R.layout.view_gameover);
        btnPlayAgain = (Button) findViewById(R.id.btnPlayAgain);
        btnQuit = (Button) findViewById(R.id.btnQuit);
        pbScore = (DonutProgress) findViewById(R.id.pbScore);
        tvProgressText = (TextView) findViewById(R.id.tvProgressText);
        tvProgressText.setText("Score:\n" + Score);
        float progress = ((float) Score / (float) QuestionLength) * (100);
        pbScore.setProgress((int) progress);

        btnPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Activity_LR.class);
                startActivity(i);
                finish();
            }
        });

        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setButton(Button btn, boolean isCorrect) {
        btnChoice1.setClickable(false);
        btnChoice2.setClickable(false);
        btnChoice3.setClickable(false);
        btnChoice4.setClickable(false);
        if (isCorrect) {
            btn.setBackground(getResources().getDrawable(R.drawable.button_correct));
        } else {
            btn.setBackground(getResources().getDrawable(R.drawable.button_wrong));
            if (btnChoice1.getText().equals(Answer)) {
                btnCorrect = btnChoice1;
            } else if (btnChoice2.getText().equals(Answer)) {
                btnCorrect = btnChoice2;
            } else if (btnChoice3.getText().equals(Answer)) {
                btnCorrect = btnChoice3;
            } else if (btnChoice4.getText().equals(Answer)) {
                btnCorrect = btnChoice4;
            }
            btnCorrect.setBackground(getResources().getDrawable(R.drawable.button_correct));
            handler.postDelayed(new Runnable() {
                public void run() {
                    btnCorrect.setBackground(getResources().getDrawable(R.drawable.button_normal));
                }
            }, 500);
        }
    }

    private int URandom(int length) {
        int num = r.nextInt(length);
        for (int i = 0; i < random.size(); i++) {
            if (random.get(i) == num) {
                num = r.nextInt(length);
                i = -1;
            }
        }
        random.add(num);
        return num;
    }

    private void updateHistory(String Score) {
        final SQLiteDatabase historyDB = new HistoryDBHelper(this).getWritableDatabase();
        Date curDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
        String Date = dateFormat.format(curDate);

        Activity_History.history = HistoryTable.getHistory(historyDB);

        HistoryTable.insertHistory(
                historyDB,
                new HistoryData(
                        Score,
                        Date
                )
        );

        Activity_History.history = HistoryTable.getHistory(historyDB);
    }
}