package com.example.thtuan3_nguyenleminh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class ProfileActivity extends AppCompatActivity {
    BottomNavigationView nav;
    ImageButton btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        btnBack = (ImageButton) findViewById(R.id.btnBack);
        nav = (BottomNavigationView) findViewById(R.id.nav_footer);
        nav.setSelectedItemId(R.id.account);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.home:
                        onClickGoToHome();
                        break;
                    case R.id.payment:
                        Toast.makeText(ProfileActivity.this, "Home selected", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.notification:
                        Toast.makeText(ProfileActivity.this, "Home selected", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.account:
                        Toast.makeText(ProfileActivity.this, "Home selected", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });
    }

    private void onClickGoToHome(){
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
        finish();
    }
}