package com.example.thtuan3_nguyenleminh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.thtuan3_nguyenleminh.Model.Food;
import com.example.thtuan3_nguyenleminh.Model.OrderItem;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DetailFoodActivity extends AppCompatActivity {
    ImageView imgFood;
    TextView txtFood;
    TextView price;
    TextView txtDescription;
    TextView lblTotalPrice;
    TextView atc;
    ImageButton btnBack;
    Button btnAddCart;
    BottomNavigationView navBottom;
    BottomNavigationView navBottomInCart;
    FloatingActionButton btnCart;
    ImageButton btnAddToCart;
    ImageButton btnIncrease;
    ImageButton btnDecrease;
    EditText edtQuantity;
    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Toolbar toolbar;
    private Menu menu;
    private boolean isExpanded = true;
    private boolean isInCart;
    int userId;
    int foodId;
    ArrayList<Integer> foodIdList;

    RequestQueue mQueue;
    double foodPrice;
    List<OrderItem> orderItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_food);

        //Hook to components
        initUI();

        mQueue = Volley.newRequestQueue(this);
        orderItems = new ArrayList<>();
        foodIdList = new ArrayList<>();

        //Get Intent from FoodAdapter
        Bundle bundle = getIntent().getBundleExtra("data");
        if(bundle != null){
            Food food = (Food) bundle.getSerializable("foodName");
            userId = bundle.getInt("userId");
            isInCart = food.isInCart();
            foodId = food.getId();
            //Add food is in cart into Set
            foodIdList.add(bundle.getInt("foodId"));
            foodPrice = food.getGia();
            imgFood.setImageBitmap(food.getImageBitmap().bitmap);
            txtFood.setText(food.getName());
            txtDescription.setText(food.getDescription());
            price.setText(String.valueOf(food.getGia()) + " VND");
        }
        getOrderItems(userId);

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                atc.setVisibility(View.GONE);
                btnAddToCart.setVisibility(View.GONE);
                btnDecrease.setVisibility(View.VISIBLE);
                btnIncrease.setVisibility(View.VISIBLE);
                edtQuantity.setVisibility(View.VISIBLE);
                btnAddCart.setVisibility(View.VISIBLE);
                navBottom.setVisibility(View.VISIBLE);
                edtQuantity.setText("1");
                sendDataToFragment();
                lblTotalPrice.setText(String.valueOf(Integer.parseInt(edtQuantity.getText().toString().trim()) * foodPrice));
            }
        });

        btnDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = Integer.parseInt(edtQuantity.getText().toString().trim());
                if(quantity == 1) {
                    atc.setVisibility(View.VISIBLE);
                    btnAddToCart.setVisibility(View.VISIBLE);
                    btnDecrease.setVisibility(View.GONE);
                    btnIncrease.setVisibility(View.GONE);
                    edtQuantity.setVisibility(View.GONE);
                    btnAddCart.setVisibility(View.GONE);
                    navBottom.setVisibility(View.GONE);
                }else {
                    edtQuantity.setText(String.valueOf(quantity - 1));
                    lblTotalPrice.setText(String.valueOf(Integer.parseInt(edtQuantity.getText().toString().trim()) * foodPrice));
                }
            }
        });

        btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = Integer.parseInt(edtQuantity.getText().toString().trim());
                edtQuantity.setText(String.valueOf(quantity + 1));
                lblTotalPrice.setText(String.valueOf(Integer.parseInt(edtQuantity.getText().toString().trim()) * foodPrice));
            }
        });


        // ONCLICK LISTENER EVENT
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickBackList();
            }
        });

        btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItemToCart(userId,foodId, Integer.parseInt(edtQuantity.getText().toString().trim()));

            }
        });


        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickOnBottomSheetCart(orderItems);
            }
        });

