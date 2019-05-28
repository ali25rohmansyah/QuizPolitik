package com.example.quizpolitik.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.example.quizpolitik.R;
import com.example.quizpolitik.model.QuestionScore;
import com.example.quizpolitik.viewHolder.ScoreDetailViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ScoreDetail extends AppCompatActivity {

    RecyclerView scoreList;
    LinearLayoutManager layoutManager;
    FirebaseRecyclerAdapter<QuestionScore, ScoreDetailViewHolder> adapter;

    FirebaseDatabase database;
    DatabaseReference questionScore;

    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_detail);

        database = FirebaseDatabase.getInstance();
        questionScore = database.getReference("Question_Score");

        //initView
        scoreList = findViewById(R.id.scoreList);
        layoutManager = new LinearLayoutManager(this);
        scoreList.setHasFixedSize(true);
        scoreList.setLayoutManager(layoutManager);

        if(getIntent() != null){
            user = getIntent().getStringExtra("user");
        }
        if(!user.isEmpty()){
            loadScoreDetail(user);

        }
    }

    private void loadScoreDetail(String user) {

        adapter = new FirebaseRecyclerAdapter<QuestionScore, ScoreDetailViewHolder>(
                QuestionScore.class,
                R.layout.score_detail_item,
                ScoreDetailViewHolder.class,
                questionScore.orderByChild("user").equalTo(user)
        ) {

            @Override
            protected void populateViewHolder(ScoreDetailViewHolder viewHolder, final QuestionScore model, int position) {

                viewHolder.txtCategoryName.setText(model.getCategoryName());
                viewHolder.txtScore.setText(model.getScore());
            }

        };
        adapter.notifyDataSetChanged();
        scoreList.setAdapter(adapter);
    }
}
