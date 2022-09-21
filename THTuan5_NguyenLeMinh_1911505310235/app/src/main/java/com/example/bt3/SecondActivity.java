package com.example.bt3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Button btnBack = (Button) findViewById(R.id.btnBack);
        TextView txtName = (TextView) findViewById(R.id.txtNameDetail);
        TextView txtSl = (TextView) findViewById(R.id.txtsl);
        TextView txtGia = (TextView) findViewById(R.id.txtprice);

        Intent intent = new Intent();
        Bundle bundle = getIntent().getBundleExtra("data");

        if(bundle != null){
            Food food = (Food) bundle.getSerializable("foodName");
            txtName.setText(food.getName());
            txtSl.setText(String.valueOf(food.getNumItem()));
            txtGia.setText(String.valueOf(food.getGia()));
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}