//        navBottom.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()){
//                    case R.id.home:
//                        break;
//                    case R.id.payment:
//                        break;
//                    case R.id.notification:
//                        break;
//                    case R.id.account:
//                        onClickToAccount();
//                        break;
//                }
//                return true;
//            }
//        });
    }

    private void sendDataToFragment() {
        String quantity = edtQuantity.getText().toString().trim();
        String title = txtFood.getText().toString().trim();
        String price = lblTotalPrice.getText().toString().trim();
        Bundle bundle = new Bundle();
        bundle.putString("quantity", quantity);
        bundle.putString("title", title);
        bundle.putString("price", price);
        CartBottomSheetFragment fragment = new CartBottomSheetFragment();
        fragment.setArguments(bundle);
        if(fragment.getArguments() != null){
            Log.e("Rr", fragment.getArguments().getString("quantity"));
        }
    }

    private void onClickOnBottomSheetCart(List<OrderItem> orderItems){
        CartBottomSheetFragment cartBottomSheetFragment = new CartBottomSheetFragment(orderItems,userId, new IClickListener() {
            @Override
            public void clickOrderItems(OrderItem orderItem) {
                Toast.makeText(DetailFoodActivity.this, orderItem.getFood().getName(), Toast.LENGTH_SHORT).show();
            }
        });
        cartBottomSheetFragment.show(getSupportFragmentManager(), cartBottomSheetFragment.getTag());
    }


    private void getOrderItems(int userId){
        String url = "http://10.0.2.2:8080/api/orders/user/cart/" + userId;
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

                                orderItems.add(new OrderItem(id, quantity, totalPrice, new Food(foodObjectID, foodObjectQuantity,
                                        foodObjectPrice, title, foodObjecImage)));

                                //Logic
                                if(foodObjectID == foodId){
                                    atc.setVisibility(View.GONE);
                                    btnAddToCart.setVisibility(View.GONE);
//                                    btnDecrease.setVisibility(View.VISIBLE);
//                                    btnIncrease.setVisibility(View.VISIBLE);
//                                    edtQuantity.setVisibility(View.VISIBLE);
//                                    btnAddCart.setVisibility(View.VISIBLE);
                                    navBottomInCart.setVisibility(View.VISIBLE);
//                                    edtQuantity.setText(String.valueOf(quantity));
//                                    lblTotalPrice.setText(String.valueOf(Integer.parseInt(edtQuantity.getText().toString().trim()) * foodPrice));
                                }
                            }
                            Log.e("OKKO", String.valueOf(userId));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("OKKO", "ERRRRR");
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Detail Food Error", error.toString());
                    }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

    private void addItemToCart(int userId, int foodId, int quantity){
        String url = "http://10.0.2.2:8080/api/orders/user/" + userId + "/food/" + foodId + "?quantity=" + quantity;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("success")){
                            Toast.makeText(DetailFoodActivity.this, "Add Cart Successful!", Toast.LENGTH_LONG).show();
                            btnDecrease.setVisibility(View.GONE);
                            btnIncrease.setVisibility(View.GONE);
                            edtQuantity.setVisibility(View.GONE);
                            btnAddCart.setVisibility(View.GONE);
                            navBottom.setVisibility(View.GONE);
                            navBottomInCart.setVisibility(View.VISIBLE);
                        } else {
                            Toast.makeText(DetailFoodActivity.this, "Add Cart Failed!!!", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Detail Food Error", "" + error.toString());
            }
        });
        mQueue.add(stringRequest);
    }

    private void initUI(){
        atc = findViewById(R.id.atc);
        txtDescription = findViewById(R.id.food_description);
        lblTotalPrice = findViewById(R.id.lbl_total_price_item);
        imgFood = findViewById(R.id.img_detail_food);
        txtFood = findViewById(R.id.txt_detail_namefood);
        price = findViewById(R.id.txt_detail_price);
        btnBack = findViewById(R.id.btn_back_list);
        btnAddCart = findViewById(R.id.btn_add_cart);
        btnCart = findViewById(R.id.btnCart);
        appBarLayout = findViewById(R.id.appbar);
        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        toolbar = findViewById(R.id.toolbar);
        btnAddToCart = findViewById(R.id.btn_add_to_cart);
        btnIncrease = findViewById(R.id.btn_increase_in_detail);
        btnDecrease = findViewById(R.id.btn_decrease_in_detail);
        edtQuantity = findViewById(R.id.tbx_quantity_item_in_detail);
        navBottom = findViewById(R.id.nav_footer_detail);
        navBottomInCart = findViewById(R.id.nav_footer_detail_isInCart);
    }

    private void onClickBackList(){
        Intent intent = new Intent(DetailFoodActivity.this, ListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("userId", userId);
        bundle.putIntegerArrayList("foodIdList", foodIdList);
        bundle.putBoolean("isInCart", isInCart);
        intent.putExtra("data", bundle);
        startActivity(intent);
        finish();
    }
}