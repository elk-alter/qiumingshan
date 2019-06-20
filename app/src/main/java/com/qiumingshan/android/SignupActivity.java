package com.qiumingshan.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.qiumingshan.android.db.UserInfo;

import org.litepal.LitePal;

import java.util.List;

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "SignupActivity";

    private UserInfo userInfo = new UserInfo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        TextView link_login = findViewById(R.id.link_login);
        link_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button button_signup = findViewById(R.id.btn_signup);
        button_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUserInfo();
            //    Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
            //    startActivity(intent);
             //   finish();
            }
        });
    }

    private void setUserInfo() {
        EditText input_username = findViewById(R.id.input_name_signup);
        EditText input_password = findViewById(R.id.input_password_signup);
        EditText reinput_password = findViewById(R.id.input_reEnterPassword_signup);

        List<UserInfo> userInfos;
        Log.d(TAG, "onClick: "+ TextUtils.isEmpty(input_username.getText()) + TextUtils.isEmpty(input_password.getText()));
        if (!TextUtils.isEmpty(input_username.getText()) && !TextUtils.isEmpty(input_password.getText()) && !TextUtils.isEmpty(reinput_password.getText())) {
            userInfos = LitePal.where("username is ?", input_username.getText().toString()).find(UserInfo.class);
            if (userInfos.isEmpty()) {
                if (input_password.getText().toString().equals(reinput_password.getText().toString())) {
                    userInfo.setUserName(input_username.getText().toString());
                    userInfo.setPassword(input_password.getText().toString());
                    userInfo.save();
                    Toast.makeText(SignupActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SignupActivity.this, "密码不同请检查", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(SignupActivity.this, "账号已存在", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(SignupActivity.this, "请填写完整", Toast.LENGTH_SHORT).show();
        }
    }
}