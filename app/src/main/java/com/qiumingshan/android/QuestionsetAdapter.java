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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.qiumingshan.android.db.Questionset;
import com.qiumingshan.android.util.HttpUtil;
import com.qiumingshan.android.util.Utility;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class QuestionsetAdapter extends RecyclerView.Adapter<QuestionsetAdapter.ViewHolder> {
    private Context mContext;
    private List<Questionset> mQuestionsetList;
    private MainActivity context;
    private ProgressDialog progressDialog;

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

    QuestionsetAdapter(List<Questionset> QuestionsetList, MainActivity context) {
        mQuestionsetList = QuestionsetList;
        this.context = context;
    }

    @NonNull
    @Override
    public QuestionsetAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.questionset_item, parent, false);

        final QuestionsetAdapter.ViewHolder holder = new QuestionsetAdapter.ViewHolder(view);
        putJSONtoSQL();
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.getAdapterPosition();
                Intent intent = new Intent(mContext, DoitActivity.class);
                intent.putExtra("name", mQuestionsetList.get(holder.getLayoutPosition()).getProblemsetName());
                intent.putExtra("id", mQuestionsetList.get(holder.getLayoutPosition()).getProblemsetId());
                intent.putExtra("problem", mQuestionsetList.get(holder.getLayoutPosition()).getProblemId());
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

    //get服务器Question
    private void putJSONtoSQL() {
        showProgressDialog();
        HttpUtil.sendOkHttpRequest("http://192.168.31.226:8080/JSON/Question.json", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(context, "加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                Utility.handleQuestionResponse(responseText);
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                    }
                });
            }
        });
    }

    /**
     * 显示进度对话框
     */
    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("正在加载云端数据...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    /**
     * 关闭进度对话框
     */
    private void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
