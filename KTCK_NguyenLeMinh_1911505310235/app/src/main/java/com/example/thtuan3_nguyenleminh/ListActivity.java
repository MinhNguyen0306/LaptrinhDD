package com.example.thtuan3_nguyenleminh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.thtuan3_nguyenleminh.Model.Food;
import com.example.thtuan3_nguyenleminh.Model.Photo;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class ListActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private PhotoAdapter photoAdapter;
    private List<Photo> photoList;
    private Timer timer;

    private List<Food> foods;
    private FoodAdapter foodAdapter;
    private RecyclerView recyclerView;
    private LinearLayout headerBoard;
    BottomNavigationView navBottom;
    CoordinatorLayout coordinatorLayout;
    private FloatingActionButton toCartActivity;
    private RequestQueue mQueue;
    private RadioGroup radioGroup;
    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Toolbar toolbar;
    private Menu mMenu;
    private boolean isExpanded = true;
    private int userid;
    private List<Integer> foodIdList;
    private String url = "http://10.0.2.2:8080/api/foods/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        //Get UserID from Login
        Bundle bundle = getIntent().getBundleExtra("profile");
        if(bundle != null){
            userid = Integer.parseInt(bundle.getString("id"));
        }

        // Init Views
        initUI();

        photoList = getListPhoto();
        photoAdapter = new PhotoAdapter(this, photoList);
        viewPager.setAdapter(photoAdapter);
        circleIndicator.setViewPager(viewPager);
        photoAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
        autoSlideImage();

        foodIdList = new ArrayList<>();

//        Get UserId from DetailFood
        Bundle bundle1 = getIntent().getBundleExtra("data");
        if(bundle1 != null) {
            userid = bundle1.getInt("userId");
        }


//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        foodAdapter = new FoodAdapter(this, userid, foodIdList);
        mQueue = Volley.newRequestQueue(this);
        foods = new ArrayList<>();
        initToolbarAnimation();
        getAllFoods(url);


        recyclerView.setOnTouchListener(new TranslateAnimation(this, coordinatorLayout));

        navBottom.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.trangchu:
                        break;
                    case R.id.donmua:
                        onClickToHistory();
                        break;
                    case R.id.phanhoi:
                        break;
                    case R.id.taikhoan:
                        onClickToAccount();
                        break;
                }
                return true;
            }
        });

//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, int i) {
//                String urls;
//                switch (i){
//                    case R.id.cate1:
//                        Log.e("HIHI", String.valueOf(i));
//                        break;
//                    case R.id.cate2:
//                        Log.e("HIHI", "KOKOKOKOKOK");
//                        break;
//                    case R.id.cate3:
//                        Log.e("HIHI", "BIBIBIBIBIB");
//                        break;
//                }
//            }
//        });

        toCartActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickToCart();
            }
        });
    }

    private void getFoodsByCategory(String url){
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        for(int i = 0; i < response.length(); i++){
                            JSONObject food = response.getJSONObject(i);
                            int id = food.getInt("id");
                            String title = food.getString("title");
                            double price = food.getDouble("price");
                            int quantity = food.getInt("quantity");
                            String image = food.getString("image");
                            String description = food.getString("descrip");
                            foods.add(new Food(id, quantity, price, title, image, description));
                        }
                        recyclerView.setHasFixedSize(true);
                        foodAdapter.setData(foods);
                        recyclerView.setAdapter(foodAdapter);
                        foodAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        Toast.makeText(ListActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("REST ERROR: ", error.toString());
                    }
                });
        mQueue.add(request);
    }

    private void getAllFoods(String url){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray jsonArray = response.getJSONArray("content");

                        for(int i = 0; i < jsonArray.length(); i++){
                            JSONObject food = jsonArray.getJSONObject(i);
                            int id = food.getInt("id");
                            String title = food.getString("title");
                            double price = food.getDouble("price");
                            int quantity = food.getInt("quantity");
                            String image = food.getString("image");
                            String description = food.getString("descrip");
                            foods.add(new Food(id,quantity,price,title, image, description));
                        }
                        recyclerView.setHasFixedSize(true);
                        foodAdapter.setData(foods);
                        recyclerView.setAdapter(foodAdapter);
                        foodAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        Toast.makeText(ListActivity.this, e.toString(), Toast.LENGTH_SHORT);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("REST ERROR: ", error.toString());
                    }
        });
        mQueue.add(request);
    }

    @SuppressLint("ResourceAsColor")
    private void initToolbarAnimation(){
        collapsingToolbarLayout.setContentScrimColor(R.color.colorPrimary);
        collapsingToolbarLayout.setStatusBarScrimColor(R.color.colorAccent);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(Math.abs(verticalOffset) > 200){
                    isExpanded = false;
                }else{
                    isExpanded = true;
                }
                invalidateOptionsMenu();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail_food, menu);
        mMenu = menu;
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(mMenu != null && (!isExpanded || mMenu.size() != 1)) {
            mMenu.add("Search").setIcon(R.drawable.ic_baseline_search_24).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }else {

        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getTitle() == "Search") {
            Toast.makeText(this, "HIHIHIH", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private List<Photo> getListPhoto() {
        List<Photo> photoList = new ArrayList<>();
        photoList.add(new Photo(R.drawable.hamberger));
        photoList.add(new Photo(R.drawable.mi_ramen));
        photoList.add(new Photo(R.drawable.sashiminauy));
        photoList.add(new Photo(R.drawable.sushi));
        return photoList;
    }

    private void autoSlideImage() {
        if(photoList == null || photoList.isEmpty() || viewPager == null) {
            return;
        }

        // Init timer
        if(timer == null) {
            timer = new Timer();
        }

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        int currentItem = viewPager.getCurrentItem();
                        int totalItem = photoList.size() - 1;
                        if(currentItem < totalItem) {
                            currentItem ++;
                            viewPager.setCurrentItem(currentItem);
                        } else {
                            viewPager.setCurrentItem(0);
                        }
                    }
                });
            }
        }, 500, 3000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private void initUI(){
        coordinatorLayout = findViewById(R.id.coordinator);
        toCartActivity = findViewById(R.id.fab_to_cart);
        recyclerView = findViewById(R.id.rcvFood);
//        headerBoard = findViewById(R.id.header_board);
        navBottom = findViewById(R.id.nav_footer);
        appBarLayout = findViewById(R.id.appBarLayout_list);
        collapsingToolbarLayout = findViewById(R.id.collapsingToolbarLayout_list);
        toolbar = findViewById(R.id.toolbar_list);
//        radioGroup = findViewById(R.id.radio_group_cate);
        viewPager = findViewById(R.id.viewpager_slider);
        circleIndicator = findViewById(R.id.circle_indicator);
    }

    private void onClickToAccount(){
        Intent intent = new Intent(ListActivity.this, ProfileActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("userId", userid);
        intent.putExtra("data", bundle);
        startActivity(intent);
        finish();
    }

    private void onClickToHistory(){
        Intent intent = new Intent(ListActivity.this, HistoryActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("userId", userid);
        intent.putExtra("data", bundle);
        startActivity(intent);
        finish();
    }

    private void onClickToCart() {
        Intent intent = new Intent(ListActivity.this, CartActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("userId", userid);
        intent.putExtra("data", bundle);
        startActivity(intent);
        finish();
    }
}