package com.qiumingshan.android;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.qiumingshan.android.db.Question;
import com.qiumingshan.android.db.UserInfo;
import com.qiumingshan.android.util.HttpUtil;
import com.qiumingshan.android.util.Utility;

import org.litepal.LitePal;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        putJSONtoSQLforUser();
        putJSONtoSQLforQuestionset();

        List<UserInfo> allUserInfos = LitePal.findAll(UserInfo.class);

        TextView link_signup = findViewById(R.id.link_signup);
        link_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button button_login = findViewById(R.id.button_login);
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText input_username = findViewById(R.id.input_username);
                EditText input_password = findViewById(R.id.input_password);
                List<UserInfo> userInfos;
                UserInfo userInfo;
                Log.d(TAG, "onClick: " + input_username.getText().toString());
                Log.d(TAG, "onClick: "+ TextUtils.isEmpty(input_username.getText()) + TextUtils.isEmpty(input_password.getText()));
                if (!TextUtils.isEmpty(input_username.getText()) && !TextUtils.isEmpty(input_password.getText())) {

                    putJSONtoSQLforQuestionset();
                    userInfos = LitePal.where("username is ?", input_username.getText().toString()).find(UserInfo.class);
                    if (!userInfos.isEmpty()) {
                        userInfo = userInfos.get(0);
                        if (userInfo.getPassword().equals(input_password.getText().toString())) {
                            Toast.makeText(LoginActivity.this, "欢迎", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("userid", userInfo.getId());
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "账号不存在", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "请填写完整", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //get服务器UserInfo
    private void putJSONtoSQLforUser() {
        showProgressDialog();
        HttpUtil.sendOkHttpRequest("http://192.168.31.226:8080/JSON/UserInfo.json", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LoginActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(LoginActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                Utility.handleUserInfoResponse(responseText);
                LoginActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                    }
                });
            }
        });
    }
    //get服务器Questionset
    private void putJSONtoSQLforQuestionset() {
        showProgressDialog();
        HttpUtil.sendOkHttpRequest("http://192.168.31.226:8080/JSON/Questionset.json", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LoginActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(LoginActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                Utility.handleQuestionsetResponse(responseText);
                LoginActivity.this.runOnUiThread(new Runnable() {
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
            progressDialog = new ProgressDialog(LoginActivity.this);
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
