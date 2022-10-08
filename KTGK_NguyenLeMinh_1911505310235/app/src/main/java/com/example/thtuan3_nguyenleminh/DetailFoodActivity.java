package com.example.thtuan3_nguyenleminh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class DetailFoodActivity extends AppCompatActivity {
    ImageView imgFood;
    TextView txtFood;
    TextView price;
    ImageButton btnBack;
    BottomNavigationView navBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_food);

        imgFood = (ImageView) findViewById(R.id.img_detail_food);
        txtFood = (TextView) findViewById(R.id.txt_detail_namefood);
        price = (TextView) findViewById(R.id.txt_detail_price);
        btnBack = (ImageButton) findViewById(R.id.btnBackFromDetail);
        navBottom = (BottomNavigationView) findViewById(R.id.nav_footer);

        Bundle bundle = getIntent().getBundleExtra("data");

        if(bundle != null){
            Food food = (Food) bundle.getSerializable("foodName");
            imgFood.setImageResource(food.getImage());
            txtFood.setText(food.getName());
            price.setText(String.valueOf(food.getGia()));
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickBackList();
            }
        });

        navBottom.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        break;
                    case R.id.payment:
                        break;
                    case R.id.notification:
                        break;
                    case R.id.account:
                        onClickToAccount();
                        break;
                }
                return true;
            }
        });
    }

    private void onClickToAccount(){
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
        finish();
    }

    private void onClickBackList(){
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
        finish();
    }
}