
package com.qiumingshan.android;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qiumingshan.android.db.Question;

import java.util.List;

public class ErrorQusetionAdapter extends RecyclerView.Adapter<ErrorQusetionAdapter.ViewHolder> {
    private Context mContext;
    private List<Question> mQusetionList;
    private MainActivity context;
    private ProgressDialog progressDialog;

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView errorquestionName;

        ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            errorquestionName = view.findViewById(R.id.error_question);
        }
    }

    ErrorQusetionAdapter(List<Question> QuestionList, MainActivity context) {
        mQusetionList = QuestionList;
        this.context = context;
    }

    @NonNull
    @Override
    public ErrorQusetionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.error_question, parent, false);

        final ErrorQusetionAdapter.ViewHolder holder = new ErrorQusetionAdapter.ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.getAdapterPosition();
                Intent intent = new Intent(mContext, ErrorQuestionActivity.class);
                intent.putExtra("title", mQusetionList.get(holder.getLayoutPosition()).getTitle());
                intent.putExtra("id", mQusetionList.get(holder.getLayoutPosition()).getQuestionid());
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ErrorQusetionAdapter.ViewHolder holder, int position) {
        Question question = mQusetionList.get(position);
        holder.errorquestionName.setText(question.getTitle());
    }

    @Override
    public int getItemCount() {
        return mQusetionList.size();
    }
}
