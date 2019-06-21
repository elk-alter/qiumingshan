package com.qiumingshan.android;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.qiumingshan.android.db.Question;

import org.litepal.LitePal;

import java.util.List;

public class ErrorQuestionActivity extends AppCompatActivity {
    private static final String TAG = "ErrorQuestionActivity";

    private String questionid;
    private String title;
    private List<Question> allQuestions = LitePal.findAll(Question.class);
    private Question question;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_question);

        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        questionid = intent.getStringExtra("id");

        Log.d(TAG, "onCreate: " + questionid + title);
        Toolbar toolbar = findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbarLayout.setTitle(title);
        showQuestion();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //更新上下题
    public void showQuestion() {
        Question question = getQuestionsFromQuestionset();
        int tag = 0;
        RadioGroup rg_base = findViewById(R.id._rg_base);
        RadioButton rb_option_a = findViewById(R.id._rb_option_a);
        RadioButton rb_option_b = findViewById(R.id._rb_option_b);
        RadioButton rb_option_c = findViewById(R.id._rb_option_c);
        RadioButton rb_option_d = findViewById(R.id._rb_option_d);
        TextView tips = findViewById(R.id.tips);

        //显示用户选项
        switch (question.getUser_answer()) {
            case "A":
                tag = 1;
                rg_base.check(R.id._rb_option_a);
                break;
            case "B":
                tag = 2;
                rg_base.check(R.id._rb_option_b);
                break;
            case "C":
                tag = 3;
                rg_base.check(R.id._rb_option_c);
                break;
            case "D":
                tag = 4;
                rg_base.check(R.id._rb_option_d);
                break;
        }

        //判断正误，显示不同颜色
        if (tag == 1) {
            if (question.getUser_answer().equals(question.getAnswer())) {
                rb_option_a.setBackgroundColor(Color.parseColor("#ffc1e3"));
            } else {
                rb_option_a.setBackgroundColor(Color.parseColor("#c7c7c7"));
            }
            rb_option_b.setBackgroundColor(Color.WHITE);
            rb_option_c.setBackgroundColor(Color.WHITE);
            rb_option_d.setBackgroundColor(Color.WHITE);
        } else if (tag == 2) {
            if (question.getUser_answer().equals(question.getAnswer())) {
                rb_option_b.setBackgroundColor(Color.parseColor("#ffc1e3"));
                tips.setText(question.getAnswer() + question.getUser_answer());
            } else {
                rb_option_b.setBackgroundColor(Color.parseColor("#c7c7c7"));
            }
            rb_option_a.setBackgroundColor(Color.WHITE);
            rb_option_c.setBackgroundColor(Color.WHITE);
            rb_option_d.setBackgroundColor(Color.WHITE);
        } else if (tag == 3) {
            if (question.getUser_answer().equals(question.getAnswer())) {
                rb_option_c.setBackgroundColor(Color.parseColor("#ffc1e3"));
            } else {
                rb_option_c.setBackgroundColor(Color.parseColor("#c7c7c7"));
            }
            rb_option_a.setBackgroundColor(Color.WHITE);
            rb_option_b.setBackgroundColor(Color.WHITE);
            rb_option_d.setBackgroundColor(Color.WHITE);
        } else if (tag == 4) {
            if (question.getUser_answer().equals(question.getAnswer())) {
                rb_option_d.setBackgroundColor(Color.parseColor("#ffc1e3"));
            } else {
                rb_option_d.setBackgroundColor(Color.parseColor("#c7c7c7"));
            }
            rb_option_a.setBackgroundColor(Color.WHITE);
            rb_option_b.setBackgroundColor(Color.WHITE);
            rb_option_c.setBackgroundColor(Color.WHITE);
        }
        rb_option_a.setText("A  " + question.getOptionA());
        rb_option_b.setText("B  " + question.getOptionB());
        rb_option_c.setText("C  " + question.getOptionC());
        rb_option_d.setText("D  " + question.getOptionD());
    }

    private Question getQuestionsFromQuestionset() {
        List<Question> questions = LitePal.where("questionid is ?", questionid).find(Question.class);
        Question question = questions.get(0);
        Log.d(TAG, "showQuestion: "+ question.getUser_answer());
        return question;
    }
}
