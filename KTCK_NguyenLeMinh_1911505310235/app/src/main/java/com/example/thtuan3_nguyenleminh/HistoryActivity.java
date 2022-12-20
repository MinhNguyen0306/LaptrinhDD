package com.example.thtuan3_nguyenleminh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.thtuan3_nguyenleminh.AdminUI.OrderAdminAdapter;
import com.example.thtuan3_nguyenleminh.Model.Order;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    private RecyclerView rcv;
    BottomNavigationView navBottom;
    HistoryAdapter adapter;
    private RequestQueue mQueue;
    private List<Order> orders;
    private int userId;
    String url = "http://10.0.2.2:8080/api/orders/user/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Bundle bundle = getIntent().getBundleExtra("data");
        if(bundle != null){
            userId = bundle.getInt("userId");
            url = url + userId;
        }

        initUI();

        navBottom.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home:
                    onClickGoToHome();
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
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcv.setLayoutManager(linearLayoutManager);

        mQueue = Volley.newRequestQueue(this);
        orders = new ArrayList<>();
        adapter = new HistoryAdapter();
        getAllOrders();
    }

    private void getAllOrders(){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        for(int i = 0; i < response.length(); i++){
                            JSONObject jsonObject = response.getJSONObject(i);
                            JSONArray _orderItems = jsonObject.getJSONArray(("orderItems"));
                            String foods = "";
                            if(_orderItems == null){
                                foods = "Khong co gi ca";
                            }else {
                                for (int j = 0; j < _orderItems.length(); j++){
                                    JSONObject orderItem = _orderItems.getJSONObject(j);
                                    JSONObject food = orderItem.getJSONObject("food");
                                    if(_orderItems.length() == 1){
                                        foods = food.getString("title");
                                    }else{
                                        foods += food.getString("title") + ", ";
                                    }
                                }
                            }
                            orders.add(new Order(jsonObject.getInt("totalItems"),
                                    jsonObject.getDouble("totalPrices"),
                                    jsonObject.getInt("orderstatus"),
                                    jsonObject.getString("fromAddress"),
                                    jsonObject.getString("created_at"),
                                    foods
                            ));
                        }
                        adapter.setData(orders);
                        rcv.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }catch (JSONException e){
                        e.printStackTrace();
                        System.out.println(e.getMessage());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.e("History Activity Error", error.toString());
            }
        });
        mQueue.add(jsonArrayRequest);
    }

    private void initUI(){
        navBottom = findViewById(R.id.nav_footer);
        navBottom.setSelectedItemId(R.id.payment);
        rcv = findViewById(R.id.rcv_history);
    }

    private void onClickGoToHome(){
        Intent intent = new Intent(HistoryActivity.this, ListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("userId", userId);
        intent.putExtra("data", bundle);
        startActivity(intent);
        finish();
    }

    private void onClickToAccount(){
        Intent intent = new Intent(HistoryActivity.this, ProfileActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("userId", userId);
        intent.putExtra("data", bundle);
        startActivity(intent);
        finish();
    }
}