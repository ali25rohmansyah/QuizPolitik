package com.example.quizpolitik.viewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.quizpolitik.R;
import com.example.quizpolitik.interFace.ItemClickListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class RankingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtScore, txtUsername, txtCorrectAnswer;
    public CircleImageView imgUser;
    private ItemClickListener itemClickListener;

    public RankingViewHolder(@NonNull View itemView) {
        super(itemView);

        imgUser = itemView.findViewById(R.id.imgUser);
        txtScore = itemView.findViewById(R.id.txtScore);
        txtUsername = itemView.findViewById(R.id.txtUsername);
        txtCorrectAnswer = itemView.findViewById(R.id.txtCorrectAnswer);
        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }
}
