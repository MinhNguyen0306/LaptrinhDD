package com.example.thtuan3_nguyenleminh.AdminUI;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.thtuan3_nguyenleminh.CategoryAdapter;
import com.example.thtuan3_nguyenleminh.Config.RealPathUtil;
import com.example.thtuan3_nguyenleminh.Model.Category;
import com.example.thtuan3_nguyenleminh.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class FoodManagementActivity extends AppCompatActivity {

    public static final String TAG = FoodManagementActivity.class.getName();
    private static final int MY_REQUEST_CODE = 10;
    private EditText tbxTitle, tbxQuantity, tbxPrice, tbxDescription;
    private Button btnUpload, btnSelectImage;
    private ImageView imgUpload;
    private Spinner spnCategory;
    private CategoryAdapter categoryAdapter;
    private String encodeImageString;
    private String cateId;
    private Uri mUri;
    private static final String url = "http://localhost:8080/api/orders/user/2/food/3?quantity=2";
    private ProgressDialog mProgessDialog;

    private ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Log.e(TAG, "onActivityResult");
                    if(result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if(data == null){
                            return;
                        }
                        Uri uri = data.getData();
                        mUri = uri;
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            imgUpload.setImageBitmap(bitmap);
//                            encodeBitmapImage(bitmap);
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                        Log.e("URI", String.valueOf(mUri.toString()));
                        String strRealPath = RealPathUtil.getRealPath(FoodManagementActivity.this, mUri);
                        Log.e("PATH", String.valueOf(strRealPath));
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_management);

        initUi();

        //Init Progress Dialog
        mProgessDialog = new ProgressDialog(this);
        mProgessDialog.setMessage("Please wait...");

        getCatagories();
        spnCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cateId= String.valueOf(categoryAdapter.getItem(i).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickRequestPermission();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                uploadImageToDb();
                if(mUri != null) {
                    createFood(cateId);
                }
            }
        });
    }

    private void getCatagories(){
        String url = "http://192.168.1.4:8080/api/categories/";
        List<Category> categoryList = new ArrayList<>();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++){
                                JSONObject jsonObject = response.getJSONObject(i);
                                categoryList.add(new Category(jsonObject.getInt("id"), jsonObject.getString("title")));
                            }
                            categoryAdapter = new CategoryAdapter(FoodManagementActivity.this, R.layout.item_category_selected, categoryList);
                            spnCategory.setAdapter(categoryAdapter);
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    private void createFood(String cateId){
        mProgessDialog.show();

        String strRealPath = RealPathUtil.getRealPath(FoodManagementActivity.this, mUri);
        File file = new File(strRealPath);
        RequestBody requestBodyImg = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part multipartBodyImg = MultipartBody.Part.createFormData("img", file.getName(), requestBodyImg);

        String url = "http://192.168.1.4:8080/api/foods/";
        String title = tbxTitle.getText().toString().trim();
        String quantity = tbxQuantity.getText().toString().trim();
        String price = tbxPrice.getText().toString().trim();
        String description = tbxDescription.getText().toString().trim();


        Map<String,String> params = new HashMap<>();
        params.put("cateId", cateId);
        params.put("title", title);
        params.put("price", price);
        params.put("quantity", quantity);
        params.put("image", String.valueOf(multipartBodyImg));
        params.put("descrip", description);

        List<Category> categoryList = new ArrayList<>();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        mProgessDialog.dismiss();
                        if (response != null){
                            Log.e("SUCCESS", response.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mProgessDialog.dismiss();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

    private void onClickRequestPermission() {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            openGallery();
            return;
        }
        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            openGallery();
        }else{
            String [] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permission, MY_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == MY_REQUEST_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                openGallery();
            }
        }
    }

    public void openGallery(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent, "Select Picture"));
    }

    public void encodeBitmapImage(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byte[] bytesOfImage = byteArrayOutputStream.toByteArray();
        encodeImageString = android.util.Base64.encodeToString(bytesOfImage, Base64.DEFAULT);
    }

    public void uploadImageToDb(String url){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("image", encodeImageString);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void initUi(){
        spnCategory = findViewById(R.id.spn_category);
        tbxTitle = findViewById(R.id.tbx_add_title_food);
        tbxDescription = findViewById(R.id.tbx_add_description_food);
        tbxPrice = findViewById(R.id.tbx_add_price_food);
        tbxQuantity = findViewById(R.id.tbx_add_quantity_food);
        btnUpload = findViewById(R.id.btn_upload_image_food);
        btnSelectImage = findViewById(R.id.btn_select_image_gallery_food);
        imgUpload = findViewById(R.id.img_upload_image_food);
    }

}