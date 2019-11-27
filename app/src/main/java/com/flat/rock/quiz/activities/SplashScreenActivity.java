package com.flat.rock.quiz.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.flat.rock.quiz.utils.SessionManager;

public class SplashScreenActivity extends AppCompatActivity {
    private SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        session = new SessionManager(getApplicationContext());

        // Check if UserResponse is Already Logged In
        session.checkLogin();

        //Intent intentLogged = new Intent(this, LoginActivity.class);
        //startActivity(intentLogged);

        finish();
    }
}