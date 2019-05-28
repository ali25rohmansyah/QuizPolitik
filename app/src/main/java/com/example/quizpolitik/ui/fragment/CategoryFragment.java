package com.example.quizpolitik.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.example.quizpolitik.R;
import com.example.quizpolitik.common.Common;
import com.example.quizpolitik.interFace.ItemClickListener;
import com.example.quizpolitik.model.Level;
import com.example.quizpolitik.model.Question;
import com.example.quizpolitik.ui.activity.Playing;
import com.example.quizpolitik.viewHolder.LevelViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collections;

public class CategoryFragment extends Fragment {
    View myFragment;

    RecyclerView listLevel;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Level, LevelViewHolder> adapter;

    DatabaseReference table_level = FirebaseDatabase.getInstance().getReference("Level");
    DatabaseReference table_question = FirebaseDatabase.getInstance().getReference("Question");
    DatabaseReference table_user;

    LayoutInflater inflater;
    View dialogView;
    Button btnStart, btnCancel;

    public static CategoryFragment newInstance(){
        CategoryFragment levelFragment = new CategoryFragment();
        return levelFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.fragment_level, container, false);

        listLevel = myFragment.findViewById(R.id.listLevel);
        listLevel.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(container.getContext());
        listLevel.setLayoutManager(layoutManager);

        loadLevel();
        return myFragment;
    }

    private void loadLevel() {
        adapter = new FirebaseRecyclerAdapter<Level, LevelViewHolder>(
                Level.class,
                R.layout.level_item,
                LevelViewHolder.class,
                table_level
        ) {
            @Override
            protected void populateViewHolder(LevelViewHolder viewHolder, final Level model, int position) {
                Glide.with(getActivity())
                        .load(model.getImage())
                        .into(viewHolder.imgLevel);
                viewHolder.txtLevel.setText(model.getName());

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, Boolean isLongClick) {
//                      Toast.makeText(getActivity(), String.format("%s | %s",adapter .getRef(position).getKey(),model.getName()), Toast.LENGTH_SHORT).show();
                        Common.CategoryId = adapter.getRef(position).getKey();
                        Common.CategoryName = model.getName();
                        loadQuestion(Common.CategoryId);
                        startDialog();
                    }
                });
            }
        };
        adapter.notifyDataSetChanged();
        listLevel.setAdapter(adapter);
    }

    private void startDialog() {

        android.app.AlertDialog.Builder start = new android.app.AlertDialog.Builder(getActivity());
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.start, null);

        start.setTitle("Ready ?")
                .setView(dialogView)
                .setIcon(R.drawable.hammer)
                .setMessage("Each question has 7 seconds, if it is not answered it will immediately pass the question.");

        final android.app.AlertDialog alertDialog = start.create();

        btnStart = dialogView.findViewById(R.id.btnStart);
        btnCancel = dialogView.findViewById(R.id.btnCancel);

        alertDialog.show();

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                Intent intent = new Intent(getActivity(), Playing.class);
                startActivity(intent);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    private void loadQuestion(String levelId) {

        table_question.orderByChild("Level").equalTo(levelId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //Clear old question
                if (Common.questionList.size() > 0)
                    Common.questionList.clear();

                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    com.example.quizpolitik.model.Question query = postSnapshot.getValue(Question.class);
                    Common.questionList.add(query);
                }
                //Shuffle Question
                Collections.shuffle(Common.questionList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}

