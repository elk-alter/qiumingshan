package com.qiumingshan.android;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.qiumingshan.android.db.Question;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.List;

public class DoitActivity extends AppCompatActivity{

    private int i = 0;
    private List<Question> allQuestions = LitePal.findAll(Question.class);
    private Question[] questions = new Question[allQuestions.size()];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doit);

        getQuestions();
        showQuestion();

        Button drop_it = findViewById(R.id.drop_it);
        Button last_q = findViewById(R.id.last_q);
        Button next_q = findViewById(R.id.next_q);
        Button submit_q = findViewById(R.id.submit_q);
        RadioGroup rg_base = findViewById(R.id._rg_base);

        //放弃
        drop_it.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(DoitActivity.this);
                dialog.setTitle("确定要放弃吗？");
                dialog.setMessage("本次答题将不会记录。");
                dialog.setCancelable(false);
                dialog.setPositiveButton("放弃", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.show();
            }
        });

        //上一题
        last_q.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(i == 0) {
                    Toast.makeText(DoitActivity.this, "已经是第一题了", Toast.LENGTH_SHORT).show();
                } else {
                    saveResult(rg_base.getCheckedRadioButtonId());
                    i--;
                    showQuestion();
                }
            }
        });

        //下一题
        next_q.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i == questions.length - 1) {
                    Toast.makeText(DoitActivity.this, "已经是最后一题了，想要提交吗？", Toast.LENGTH_SHORT).show();
                } else {
                    saveResult(rg_base.getCheckedRadioButtonId());
                    i++;
                    showQuestion();
                }
            }
        });

        //提交
        submit_q.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(DoitActivity.this);
                dialog.setTitle("确定要提交吗？");
                dialog.setMessage("提交后将不能继续更改答案。");
                dialog.setCancelable(false);
                dialog.setPositiveButton("提交", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DoitActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                        submit();
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                dialog.show();
            }
        });

    }

    //禁用返回
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Toast.makeText(DoitActivity.this, "请选择放弃或继续答题。", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    //更新上下题
    public void showQuestion() {
        TextView tv_title = findViewById(R.id._tv_title);
        RadioGroup rg_base = findViewById(R.id._rg_base);
        RadioButton rb_option_a = findViewById(R.id._rb_option_a);
        RadioButton rb_option_b = findViewById(R.id._rb_option_b);
        RadioButton rb_option_c = findViewById(R.id._rb_option_c);
        RadioButton rb_option_d = findViewById(R.id._rb_option_d);

        if (questions[i].getUser_answer() == null) {
            rg_base.clearCheck();
        } else {
            if (questions[i].getUser_answer().equals("A")) {
                rg_base.check(R.id._rb_option_a);
            }
            if (questions[i].getUser_answer().equals("B")) {
                rg_base.check(R.id._rb_option_b);
            }
            if (questions[i].getUser_answer().equals("C")) {
                rg_base.check(R.id._rb_option_c);
            }
            if (questions[i].getUser_answer().equals("D")) {
                rg_base.check(R.id._rb_option_d);
            }
        }

        tv_title.setText(questions[i].getTitle());
        rb_option_a.setText("A  " + questions[i].getOptionA());
        rb_option_b.setText("B  " + questions[i].getOptionB());
        rb_option_c.setText("C  " + questions[i].getOptionC());
        rb_option_d.setText("D  " + questions[i].getOptionD());

    }

    //保存做题过程中的结果
    public void saveResult(int result) {
        if (result == R.id._rb_option_a) {
            questions[i].setUser_answer("A");
        }
        if (result == R.id._rb_option_b) {
            questions[i].setUser_answer("B");
        }
        if (result == R.id._rb_option_c) {
            questions[i].setUser_answer("C");
        }
        if (result == R.id._rb_option_d) {
            questions[i].setUser_answer("D");
        }
        questions[i].save();
    }

    public void submit() {
        Intent intent = new Intent(DoitActivity.this, ResultActivity.class);
        startActivity(intent);
        Toast.makeText(DoitActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
        finish();
    }
    public void getQuestions() {
        for (int i = 0; i < allQuestions.size(); i++) {
            questions[i] = allQuestions.get(i);
        }
    }
}
