package com.example.thtuan3_nguyenleminh;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class ResetPasswordActivity extends AppCompatActivity {
     private Button btnReset;
     private EditText edtNewPassword;
     private EditText edtConfirm;
     private Integer otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        // Get OTP from EnterOTPActivity
        Bundle bundle = getIntent().getBundleExtra("otp");
        if(bundle != null) {
            otp = bundle.getInt("otp");
        }

        // Init Views
        initUI();

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });
    }

    private void resetPassword() {
        String password = edtConfirm.getText().toString().trim();
        String url = "http://10.0.2.2:8080/api/users/user/reset_password/" + otp + "/" + password;

        if(!validatePassword()){
            return;
        }

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equalsIgnoreCase("success")) {
                    Toast.makeText(ResetPasswordActivity.this, "Reset password successful", Toast.LENGTH_SHORT).show();
                    goToLoginActivity();
                } else {
                    Toast.makeText(ResetPasswordActivity.this, "Reset password failed!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Reset Password error", error.getMessage().toString());
            }
        });
        queue.add(request);
    }

    private void goToLoginActivity() {
        Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean validatePassword() {
        String password = edtNewPassword.getText().toString();
        String confirmPassword = edtConfirm.getText().toString();

        if(password.length() < 6) {
            edtNewPassword.setError("Password length must be less than 6!");
            return false;
        } else if(!confirmPassword.equalsIgnoreCase(password)) {
            edtConfirm.setError("Confirm not matches!");
            return false;
        } else {
            edtConfirm.setError(null);
            return true;
        }
    }

    private void initUI() {
        btnReset = findViewById(R.id.btn_reset_password);
        edtNewPassword = findViewById(R.id.edt_new_password);
        edtConfirm = findViewById(R.id.edt_confirm_new_password);
    }
}