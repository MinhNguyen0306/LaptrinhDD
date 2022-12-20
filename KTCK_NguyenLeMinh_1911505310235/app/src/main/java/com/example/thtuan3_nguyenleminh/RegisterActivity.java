package com.example.thtuan3_nguyenleminh;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class RegisterActivity extends AppCompatActivity {
    Button switchLogin;
    private Button btnRegister;
    private EditText txtEmail;
    private EditText txtName;
    private EditText txtPhone;
    private EditText txtConfirm;
    private EditText txtPassword;
    private String url = "http://10.0.2.2:8080/api/users/user/register/";
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Hook to Components
        switchLogin = (Button) findViewById(R.id.switchSignin);
        btnRegister = (Button) findViewById(R.id.btnRegis);
        txtName = (EditText) findViewById(R.id.username_regis);
        txtPhone = (EditText) findViewById(R.id.phone_regis);
        txtEmail = (EditText) findViewById(R.id.email_regis);
        txtPassword = (EditText) findViewById(R.id.pass_regis);
        txtConfirm = (EditText) findViewById(R.id.confirm_regis);

        //Instantiate the request queue
        mQueue = Volley.newRequestQueue(this);

        //Switch to login
        switchLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickGoToLogin();
            }
        });

        //User register action
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerAction(url);
            }
        });

    }

    //Thực hiện đăng ký tài khoản
    public void registerAction(String url){
        //Check for errors
        if(!validateEmail() || !validateUserName() || !validatePhone() || !validatePasswordAndConfirm()){
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equalsIgnoreCase("success")){
                    txtName.setText(null);
                    txtConfirm.setText(null);
                    txtPassword.setText(null);
                    txtEmail.setText(null);
                    txtPhone.setText(null);
                    Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    onClickGoToLogin();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(RegisterActivity.this, "Đăng ký thất bại!", Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("name", txtName.getText().toString());
                params.put("email", txtEmail.getText().toString());
                params.put("phone", txtPhone.getText().toString());
                params.put("password", txtPassword.getText().toString());
                return params;
            }
        };
        mQueue.add(stringRequest);
    }

    public boolean validateEmail(){
        String v_email = txtEmail.getText().toString();
        //Check if email empty
        if(v_email.isEmpty()){
            txtEmail.setError("Email can not be empty!");
            return false;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(v_email).matches()){
            txtEmail.setError("Enter incorrect email format");
            return false;
        }else {
            txtEmail.setError(null);
            return true;
        }
    }

    public boolean validatePhone(){
        String v_phone = txtPhone.getText().toString();

        if(v_phone.isEmpty()){
            txtPhone.setError("Không được để trống");
            return false;
        }else if(v_phone.length() > 11 && v_phone.length() < 8){
            txtPhone.setError("Phone must be less than 12 and better 7");
            return true;
        }else{
            txtPhone.setError(null);
            return true;
        }
    }

    public boolean validateUserName(){
        String v_Name = txtName.getText().toString();

        if(v_Name.isEmpty()){
            txtName.setError("Not empty");
            return false;
        }else if(v_Name.length() > 15){
            txtName.setError("Username must be less than 16 character");
            return false;
        }else{
            txtName.setError(null);
            return true;
        }
    }

    public boolean validatePasswordAndConfirm(){
        String v_pass = txtPassword.getText().toString();
        String v_confirm = txtConfirm.getText().toString();

        if(v_pass.isEmpty()){
            txtPassword.setError("Không được để trống");
            return false;
        }else if(v_pass.length() <= 8){
            txtPassword.setError("Mật khẩu phải nhiều hơn 8 kí tự");
            return false;
        }else if(!v_confirm.equals(v_pass)){
            txtConfirm.setError("Không khớp mật khẩu");
            return false;
        }else{
            txtPassword.setError(null);
            return true;
        }
    }

    public void onClickGoToLogin(){
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}