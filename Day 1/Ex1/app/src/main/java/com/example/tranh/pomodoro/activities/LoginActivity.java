package com.example.tranh.pomodoro.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tranh.pomodoro.R;

public class LoginActivity extends AppCompatActivity {

    private EditText etUserName;
    private EditText etPassWord;
    private Button btRegister;
    private Button btLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    private void attemptRegister() {
        String userName = etUserName.getText().toString();
        String passWord = etPassWord.getText().toString();
        if (!checkSpecialCharacter(userName)) {
            Toast.makeText(this, "Not support special character!", Toast.LENGTH_SHORT).show();
        }
        if (passWord.length() < 8) {
            Toast.makeText(this, "Password more than 8 characters. Please try again!", Toast.LENGTH_SHORT).show();
        } else if (userName.equals("admin") || userName.isEmpty() || passWord.isEmpty()) {
            //Notifiaction
            Toast.makeText(this, "Register failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Register Success", Toast.LENGTH_SHORT).show();
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

    private void attemptLogin() {
        String userName = String.valueOf(etUserName.getText());
        String passWord = etPassWord.getText().toString();
        if (userName.equals("admin") && passWord.equals("admin")) {
            //Notifiaction
            Toast.makeText(this, "Logged in", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
        }
    }
}
