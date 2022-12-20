package com.example.thtuan3_nguyenleminh;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.thtuan3_nguyenleminh.Model.Food;
import com.example.thtuan3_nguyenleminh.Model.OrderItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartActivity extends AppCompatActivity {
    private List<OrderItem> orderItemList;
    private IClickListener iClickListener;
    private RecyclerView rcvCart;
    private CartAdapter cartAdapter;
    private Button btnOrder;
    private BottomNavigationView nav;
    private EditText edtAddress;
    TextView tvTotalPrice;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // Get intent data
        Bundle bundle = getIntent().getBundleExtra("data");
        if(bundle != null) {
            userId = bundle.getInt("userId");
        }

        // Init Views
        initUI();

        orderItemList = new ArrayList<>();

        getOrderItems(userId);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CartActivity.this);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(CartActivity.this, DividerItemDecoration.VERTICAL);
        rcvCart.addItemDecoration(itemDecoration);

        rcvCart.setLayoutManager(linearLayoutManager);

        cartAdapter = new CartAdapter(orderItemList, userId, CartActivity.this, tvTotalPrice, new IClickListener() {
            @Override
            public void clickOrderItems(OrderItem orderItem) {
                iClickListener.clickOrderItems(orderItem);
            }
        });

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openOrderDialog(Gravity.CENTER);
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
                        onClickToHistory();
                        break;
                    case R.id.notification:
                        Toast.makeText(CartActivity.this, "Home selected", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.account:
                        onClickToAccount();
                        break;
                }
                return false;
            }
        });
    }

    private void openOrderDialog(int gravity) {
        final Dialog dialog = new Dialog(CartActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_order);


        Window window = dialog.getWindow();
        if(window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        dialog.setCancelable(false);

        edtAddress = dialog.findViewById(R.id.edt_address);
        Button btnCancel = dialog.findViewById(R.id.btn_cancel_dialog);
        Button btnAccept = dialog.findViewById(R.id.btn_accept_order_dialog);
        Spinner spinner = dialog.findViewById(R.id.spn_type_payment);

        String[] valueSpinner = {"Tiền mặt", "Ví Momo"};
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(valueSpinner));
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(CartActivity.this, R.layout.style_spinner, arrayList);
        spinner.setAdapter(arrayAdapter);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paymentOrder(dialog);
            }
        });

        dialog.show();
    }

    private void getOrderItems(int userId){
        String url = "http://10.0.2.2:8080/api/orders/user/cart/" + userId;

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("orderItems");
                            for (int i = 0; i < jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                int id = jsonObject.getInt("id");
                                int quantity = jsonObject.getInt("quantity");
                                double totalPrice = jsonObject.getDouble("totalPrice");
                                JSONObject foodObject = jsonObject.getJSONObject("food");
                                int foodObjectID = foodObject.getInt("id");
                                String title = foodObject.getString("title");
                                double foodObjectPrice = foodObject.getDouble("price");
                                int foodObjectQuantity = foodObject.getInt("quantity");
                                String foodObjecImage = foodObject.getString("image");

                                orderItemList.add(new OrderItem(id, quantity, totalPrice, new Food(foodObjectID, foodObjectQuantity,
                                        foodObjectPrice, title, foodObjecImage)));
                            }
                            rcvCart.setAdapter(cartAdapter);
                            cartAdapter.notifyDataSetChanged();
                            if (orderItemList != null){
                                double total = 0.0;
                                for(OrderItem orderItem : orderItemList){
                                    total += orderItem.getTotalprice();
                                }
                                tvTotalPrice.setText("BILL: " + total);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("Request Error", e.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Detail Food Error", error.toString());
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    public void paymentOrder(Dialog dialog) {
        String url = "http://10.0.2.2:8080/api/orders/user/" + userId;
        RequestQueue queue = Volley.newRequestQueue(CartActivity.this);
        StringRequest request = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast toast = new Toast(CartActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                View view = inflater.inflate(R.layout.layout_custom_toast, findViewById(R.id.layout_custom_toast));
                TextView txtMessage = view.findViewById(R.id.txt_toast_message);
                txtMessage.setText("Đã đặt" + orderItemList.size() + " món");
                toast.setView(view);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.show();
                dialog.dismiss();
                orderItemList.removeAll(orderItemList);
                cartAdapter.notifyDataSetChanged();
                tvTotalPrice.setText("BILL: " + 0.0);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Request Order Error", error.toString());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("address", edtAddress.getText().toString());
                return map;
            }
        };
        queue.add(request);
    }

    private void initUI() {
        tvTotalPrice = findViewById(R.id.total_prices_cart_activity);
        btnOrder = findViewById(R.id.btn_order_cart_activity);
        rcvCart = findViewById(R.id.rcv_cart_activity);
        nav = findViewById(R.id.nav_footer);
    }

    private void onClickGoToHome(){
        Intent intent = new Intent(CartActivity.this, ListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("userId", userId);
        intent.putExtra("data", bundle);
        startActivity(intent);
        finish();
    }

    private void onClickToHistory(){
        Intent intent = new Intent(CartActivity.this, HistoryActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("userId", userId);
        intent.putExtra("data", bundle);
        startActivity(intent);
        finish();
    }

    private void onClickToAccount(){
        Intent intent = new Intent(CartActivity.this, ProfileActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("userId", userId);
        intent.putExtra("data", bundle);
        startActivity(intent);
        finish();
    }
}