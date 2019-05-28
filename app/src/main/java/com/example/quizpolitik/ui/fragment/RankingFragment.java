package com.example.quizpolitik.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.quizpolitik.R;
import com.example.quizpolitik.common.Common;
import com.example.quizpolitik.interFace.ItemClickListener;
import com.example.quizpolitik.interFace.RankingCallback;
import com.example.quizpolitik.model.QuestionScore;
import com.example.quizpolitik.model.Ranking;
import com.example.quizpolitik.ui.activity.ScoreDetail;
import com.example.quizpolitik.viewHolder.RankingViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RankingFragment extends Fragment {
    View myFragment;

    RecyclerView rankingList;
    LinearLayoutManager layoutManager;
    FirebaseRecyclerAdapter<Ranking, RankingViewHolder> adapter;

    FirebaseDatabase database;
    DatabaseReference questionScore, rankingTable;

    int sum=0;
    int i=0;

    public static  RankingFragment newInstance(){
        RankingFragment rankingFragment = new RankingFragment();
        return rankingFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = FirebaseDatabase.getInstance();
        questionScore = database.getReference("Question_Score");
        rankingTable = database.getReference("Ranking");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.fragment_ranking, container, false);

        //initView
        rankingList = myFragment.findViewById(R.id.rankingList);
        layoutManager = new LinearLayoutManager(getActivity());
        rankingList.setHasFixedSize(true);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        rankingList.setLayoutManager(layoutManager);

        adapter = new FirebaseRecyclerAdapter<Ranking, RankingViewHolder>(
                Ranking.class,
                R.layout.ranking_item,
                RankingViewHolder.class,
                rankingTable.orderByChild("score")
        ) {

            @Override
            protected void populateViewHolder(RankingViewHolder viewHolder, final Ranking model, int position) {

                viewHolder.txtUsername.setText(model.getUsername());
                viewHolder.txtScore.setText(String.valueOf(model.getScore()));
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, Boolean isLongClick) {
                        Intent intent = new Intent(getActivity(), ScoreDetail.class);
                        intent.putExtra("user", model.getUsername());
                        startActivity(intent);
                    }
                });
            }

        };

        adapter.notifyDataSetChanged();
        rankingList.setAdapter(adapter);
        return myFragment;
    }
}
