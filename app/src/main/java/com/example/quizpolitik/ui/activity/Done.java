package com.example.quizpolitik.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quizpolitik.R;
import com.example.quizpolitik.common.Common;
import com.example.quizpolitik.interFace.RankingCallback;
import com.example.quizpolitik.model.QuestionScore;
import com.example.quizpolitik.model.Ranking;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collections;

import io.paperdb.Paper;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Done extends AppCompatActivity {

    TextView txtScore, txtCorrectAnswer;
    ImageView left;
    Button btnTry, btnHome;
    DatabaseReference rankingTable, questionScore;

    int score, cAnswer;
    int sum=0, i=0;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Arkhip_font.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        setContentView(R.layout.activity_done);


        rankingTable = FirebaseDatabase.getInstance().getReference("Ranking");
        questionScore = FirebaseDatabase.getInstance().getReference("Question_Score");

        //Views
        txtScore = findViewById(R.id.txtScore);
        txtCorrectAnswer = findViewById(R.id.txtCorrectAnswer);
        btnTry = findViewById(R.id.btnTry);
        btnHome = findViewById(R.id.btnHome);
        left = findViewById(R.id.left);

        btnTry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.shuffle(Common.questionList);
                startActivity(new Intent(Done.this,Playing.class));
                finish();
            }
        });

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Done.super.onBackPressed();
            }
        });

        //Get data from playing to display score and correct answer
        Bundle extra = getIntent().getExtras();
        if (extra != null){
            score = extra.getInt("QuestionScore");
            cAnswer = extra.getInt("CorrectAnswer");

            txtScore.setText(String.format("QuestionScore : %d", score));
            txtCorrectAnswer.setText(String.format("Passed : %d", cAnswer));

            //upload score to Rangking DB
            questionScore.child(String.format( "%s_%s", Common.username, Common.CategoryId))
                    .setValue(new QuestionScore(String.format("%s_%s", Common.username, Common.CategoryId)
                    ,Common.username
                    ,String.valueOf(score)
                    ,Common.CategoryId
                    ,Common.CategoryName));

            btnHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Done.this, BaseActivity.class);
                    startActivity(intent);
                }
            });
        }

        updateScore(Common.username, new RankingCallback<Ranking>() {
            @Override
            public void callBack(Ranking ranking) {
                rankingTable.child(ranking.getUsername())
                        .setValue(ranking);
            }
        });

    }

    private void updateScore(final String username, final RankingCallback<Ranking> callback) {

        questionScore.orderByChild("user").equalTo(username)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot data:dataSnapshot.getChildren()){
                            QuestionScore query = data.getValue(QuestionScore.class);
                            sum+=Integer.parseInt(query.getScore());
                            i++;
                        }
                        Ranking ranking = new Ranking(sum, username);
                        callback.callBack(ranking);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
}
