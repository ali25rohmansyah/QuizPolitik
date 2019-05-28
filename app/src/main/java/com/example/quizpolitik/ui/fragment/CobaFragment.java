package com.example.quizpolitik.ui.fragment;


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
import android.widget.TextView;

import com.example.quizpolitik.R;
import com.example.quizpolitik.common.Common;
import com.example.quizpolitik.interFace.ItemClickListener;
import com.example.quizpolitik.interFace.RankingCallback;
import com.example.quizpolitik.model.QuestionScore;
import com.example.quizpolitik.model.Ranking;
import com.example.quizpolitik.model.User;
import com.example.quizpolitik.viewHolder.RankingViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class CobaFragment extends Fragment {
    View myFragment;

    TextView score;

    public CobaFragment() {
    }


    public static  CobaFragment newInstance(){
        CobaFragment cobaFragment = new CobaFragment();
        return cobaFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.fragment_coba, container, false);

        //initView
        score = myFragment.findViewById(R.id.asdfg);
        Bundle extra = getActivity().getIntent().getExtras();
        if (extra != null) {
            int sc = extra.getInt("Score");
            score.setText(String.valueOf(sc));
        }

        return myFragment;
    }
}

