package com.example.quizpolitik.ui.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    View view;

    Button btnStart;
    TextView welcome1, welcome2, t1, t2, t3, t4, t5, txtLevel, txtScore;
    DatabaseReference rankingTable;
    String status="";

    public static  HomeFragment newInstance(){
        return new HomeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rankingTable = FirebaseDatabase.getInstance().getReference("Ranking");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/SegoeBold.ttf");
        Typeface font1 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/SegoeUI.ttf");

        //Views
        welcome1 = view.findViewById(R.id.welcome1);
        welcome2 = view.findViewById(R.id.welcome2);
        t1  = view.findViewById(R.id.t1);
        t2  = view.findViewById(R.id.t2);
        t3  = view.findViewById(R.id.t3);
        t4  = view.findViewById(R.id.t4);
        t5  = view.findViewById(R.id.t5);
        txtLevel = view.findViewById(R.id.txtLevel);
        txtScore = view.findViewById(R.id.txtScore);
        btnStart = view.findViewById(R.id.btnStart);

        welcome1.setTypeface(font);
        welcome2.setTypeface(font);
        t1.setTypeface(font);
        t2.setTypeface(font);
        t3.setTypeface(font);
        t4.setTypeface(font);
        t5.setTypeface(font1);
        txtLevel.setTypeface(font);
        txtScore.setTypeface(font);
        btnStart.setTypeface(font);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoryFragment CategoryFragment = new CategoryFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frameLayout, CategoryFragment);
                ft.commit();
            }
        });

       rankingTable.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               Ranking ranking = dataSnapshot.child(Common.username).getValue(Ranking.class);
               assert ranking != null;
               txtScore.setText(String.valueOf(ranking.getScore()));
               if (ranking.getScore()<150){
                   status="Beginner";
               }else if(ranking.getScore()<300){
                   status="Intermediate";
               }else{
                   status="Expert";
               }
               txtLevel.setText(status);
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });

        return view;
    }
}
