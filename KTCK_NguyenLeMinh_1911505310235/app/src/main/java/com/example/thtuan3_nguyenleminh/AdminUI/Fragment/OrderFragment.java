package com.example.thtuan3_nguyenleminh.AdminUI.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.thtuan3_nguyenleminh.AdminUI.AdminActivity;
import com.example.thtuan3_nguyenleminh.AdminUI.OrderAdminAdapter;
import com.example.thtuan3_nguyenleminh.Model.Food;
import com.example.thtuan3_nguyenleminh.Model.Order;
import com.example.thtuan3_nguyenleminh.Model.OrderItem;
import com.example.thtuan3_nguyenleminh.Model.User;
import com.example.thtuan3_nguyenleminh.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OrderFragment extends Fragment {

    private RecyclerView rcvOrder;
    private View mView;
    private AdminActivity adminActivity;
    private Button btnViewDetail;
    private Button btnReject;
    private Button btnAccept;
    OrderAdminAdapter adapter;
    private RequestQueue mQueue;
    private List<Order> orders;
    private String url = "http://10.0.2.2:8080/api/orders/";

    public OrderFragment(){}


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_order_admin, container, false);
        adminActivity = (AdminActivity) getActivity();

        // Hook to component
        rcvOrder = mView.findViewById(R.id.rcv_order_admin);
//        btnViewDetail = mView.findViewById(R.id.btn_view_detail);
//        btnAccept = mView.findViewById(R.id.btn_accept);
//        btnReject = mView.findViewById(R.id.btn_reject);

        // Init request queue
        mQueue = Volley.newRequestQueue(adminActivity);
        orders = new ArrayList<>();

        // Set Layout for recycleview
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(adminActivity);
        rcvOrder.setLayoutManager(linearLayoutManager);

        adapter = new OrderAdminAdapter();
//        adapter.setData(getListOrder());
//        rcvOrder.setAdapter(adapter);
        getAllOrders(url);

        return mView;
    }

    // Các phương thức truy cập webservice
    // Get Name User
    private void getAllOrders(String url){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for(int i = 0; i < response.length(); i++){
                                JSONObject jsonObject = response.getJSONObject(i);
//                                String date = jsonObject.getString("created_at");
//                                DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                                Date created_at = sdf.parse(date);
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
                            rcvOrder.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }catch (JSONException e){
                            e.printStackTrace();
                            System.out.println(e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                System.out.println(error.getMessage());
                Log.e("ERROR", "KOKOOOOOOOOOOOOOOOKOKOKOKO");
            }
        });
        mQueue.add(jsonArrayRequest);
    }

//    private List<Order> getListOrder() {
//        List<Order> orders = new ArrayList<>();
//        Set<OrderItem> orderItems = new HashSet<>();
//        orderItems.add(new OrderItem(new Food(6, "Ramen")));
//        orderItems.add(new OrderItem(new Food(6, "My Y")));
//        orderItems.add(new OrderItem(new Food(6, "Banh Xeo")));
//        orders.add(new Order(5,60000,0, "Texas", Calendar.getInstance().getTime(),
//                new User("Taylor Switt", "0703512447"), orderItems));
//        orders.add(new Order(8,895000,1, "Washington DC", Calendar.getInstance().getTime(),
//                new User("John Smith", "0703512447"), orderItems));
//        orders.add(new Order(1,54651,1, "Colorado", Calendar.getInstance().getTime(),
//                new User("Jungkook", "0703512447"), orderItems));
//        return orders;
//    }
}
