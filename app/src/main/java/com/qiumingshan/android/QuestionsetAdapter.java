package com.qiumingshan.android;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qiumingshan.android.db.Questionset;

import java.util.List;

public class QuestionsetAdapter extends RecyclerView.Adapter<QuestionsetAdapter.ViewHolder> {
    private Context mContext;
    private List<Questionset> mQuestionsetList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView questionsetImage;
        TextView questionsetName;

        ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            questionsetImage = view.findViewById(R.id.questionset_image);
            questionsetName = view.findViewById(R.id.questionset_name);
        }
    }

    QuestionsetAdapter(List<Questionset> QuestionsetList) {
        mQuestionsetList = QuestionsetList;
    }

    @NonNull
    @Override
    public QuestionsetAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.questionset_item, parent, false);

        final QuestionsetAdapter.ViewHolder holder = new QuestionsetAdapter.ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.getAdapterPosition();
                Intent intent = new Intent(mContext, DoitActivity.class);
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionsetAdapter.ViewHolder holder, int position) {
        Questionset questionset = mQuestionsetList.get(position);
        holder.questionsetName.setText(questionset.getProblemsetName());
        Glide.with(mContext).load(questionset.getProblemsetImage()).into(holder.questionsetImage);
    }

    @Override
    public int getItemCount() {
        return mQuestionsetList.size();
    }
}
