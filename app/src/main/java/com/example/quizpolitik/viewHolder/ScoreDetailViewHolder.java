package com.example.quizpolitik.viewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.quizpolitik.R;

public class ScoreDetailViewHolder extends RecyclerView.ViewHolder {

    public TextView txtCategoryName, txtScore;

    public ScoreDetailViewHolder(@NonNull View itemView) {
        super(itemView);

        txtCategoryName = itemView.findViewById(R.id.txtCategoryName);
        txtScore = itemView.findViewById(R.id.txtScore);
    }
}
