package com.example.thtuan3_nguyenleminh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
                doOpenLoginActivity();
            }
        });

        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doOpenRegisActivity();
            }
        });
    }

        public void doOpenLoginActivity(){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

    public void doOpenRegisActivity(){
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }
}