package com.example.thtuan3_nguyenleminh;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity {
    private BottomNavigationView nav;
    private TextView txtUserName;
    private TextView txtEmail;
    private TextView txtPhone;
    private Button btnLogout;
    private Button btnEditUser;
    private int userId;
    private String email;
    private String userName;
    private String phone;
    private String url = "http://10.0.2.2:8080/api/users/user/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Get intent data profile from different activity
        Bundle bundle = getIntent().getBundleExtra("data");
        if(bundle != null){
            userId = bundle.getInt("userId");
            url = url + userId;
        }

        //Init Views
        initUI();

        getUserByID();

        //Set onclick listener
        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.home:
                        onClickGoToHome();
                        break;
                    case R.id.payment:
                        onClickToHistory();
                        break;
                    case R.id.notification:
                        Toast.makeText(ProfileActivity.this, "Home selected", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });

        btnLogout.setOnClickListener(view -> {
            new AlertDialog.Builder(ProfileActivity.this)
                    .setTitle("Đăng xuất")
                    .setMessage("Bạn sẽ đăng xuất?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            onClickToLogout();
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });
    }

    private void getUserByID() {
        RequestQueue queue = Volley.newRequestQueue(ProfileActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                userName = response.getString("name");
                email = response.getString("email");
                phone = response.getString("phone");
                txtUserName.setText(userName);
                txtEmail.setText(email);
                txtPhone.setText(phone);
            } catch (Exception e){
                Log.e("Profile Exception", e.toString());
            }
        }, error -> {
            Log.e("Profile Error", error.toString());
        });
        queue.add(jsonObjectRequest);
    }

    private void onClickGoToHome(){
        Intent intent = new Intent(ProfileActivity.this, ListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("userId", userId);
        intent.putExtra("data", bundle);
        startActivity(intent);
        finish();
    }

    private void onClickToHistory(){
        Intent intent = new Intent(ProfileActivity.this, HistoryActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("userId", userId);
        intent.putExtra("data", bundle);
        startActivity(intent);
        finish();
    }

    private void onClickToLogout(){
        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void initUI(){
        txtUserName = findViewById(R.id.txt_profile_userName);
        txtEmail = findViewById(R.id.txt_profile_email);
        txtPhone = findViewById(R.id.txt_profile_phone);
        btnEditUser = findViewById(R.id.btn_edit_user);
        btnLogout = findViewById(R.id.btn_dang_xuat);
        nav = findViewById(R.id.nav_footer);
        nav.setSelectedItemId(R.id.account);
    }
}