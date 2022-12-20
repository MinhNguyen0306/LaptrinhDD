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
import android.widget.ProgressBar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class GetOTPActivity extends AppCompatActivity {

    private EditText edtEmailOTP;
    private Button btnSendOTP;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_otpactivity);

        edtEmailOTP = findViewById(R.id.edt_email_get_otp);
        btnSendOTP = findViewById(R.id.btn_get_otp);
        mProgressBar = findViewById(R.id.progressBar);

        btnSendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmailOTP();
            }
        });
    }

    private void sendEmailOTP() {
        String url = "http://10.0.2.2:8080/api/users/user/forgot_password";
        RequestQueue queue = Volley.newRequestQueue(GetOTPActivity.this);
        String email = edtEmailOTP.getText().toString();

        if(!validateEmailOTP()) {
            return;
        }

        mProgressBar.setVisibility(View.VISIBLE);

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equalsIgnoreCase("success")){
                    mProgressBar.setVisibility(View.GONE);
                    goToEnterOTPActivity();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mProgressBar.setVisibility(View.GONE);
                Log.e("Sent Email OTP Error", error.getMessage().toString());
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

    private boolean validateEmailOTP(){
        String email = edtEmailOTP.getText().toString();

        if(email.isEmpty()) {
            edtEmailOTP.setError("Email Invalid!");
            return false;
        } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edtEmailOTP.setError("Email Format Invalid!");
            return false;
        } else {
            edtEmailOTP.setError(null);
            return true;
        }
    }

    private void goToEnterOTPActivity() {
        Intent intent = new Intent(GetOTPActivity.this, EnterOTPActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("email", edtEmailOTP.getText().toString().trim());
        intent.putExtra("email", bundle);
        startActivity(intent);
        finish();
    }
}