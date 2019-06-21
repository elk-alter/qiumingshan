package com.qiumingshan.android;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.qiumingshan.android.db.Question;
import com.qiumingshan.android.db.Questionset;
import com.qiumingshan.android.db.UserInfo;
import com.qiumingshan.android.util.HttpUtil;
import com.qiumingshan.android.util.Utility;

import org.litepal.LitePal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private DrawerLayout mDrawerLayout;

    private ProgressDialog progressDialog;

    private List<Questionset> QuestionsetList = LitePal.findAll(Questionset.class);

    private List<Question> QuestionList = LitePal.findAll(Question.class);

    private QuestionsetAdapter adapter;

    private ErrorQusetionAdapter errorQusetionAdapter;

    private SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        putJSONtoSQL();

        Log.d(TAG, "onCreate: " + QuestionsetList);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navView = findViewById(R.id.nav_view);
        View view = navView.getHeaderView(0);

        //在侧边栏显示username
        TextView username = view.findViewById(R.id.username);
        Intent intent = getIntent();
        Log.d(TAG, "onCreate: "+intent.getIntExtra("userid", 0));
        String s = LitePal.find(UserInfo.class, intent.getIntExtra("userid", 0)).getUserName();
        Log.d(TAG, "onCreate: " + s);
        username.setText(s);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        navView.setCheckedItem(R.id.nav_main);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_main:
                        showMain();
                        break;
                    case R.id.nav_result:
                        break;
                    case R.id.nav_error:
                        QuestionList = LitePal.findAll(Question.class);
                        if (LitePal.where("favorite is ?", "true").find(Question.class).size() != 0) {
                            QuestionList = LitePal.where("favorite is ?", "true").find(Question.class);
                            showError();
                        } else {
                        Toast.makeText(MainActivity.this, "还没有收藏", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.nav_about:
                        showAboutAlertDialog();
                        break;
                }
                mDrawerLayout.closeDrawers();
                return true;
            }
        });


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Data deleted", Snackbar.LENGTH_SHORT).setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "Data restored", Toast.LENGTH_SHORT).show();
                    }
                }).show();

            }
        });

        setImage();

        showMain();

        swipeRefresh = findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshQuestionsets();
            }
        });

    }

    private void setImage() {
        for (int i = 0; i < QuestionsetList.size(); i++) {
            QuestionsetList.get(i).setProblemsetImage(R.drawable.apple);
        }
    }
    private void refreshQuestionsets() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //TODO
                        //putJSONtoSQLforQuestionset();
                        adapter.notifyDataSetChanged();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.delete:
                break;
            case R.id.settings:
                Toast.makeText(this, "You clicked Settings", Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }

    //显示about信息
    public void showAboutAlertDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.about, null);
        AlertDialog.Builder dialog = new AlertDialog.Builder(this).setView(view).setTitle("秋名山 1.0");
        dialog.setNegativeButton("关闭", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dialog.show();
    }

    //主页
    public void showMain() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new QuestionsetAdapter(QuestionsetList, MainActivity.this);
        recyclerView.setAdapter(adapter);
    }

    //我的成绩
    public void showReslut() {

    }

    //错题集
    public void showError() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);

        errorQusetionAdapter = new ErrorQusetionAdapter(QuestionList, MainActivity.this);
        recyclerView.setAdapter(errorQusetionAdapter);
    }

    //get服务器Question
    private void putJSONtoSQL() {
        showProgressDialog();
        HttpUtil.sendOkHttpRequest("http://192.168.31.226:8080/JSON/Question.json", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(MainActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                Utility.handleQuestionResponse(responseText);
                MainActivity.this.runOnUiThread(new Runnable() {
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
            progressDialog = new ProgressDialog(MainActivity.this);
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
