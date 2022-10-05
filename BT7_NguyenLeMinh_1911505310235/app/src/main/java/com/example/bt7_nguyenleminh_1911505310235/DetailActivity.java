package com.example.bt7_nguyenleminh_1911505310235;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {
    ContentResolver contentResolver;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        contentResolver = getContentResolver();

        Bundle bundle = getIntent().getBundleExtra("data");
        textView = findViewById(R.id.txtName);

        if(bundle != null){
            String name = bundle.getString("name");
            textView.setText(name);
        }
    }
}