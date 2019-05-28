package com.example.quizpolitik.viewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quizpolitik.R;
import com.example.quizpolitik.interFace.ItemClickListener;

public class LevelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtLevel;
    public ImageView imgLevel;
    private ItemClickListener itemClickListener;

    public LevelViewHolder(@NonNull View itemView) {
        super(itemView);
        txtLevel = itemView.findViewById(R.id.txtLevel);
        imgLevel = itemView.findViewById(R.id.imgLevel);

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
