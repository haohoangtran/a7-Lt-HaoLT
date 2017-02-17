package com.example.tranh.pomodoro.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tranh.pomodoro.R;
import com.example.tranh.pomodoro.networks.jsonmodels.LoginBodyJSON;
import com.example.tranh.pomodoro.networks.jsonmodels.LoginResponseJSON;
import com.example.tranh.pomodoro.networks.jsonmodels.RegisterBodyJSON;
import com.example.tranh.pomodoro.networks.jsonmodels.RegisterResponseJSON;
import com.example.tranh.pomodoro.networks.services.LoginService;
import com.example.tranh.pomodoro.networks.services.RegisterService;
import com.example.tranh.pomodoro.networks.services.Service;
import com.example.tranh.pomodoro.settings.Login;
import com.example.tranh.pomodoro.settings.SharedPrefs;
import com.google.gson.Gson;

import okhttp3.MediaType;
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
    private final Retrofit retrofit= new Retrofit.Builder()
            .baseUrl(api)
            .addConverterFactory(GsonConverterFactory.create())
            .build();;
    private String userName;
    private String passWord;
    private static final String api="http://a-task.herokuapp.com/api/";

    private static final MediaType mediaTypeJSON = MediaType.parse("application/json");

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


    }

    private void sentRegister(){
        //lặp nhiều quá
        //Tao dang ky
        Service registerService=retrofit.create(Service.class);
        // lay JSON dang ky,final de  tranh thay doi trong luc code
        final String registerJSON=new Gson().toJson(new RegisterBodyJSON(userName,passWord));
        //tao request loai JSON
        RequestBody registerBody=RequestBody.create(mediaTypeJSON,registerJSON);
        //chua giai thich dc sao dung enqueue
        registerService.register(registerBody).enqueue(new Callback<RegisterResponseJSON>() {
            @Override
            public void onResponse(Call<RegisterResponseJSON> call, Response<RegisterResponseJSON> response) {
                RegisterResponseJSON registerResponseJSON=response.body();
                if (registerResponseJSON==null){
                    Toast.makeText(LoginActivity.this, "Register failed!", Toast.LENGTH_SHORT).show();
                }else {
                    Log.d(TAG, String.format("code: %s", response.code()));
                    if (response.code()==200){
                        Toast.makeText(LoginActivity.this, "Register Successes!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<RegisterResponseJSON> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        
        
    }
    private void sentLogin() {

        Service loginService = retrofit.create(Service.class);
        //data
        //format => MediaType=>json
        final String loginJSON = (new Gson().toJson(new LoginBodyJSON(userName, passWord)));
        RequestBody loginBody = RequestBody.create(mediaTypeJSON, loginJSON);
        loginService.login(loginBody).enqueue(new Callback<LoginResponseJSON>() {
            @Override
            public void onResponse(Call<LoginResponseJSON> call, Response<LoginResponseJSON> response) {
                LoginResponseJSON loginResponseJSON = response.body();
                if (loginResponseJSON == null) {
                    Toast.makeText(LoginActivity.this, "User or password doesn't match", Toast.LENGTH_SHORT).show();
                } else {
                    if (response.code()==200){
                        checkLogin(loginResponseJSON);
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponseJSON> call, Throwable t) {
                Toast.makeText(LoginActivity.this,"Can't not connect to network. Please try again!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void checkLogin(LoginResponseJSON loginResponseJSON){
        if (loginResponseJSON.getCode()==1){
            onLoginSuccess();
        }else
            Toast.makeText(this, String.format("%s", loginResponseJSON.getMessage()), Toast.LENGTH_SHORT).show();
    }
    private void onLoginSuccess(){
        SharedPrefs.getInstance().put(new Login(userName, passWord));
        Toast.makeText(this, "Logged in", Toast.LENGTH_SHORT).show();
        gotoActivity();

    }

    private void attemptRegister() {
        userName = etUserName.getText().toString();
        passWord = etPassWord.getText().toString();
        if (!checkSpecialCharacter(userName)) {
            Toast.makeText(this, "Not support special character!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (passWord.length() < 8) {
            Toast.makeText(this, "Password more than 8 characters. Please try again!", Toast.LENGTH_SHORT).show();
            return;
        }
        sentRegister();
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
        sentLogin();
    }

    private void gotoActivity() {
        Intent intent = new Intent(this, TaskActivity.class);
        intent.putExtra("user",userName);
        startActivity(intent);
    }
}
