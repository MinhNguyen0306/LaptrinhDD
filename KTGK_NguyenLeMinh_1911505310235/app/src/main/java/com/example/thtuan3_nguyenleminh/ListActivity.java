package com.example.thtuan3_nguyenleminh;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ListActivity extends AppCompatActivity {
    private List<Food> foods;
    private FoodAdapter foodAdapter;
    private RecyclerView recyclerView;
    private LinearLayout headerBoard;
    BottomNavigationView navBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recyclerView = (RecyclerView) findViewById(R.id.rcvFood);
        headerBoard = (LinearLayout) findViewById(R.id.header_board);
        navBottom = (BottomNavigationView) findViewById(R.id.nav_footer);


        foodAdapter = new FoodAdapter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(linearLayoutManager);

        foodAdapter.setData(getListFood());
        recyclerView.setAdapter(foodAdapter);

        recyclerView.setOnTouchListener(new TranslateAnimation(this, navBottom));

        navBottom.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
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
            }
        });

//        lv.setOnClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                doDetailActivity(i);
//            }
//        });
//
//        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//                XacNhanXoa(i);
//                return false;
//            }
//        });
    }

    private List<Food> getListFood(){
        foods = new ArrayList<>();
        Random r = new Random();
        foods.add(new Food(R.drawable.ramen, r.nextInt(10) * 5, 6, "Ramen"));
        foods.add(new Food(R.drawable.khoaitay, r.nextInt(10) * 5, 20,"Khoai tây chiên"));
        foods.add(new Food(R.drawable.pizza, r.nextInt(10) * 5, 15,"Pizza"));
        foods.add(new Food(R.drawable.miy, r.nextInt(10) * 5, 120,"Mì Ý"));
        foods.add(new Food(R.drawable.hamberger, r.nextInt(10) * 5, 62,"Hamberger"));
        foods.add(new Food(R.drawable.ramen, r.nextInt(10) * 5, 6, "Ramen"));
        foods.add(new Food(R.drawable.khoaitay, r.nextInt(10) * 5, 20,"Khoai tây chiên"));
        foods.add(new Food(R.drawable.pizza, r.nextInt(10) * 5, 15,"Pizza"));
        foods.add(new Food(R.drawable.miy, r.nextInt(10) * 5, 120,"Mì Ý"));
        foods.add(new Food(R.drawable.hamberger, r.nextInt(10) * 5, 62,"Hamberger"));
        foods.add(new Food(R.drawable.ramen, r.nextInt(10) * 5, 6, "Ramen"));
        foods.add(new Food(R.drawable.khoaitay, r.nextInt(10) * 5, 20,"Khoai tây chiên"));
        foods.add(new Food(R.drawable.pizza, r.nextInt(10) * 5, 15,"Pizza"));
        foods.add(new Food(R.drawable.miy, r.nextInt(10) * 5, 120,"Mì Ý"));
        foods.add(new Food(R.drawable.hamberger, r.nextInt(10) * 5, 62,"Hamberger"));
        return foods;
    }

    private void onClickToAccount(){
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
        finish();
    }

//    public void XacNhanXoa(final int i){
//        AlertDialog.Builder b = new AlertDialog.Builder(this);
//        b.setTitle("NguyenLeMinh_1911505310235");
//        b.setIcon(R.mipmap.ic_launcher);
//        b.setMessage("Ban co xoa item nay?");
//        b.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                foods.remove(i);
//                foodAdapter.notifyDataSetChanged();
//            }
//        });
//        b.setNegativeButton("No", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.cancel();
//            }
//        });
//        b.show();
//    }
//
//    public void doDetailActivity(int i){
//        Intent intent = new Intent(ListActivity.this, DetailFoodActivity.class);
//        Food food = foods.get(i);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("foodName", (Serializable) food);
//        intent.putExtra("data", bundle);
//        startActivity(intent);
//    }
}