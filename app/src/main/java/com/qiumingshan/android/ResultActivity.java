package com.qiumingshan.android;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.qiumingshan.android.db.Question;

import org.litepal.LitePal;

import java.util.List;

public class ResultActivity extends AppCompatActivity {

    private int i = 0;
    private List<Question> allQuestions = LitePal.findAll(Question.class);
    private Question[] questions;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        getQuestionsFromQuestionset();
        getQuestions();
        showQuestion();

        Button last_s = findViewById(R.id.last_s);
        Button next_s = findViewById(R.id.next_s);
        //上一题
        last_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next_s.setText("下一题");
                if(i == 0) {
                    Toast.makeText(ResultActivity.this, "已经是第一题了", Toast.LENGTH_SHORT).show();
                } else {
                    i--;
                    showQuestion();
                }
            }
        });

        //下一题
        next_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i == questions.length - 1) {
                    finish();
                } else if (i == questions.length - 2) {
                    ImageButton favorite_button = findViewById(R.id.favorite_button);
                    next_s.setText("退出");
                    i++;
                    showQuestion();
                } else {
                    ImageButton favorite_button = findViewById(R.id.favorite_button);
                    next_s.setText("下一题");
                    i++;
                    showQuestion();
                }
            }
        });
    }

    //不允许返回键
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Toast.makeText(ResultActivity.this, "请选择看完结果。", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    //更新上下题
    public void showQuestion() {
        int tag = 0;
        TextView result_title = findViewById(R.id.result_title);
        RadioGroup rg_base = findViewById(R.id._rg_base);
        RadioButton rb_option_a = findViewById(R.id._rb_option_a);
        RadioButton rb_option_b = findViewById(R.id._rb_option_b);
        RadioButton rb_option_c = findViewById(R.id._rb_option_c);
        RadioButton rb_option_d = findViewById(R.id._rb_option_d);
        TextView tips = findViewById(R.id.tips);

        ImageButton favorite_button = findViewById(R.id.favorite_button);
        TextView favorite_text = findViewById(R.id.favorite_text);
        favorite_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favorite_button.setBackgroundResource(R.drawable.baseline_favorite_white_48);
                Toast.makeText(ResultActivity.this, "已收藏", Toast.LENGTH_SHORT).show();
                questions[i].setFavorite("true");
            }
        });
        //显示用户选项
        switch (questions[i].getUser_answer()) {
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
            if (questions[i].getUser_answer().equals(questions[i].getAnswer())) {
                rb_option_a.setBackgroundColor(Color.parseColor("#ffc1e3"));
            } else {
                rb_option_a.setBackgroundColor(Color.parseColor("#c7c7c7"));
            }
            rb_option_b.setBackgroundColor(Color.WHITE);
            rb_option_c.setBackgroundColor(Color.WHITE);
            rb_option_d.setBackgroundColor(Color.WHITE);
        } else if (tag == 2) {
            if (questions[i].getUser_answer().equals(questions[i].getAnswer())) {
                rb_option_b.setBackgroundColor(Color.parseColor("#ffc1e3"));
                tips.setText(questions[i].getAnswer() + questions[i].getUser_answer());
            } else {
                rb_option_b.setBackgroundColor(Color.parseColor("#c7c7c7"));
            }
            rb_option_a.setBackgroundColor(Color.WHITE);
            rb_option_c.setBackgroundColor(Color.WHITE);
            rb_option_d.setBackgroundColor(Color.WHITE);
        } else if (tag == 3) {
            if (questions[i].getUser_answer().equals(questions[i].getAnswer())) {
                rb_option_c.setBackgroundColor(Color.parseColor("#ffc1e3"));
            } else {
                rb_option_c.setBackgroundColor(Color.parseColor("#c7c7c7"));
            }
            rb_option_a.setBackgroundColor(Color.WHITE);
            rb_option_b.setBackgroundColor(Color.WHITE);
            rb_option_d.setBackgroundColor(Color.WHITE);
        } else if (tag == 4) {
            if (questions[i].getUser_answer().equals(questions[i].getAnswer())) {
                rb_option_d.setBackgroundColor(Color.parseColor("#ffc1e3"));
            } else {
                rb_option_d.setBackgroundColor(Color.parseColor("#c7c7c7"));
            }
            rb_option_a.setBackgroundColor(Color.WHITE);
            rb_option_b.setBackgroundColor(Color.WHITE);
            rb_option_c.setBackgroundColor(Color.WHITE);
        }

        result_title.setText(questions[i].getTitle());
        rb_option_a.setText("A  " + questions[i].getOptionA());
        rb_option_b.setText("B  " + questions[i].getOptionB());
        rb_option_c.setText("C  " + questions[i].getOptionC());
        rb_option_d.setText("D  " + questions[i].getOptionD());
    }

    //从List中获取每一个Question
    public void getQuestions() {
        for (int i = 0; i < allQuestions.size(); i++) {
            questions[i] = allQuestions.get(i);
        }
    }

    private void getQuestionsFromQuestionset() {
        allQuestions.clear();
        Intent intent = getIntent();
        //题集名称
        String questionID = intent.getStringExtra("problem");
        String[] id = questionID.split(",");
        for (int i = 0; i < id.length; i++) {
            List<Question> questions = LitePal.where("questionid is ?", id[i]).find(Question.class);
            Question question = questions.get(0);
            allQuestions.add(question);
        }
        questions = new Question[allQuestions.size()];
    }
}
