package com.example.thtuan3_nguyenleminh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private Button btnLogin;
    private Button switchRegis;
    private EditText txtUserName;
    private EditText txtPass;
    private TextView txtForgotPassword;
    private CheckBox saveLoginCheckBox;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;
    private ProgressBar mProgressBar;

    private String url = "http://10.0.2.2:8080/api/users/user/login/";
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Init Views
        initUI();

        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
        saveLogin = loginPreferences.getBoolean("saveLogin", false);

        if (saveLogin == true) {
            txtUserName.setText(loginPreferences.getString("username", ""));
            txtPass.setText(loginPreferences.getString("password", ""));
            saveLoginCheckBox.setChecked(true);
        }

        mQueue = Volley.newRequestQueue(this);

        //Switch to signup
        switchRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickGoToRegis();
            }
        });

        //User login action
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authenticateUser(url);
            }
        });

        txtForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickOpenGetOTPActivity();
            }
        });
    }

    //Xac thuc dang nhap
    public void authenticateUser(String url){

        //Check error input fields
        if( !validateEmail() || !validatePassword()){
            return;
        }

        String name = txtUserName.getText().toString();
        String pass = txtPass.getText().toString();

        if (saveLoginCheckBox.isChecked()) {
            loginPrefsEditor.putBoolean("saveLogin", true);
            loginPrefsEditor.putString("username", name);
            loginPrefsEditor.putString("password", pass);
        } else {
            loginPrefsEditor.clear();
        }
        loginPrefsEditor.commit();

        //Set parameters
        Map<String,String> params = new HashMap<>();
        params.put("email", name);
        params.put("password", pass);

        mProgressBar.setVisibility(View.VISIBLE);

        //Set request object
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params),
                response -> {
                    try {
                        String id = String.valueOf(response.get("id"));
                        String name1 = String.valueOf(response.get("name"));
                        String email = String.valueOf(response.get("email"));
                        String phone = String.valueOf(response.get("phone"));

                        //Set data intent to sent list activity
                        Intent gotoHome = new Intent(LoginActivity.this, ListActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("id", id);
                        bundle.putString("name", name1);
                        bundle.putString("email", email);
                        bundle.putString("phone", phone);
                        gotoHome.putExtra("profile", bundle);
                        mProgressBar.setVisibility(View.GONE);
                        Toast.makeText(LoginActivity.this, "Login Successfully!", Toast.LENGTH_LONG).show();
                        //Login success into list activity
                        startActivity(gotoHome);
                        finish();
                    }catch (JSONException e){
                        e.printStackTrace();
                        System.out.println(e.getMessage());
                        mProgressBar.setVisibility(View.GONE);
                    }
                }, error -> {
                    error.printStackTrace();
                    Log.e("",error.toString());
                    Toast.makeText(LoginActivity.this, "Login Failed!", Toast.LENGTH_LONG).show();
                    mProgressBar.setVisibility(View.GONE);
                });

        mQueue.add(jsonObjectRequest);
    }

    //Xac thuc field email
    public boolean validateEmail(){
        String v_email = txtUserName.getText().toString();
        //Check if email empty
        if(v_email.isEmpty()){
            txtUserName.setError("Email can not be empty!");
            txtUserName.requestFocus();
            return false;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(v_email).matches()){
            txtUserName.setError("Please enter a valid email");
            txtUserName.requestFocus();
            return false;
        }else {
            txtUserName.setError(null);
            return true;
        }
    }

    //Xac thuc field password
    public boolean validatePassword(){
        String v_pass = txtPass.getText().toString();
        if(v_pass.isEmpty()){
            txtPass.setError("Password can not be empty");
            txtUserName.requestFocus();
            return false;
        }else{
            txtPass.setError(null);
            return true;
        }
    }

    private void initUI(){
        btnLogin = findViewById(R.id.btnLogin);
        switchRegis = findViewById(R.id.switchSignup);
        txtUserName = findViewById(R.id.email_login);
        txtForgotPassword = findViewById(R.id.forgot_pass);
        txtPass = findViewById(R.id.pass_login);
        saveLoginCheckBox = findViewById(R.id.saveLoginCheckBox);
        mProgressBar = findViewById(R.id.progressBar);
    }

    public void onClickGoToRegis(){
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    public void onClickOpenGetOTPActivity() {
        Intent intent = new Intent(LoginActivity.this, GetOTPActivity.class);
        startActivity(intent);
        finish();
    }
}