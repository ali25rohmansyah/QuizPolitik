package com.example.quizpolitik.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.quizpolitik.R;
import com.example.quizpolitik.common.Common;
import com.example.quizpolitik.model.Question;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collections;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Playing extends AppCompatActivity implements View.OnClickListener{

    final static long INTERVAL = 1000; // 1 sec
    final static long TIMEOUT = 10000;  // 10 sec
    int progressValue = 10;

    CountDownTimer countDownTimer;

    int index=0, score=0, thisQuestion=0, totalQuestion, correctAnswer;

    FrameLayout frameLayout;
    View QuestionImage, QuestionText;
    TextView txtQuestion, txtScore, txtTotalQuestion;
    Button btnJawabanA, btnJawabanB, btnJawabanC, btnJawabanD;
    TextView Question, txtTimer;
    Button JawabanA, JawabanB, JawabanC, JawabanD, btnYes, btnNo;
    ImageView img;

    LayoutInflater inflater;
    View dialogView;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Georgia.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        setContentView(R.layout.activity_playing);
        Common.questionList.size();

        //Views
        QuestionImage = getLayoutInflater().inflate(R.layout.question_image, frameLayout, false);
        txtQuestion = QuestionImage.findViewById(R.id.txtQuestion);
        img = QuestionImage.findViewById(R.id.img);
        btnJawabanA = QuestionImage.findViewById(R.id.btnJawabanA);
        btnJawabanB = QuestionImage.findViewById(R.id.btnJawabanB);
        btnJawabanC = QuestionImage.findViewById(R.id.btnJawabanC);
        btnJawabanD = QuestionImage.findViewById(R.id.btnJawabanD);
        btnJawabanA.setOnClickListener(this);
        btnJawabanB.setOnClickListener(this);
        btnJawabanC.setOnClickListener(this);
        btnJawabanD.setOnClickListener(this);

        QuestionText = getLayoutInflater().inflate(R.layout.question_text, frameLayout, false);
        Question = QuestionText.findViewById(R.id.Question);
        JawabanA = QuestionText.findViewById(R.id.JawabanA);
        JawabanB = QuestionText.findViewById(R.id.JawabanB);
        JawabanC = QuestionText.findViewById(R.id.JawabanC);
        JawabanD = QuestionText.findViewById(R.id.JawabanD);
        JawabanA.setOnClickListener(this);
        JawabanB.setOnClickListener(this);
        JawabanC.setOnClickListener(this);
        JawabanD.setOnClickListener(this);

        frameLayout = findViewById(R.id.frameLayout);
        txtScore = findViewById(R.id.txtScore);
        txtTotalQuestion = findViewById(R.id.txtTotalQuestion);
        txtTimer = findViewById(R.id.txtTimer);
    }

    @Override
    public void onClick(View v) {

        countDownTimer.cancel();
        progressValue=10;

        if(index<totalQuestion){
            Button clickedButton = (Button)v;
            if(clickedButton.getText().equals(Common.questionList.get(index).getJawabanBenar())){
                //CorrecAnswer
                score  +=10;
                correctAnswer++;
                showQuestion(++index);
            }else{
                //Wrong Answer
                score += 0;
                correctAnswer += 0;
                showQuestion(++index);
            }
            txtScore.setText(String.format("%d",score));
        }

    }

    private void showQuestion(int index) {

        if(index < totalQuestion){

            thisQuestion++;
            txtTotalQuestion.setText(String.format("%d / %d", thisQuestion,totalQuestion));

            if(Common.questionList.get(index).getImage().equals("")){
                frameLayout.removeView(QuestionImage);
                frameLayout.removeView(QuestionText);
                frameLayout.addView(QuestionText);
                Question.setText(Common.questionList.get(index).getPertanyaan());
                JawabanA.setText(Common.questionList.get(index).getJawabanA());
                JawabanB.setText(Common.questionList.get(index).getJawabanB());
                JawabanC.setText(Common.questionList.get(index).getJawabanC());
                JawabanD.setText(Common.questionList.get(index).getJawabanD());

            }else {
                frameLayout.removeView(QuestionText);
                frameLayout.removeView(QuestionImage);
                frameLayout.addView(QuestionImage);
                txtQuestion.setText(Common.questionList.get(index).getPertanyaan());
                Glide.with(getBaseContext())
                        .load(Common.questionList.get(index).getImage())
                        .into(img);
                btnJawabanA.setText(Common.questionList.get(index).getJawabanA());
                btnJawabanB.setText(Common.questionList.get(index).getJawabanB());
                btnJawabanC.setText(Common.questionList.get(index).getJawabanC());
                btnJawabanD.setText(Common.questionList.get(index).getJawabanD());

            }
            countDownTimer.start();
        }else{
            //Final Question
            Intent intent = new Intent(Playing.this, Done.class);
            Bundle dataSend = new Bundle();
            dataSend.putInt("QuestionScore", score);
            dataSend.putInt("Total", totalQuestion);
            dataSend.putInt("CorrectAnswer", correctAnswer);
            intent.putExtras(dataSend);

            startActivity(intent);
            finish();
        }
    }

    private void pauseDialog() {

        android.app.AlertDialog.Builder start = new android.app.AlertDialog.Builder(this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.pause, null);

        start.setTitle("Pause")
                .setView(dialogView)
                .setIcon(R.drawable.hammer)
                .setMessage("Are you sure want to leave ?");

        final android.app.AlertDialog alertDialog = start.create();

        btnYes = dialogView.findViewById(R.id.btnYes);
        btnNo = dialogView.findViewById(R.id.btnNo);

        alertDialog.show();
        countDownTimer.cancel();

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                Intent intent = new Intent(Playing.this, Done.class);
                Bundle dataSend = new Bundle();
                dataSend.putInt("QuestionScore", score);
                dataSend.putInt("Total", totalQuestion);
                dataSend.putInt("CorrectAnswer", correctAnswer);
                intent.putExtras(dataSend);
                startActivity(intent);
                finish();
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                countDownTimer.start();
            }
        });
    }

    @Override
    public void onBackPressed() {
        pauseDialog();
    }

    @Override
    protected void onResume() {
        super.onResume();

        totalQuestion = Common.questionList.size();

        countDownTimer = new CountDownTimer(TIMEOUT,INTERVAL) {
            @Override
            public void onTick(long miniSec) {
                txtTimer.setText(String.valueOf(progressValue));
                progressValue--;
            }

            @Override
            public void onFinish() {
                progressValue=10;
                countDownTimer.cancel();
                showQuestion(++index);
            }
        };

        showQuestion(index);

    }
}
