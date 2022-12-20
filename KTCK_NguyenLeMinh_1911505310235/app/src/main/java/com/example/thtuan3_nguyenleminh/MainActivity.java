package com.example.thtuan3_nguyenleminh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.thtuan3_nguyenleminh.AdminUI.AdminActivity;
import com.example.thtuan3_nguyenleminh.AdminUI.FoodManagementActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnLogin = (Button) findViewById(R.id.btnLoginMain);
        Button btnRegis = (Button) findViewById(R.id.btnRegisMain);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doOpenSplashScreenActivity();
            }
        });

        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doOpenAdminActivity();
            }
        });
    }

    public void doOpenSplashScreenActivity(){
        Intent intent = new Intent(MainActivity.this, SplashScreenActivity.class);
        startActivity(intent);
        finish();
    }

    public void doOpenAdminActivity(){
        Intent intent = new Intent(MainActivity.this, FoodManagementActivity.class);
        startActivity(intent);
        finish();
    }
}