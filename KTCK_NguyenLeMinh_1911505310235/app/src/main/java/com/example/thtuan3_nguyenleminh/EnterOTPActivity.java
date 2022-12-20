package com.example.thtuan3_nguyenleminh;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class EnterOTPActivity extends AppCompatActivity {
    private Button btnVerifyOTP;
    private TextView txtResendOTP;
    private TextView txtEmail;
    private EditText edtOTP1;
    private String email;
    private EditText edtOTP2;
    private EditText edtOTP3;
    private EditText edtOTP4;
    private EditText edtOTP5;
    private EditText edtOTP6;
    private Integer otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_otpactivity);

        // Init Views
        initUI();

        setupOTPInputs();

        //Get Bundle Extra email from GetOTPActivity
        Bundle bundle = getIntent().getBundleExtra("email");
        if(bundle != null){
            email = bundle.getString("email");
        }

        txtEmail.setText(email);

        btnVerifyOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPasswordShowForm();
            }
        });

        txtResendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resendEmail();
            }
        });
    }

    private void resetPasswordShowForm() {
        String otp1 = edtOTP1.getText().toString().trim();
        String otp2 = edtOTP2.getText().toString().trim();
        String otp3 = edtOTP3.getText().toString().trim();
        String otp4 = edtOTP4.getText().toString().trim();
        String otp5 = edtOTP5.getText().toString().trim();
        String otp6 = edtOTP6.getText().toString().trim();

        otp = Integer.parseInt(otp1 + otp2 + otp3 + otp4 + otp5 + otp6);
        String url = "http://10.0.2.2:8080/api/users/user/reset_password/" + otp;

        RequestQueue queue = Volley.newRequestQueue(EnterOTPActivity.this);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equalsIgnoreCase("success")){
                    Toast.makeText(EnterOTPActivity.this, "Verify Success!", Toast.LENGTH_SHORT).show();
                    goToResetPasswordActivity(otp);
                } else {
                    Toast.makeText(EnterOTPActivity.this, "OTP Not Match!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Log.e("Enter OTP Error", error.getMessage());
                Log.e("otp", otp.toString());
            }
        });
        queue.add(request);
    }

    private void resendEmail() {
        String url = "http://10.0.2.2:8080/api/users/user/forgot_password";
        RequestQueue queue = Volley.newRequestQueue(EnterOTPActivity.this);

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equalsIgnoreCase("success")){
                    Toast.makeText(EnterOTPActivity.this, "Resend email successful!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Resend Email Error", error.getMessage().toString());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("email", email);
                return map;
            }
        };
        queue.add(request);
    }

    private void goToResetPasswordActivity(Integer otp) {
        Intent intent = new Intent(EnterOTPActivity.this, ResetPasswordActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("otp", otp);
        intent.putExtra("otp", bundle);
        startActivity(intent);
        finish();
    }

    private void setupOTPInputs() {
        edtOTP1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    edtOTP2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtOTP2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    edtOTP3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtOTP3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    edtOTP4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtOTP4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    edtOTP5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtOTP5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    edtOTP6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void initUI() {
        btnVerifyOTP = findViewById(R.id.btn_verify_otp);
        txtResendOTP = findViewById(R.id.txt_resend_otp);
        txtEmail = findViewById(R.id.txt_email_enter_otp_ac);
        edtOTP1 = findViewById(R.id.otp_1);
        edtOTP2 = findViewById(R.id.otp_2);
        edtOTP3 = findViewById(R.id.otp_3);
        edtOTP4 = findViewById(R.id.otp_4);
        edtOTP5 = findViewById(R.id.otp_5);
        edtOTP6 = findViewById(R.id.otp_6);
    }
}