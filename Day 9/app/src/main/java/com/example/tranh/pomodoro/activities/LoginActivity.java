package com.example.tranh.pomodoro.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tranh.pomodoro.R;
import com.example.tranh.pomodoro.database.DbContext;
import com.example.tranh.pomodoro.database.models.Task;
import com.example.tranh.pomodoro.networks.jsonmodels.LoginBodyJSON;
import com.example.tranh.pomodoro.networks.jsonmodels.LoginResponseJSON;
import com.example.tranh.pomodoro.networks.jsonmodels.RegisterBodyJSON;
import com.example.tranh.pomodoro.networks.jsonmodels.RegisterResponseJSON;
import com.example.tranh.pomodoro.networks.services.GetAllTask;
import com.example.tranh.pomodoro.networks.services.Service;
import com.example.tranh.pomodoro.settings.Login;
import com.example.tranh.pomodoro.settings.SharedPrefs;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.toString();
    private EditText etUserName;
    private EditText etPassWord;
    private Button btRegister;
    private Button btLogin;
    private ProgressDialog pdLogin;
    private static final String api = "http://a-task.herokuapp.com/api/";
    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(api)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    ;
    private String userName;
    private String passWord;

    private static final MediaType mediaTypeJSON = MediaType.parse("application/json");
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //skipLogin();
        setContentView(R.layout.activity_login);
        etUserName = (EditText) this.findViewById(R.id.et_username);
        etPassWord = (EditText) this.findViewById(R.id.et_password);
        btLogin = (Button) this.findViewById(R.id.bt_login);
        btRegister = (Button) this.findViewById(R.id.bt_register);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptRegister();
            }
        });
        etPassWord.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });


    }

    private void sentRegister() {
        //lặp nhiều quá
        //Tao dang ky
        Service registerService = retrofit.create(Service.class);
        // lay JSON dang ky,final de  tranh thay doi trong luc code
        final String registerJSON = new Gson().toJson(new RegisterBodyJSON(userName, passWord));
        //tao request loai JSON
        RequestBody registerBody = RequestBody.create(mediaTypeJSON, registerJSON);

        registerService.register(registerBody).enqueue(new Callback<RegisterResponseJSON>() {
            @Override
            public void onResponse(Call<RegisterResponseJSON> call, Response<RegisterResponseJSON> response) {
                RegisterResponseJSON registerResponseJSON = response.body();
                if (registerResponseJSON == null) {
                    Toast.makeText(LoginActivity.this, "Register failed!", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d(TAG, String.format("code: %s", response.code()));
                    if (response.code() == 200) {
                        Toast.makeText(LoginActivity.this, "Register Successes!", Toast.LENGTH_SHORT).show();
                        Log.d(this.toString(), String.format("%s", registerResponseJSON));
                    }
                }
            }

            @Override
            public void onFailure(Call<RegisterResponseJSON> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, String.format("onFailure: %s", t.toString()));
            }
        });


    }
    //Todo nên để ở đâu?
    public void getAllTask() {
        GetAllTask getAllTaskService = LoginActivity.retrofit.create(GetAllTask.class);
        String token= SharedPrefs.getInstance().getAccessToken();
        Call<List<Task>> getAllTask = getAllTaskService.getAllTask("JWT " + token);
        Log.e(TAG, String.format("getAllTask: %s", token));
        getAllTask.enqueue(new Callback<List<Task>>() {
            @Override
            public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                List<Task> tasks = response.body();

                for (int i = 0; i < tasks.size(); i++) {
                    if (tasks.get(i).getName() != null) {
                        DbContext.instance.addTask(tasks.get(i));
                        Log.e(TAG, String.format("onResponse: %s", tasks.get(i).toString()));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Task>> call, Throwable t) {

            }
        });

    }



    private void sentLogin() {
        pdLogin = ProgressDialog.show(LoginActivity.this, getResources().getString(R.string.wait_title),
                getResources().getString(R.string.wait_mes), true);
        Service loginService = retrofit.create(Service.class);
        final String loginJSON = (new Gson().toJson(new LoginBodyJSON(userName, passWord)));
        RequestBody loginBody = RequestBody.create(mediaTypeJSON, loginJSON);
        Call<LoginResponseJSON> loginCall = loginService.login(loginBody);
        loginCall.enqueue(new Callback<LoginResponseJSON>() {
            @Override
            public void onResponse(Call<LoginResponseJSON> call, Response<LoginResponseJSON> response) {
                LoginResponseJSON loginResponseJSON = response.body();
                if (loginResponseJSON == null) {
                    Toast.makeText(LoginActivity.this, "User or password doesn't match", Toast.LENGTH_SHORT).show();
                    pdLogin.dismiss();

                } else {
                    if (response.code() == 200) {
                        checkLogin(loginResponseJSON);
                        onLoginSuccess();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponseJSON> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Can't not connect to network. Please try again!", Toast.LENGTH_SHORT).show();
                pdLogin.dismiss();
            }
        });
    }

    private void checkLogin(LoginResponseJSON loginResponseJSON) {
        token = loginResponseJSON.getAccessToken();
    }

    private void onLoginSuccess() {
        SharedPrefs.getInstance().put(new Login(userName, passWord, token));
        pdLogin.dismiss();
        Toast.makeText(this, "Logged in", Toast.LENGTH_SHORT).show();
        getAllTask();
        gotoActivity();


    }

    private void attemptRegister() {
        userName = etUserName.getText().toString();
        passWord = etPassWord.getText().toString();
        if (checkCondition(userName, passWord)) {
            sentRegister();
        }
    }

    private boolean checkSpecialCharacter(String s) {
        for (int i = 0; i < s.length(); i++) {
            if ((!Character.isLetter(s.charAt(i)) && !Character.isDigit(s.charAt(i)))) {
                return false;
            }
        }
        return true;
    }

    public void skipLogin() {
        if (SharedPrefs.getInstance().getLogin() != null) {
            gotoActivity();
        }
    }

    private void attemptLogin() {

        userName = etUserName.getText().toString();
        passWord = etPassWord.getText().toString();
//        if (userName.equals("admin") && passWord.equals("admin")) {
//            SharedPrefs.getInstance().put(new Login(userName, passWord));
//            Toast.makeText(this, "Logged in", Toast.LENGTH_SHORT).show();
//            gotoActivity();
//        } else
//            Toast.makeText(this, "Lỗi", Toast.LENGTH_SHORT).show();
        if (checkCondition(userName, passWord)) {
            sentLogin();
        }
    }

    public boolean checkCondition(String userName, String passWord) {

        if (userName.length() == 0) {

            String s = getResources().getString(R.string.user_name_too_sort);
            etUserName.setError(Html.fromHtml(String.format("<font color='green'>%s</font>", s)));
            ;
        }
        if (passWord.length() == 0) {
            String s = getResources().getString(R.string.password_too_sort);
            etPassWord.setError(Html.fromHtml(String.format("<font color='green'>%s</font>", s)));
            ;
        }
        if (userName.length() > 0 && userName.length() > 0) {
            return true;
        } else
            return false;
    }

    private void gotoActivity() {
        Intent intent = new Intent(this, TaskActivity.class);
        intent.putExtra("user", userName);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}
