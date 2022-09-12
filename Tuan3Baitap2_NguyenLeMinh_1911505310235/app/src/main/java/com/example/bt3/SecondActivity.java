package com.example.bt3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Button btnBack = (Button) findViewById(R.id.btnBack);
        EditText text = (EditText) findViewById(R.id.txtName);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Bundle bundle = getIntent().getExtras();
        if(bundle == null){
            return;
        }

        String subject = (String) bundle.get("Subject title");
        TextView txtSubject = (TextView) findViewById(R.id.txtSubject);
        txtSubject.setText(subject);
    }
}