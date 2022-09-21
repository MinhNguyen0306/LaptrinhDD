package com.example.bt3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ListActivity extends AppCompatActivity {
    ArrayList<Food> foods;
    FoodAdapter foodAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ListView lv = (ListView) findViewById(R.id.lvFood);

        foods = new ArrayList<>();

        Random r = new Random();

        foods.add(new Food(R.drawable.ramen, r.nextInt(10) * 5, 6, "Ramen"));
        foods.add(new Food(R.drawable.khoaitay, r.nextInt(10) * 5, 20,"Khoai tây chiên"));
        foods.add(new Food(R.drawable.pizza, r.nextInt(10) * 5, 15,"Pizza"));
        foods.add(new Food(R.drawable.miy, r.nextInt(10) * 5, 120,"Mì Ý"));
        foods.add(new Food(R.drawable.hamberger, r.nextInt(10) * 5, 62,"Hamberger"));

        foodAdapter = new FoodAdapter(this, 0, foods);
        lv.setAdapter(foodAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                doDetailActivity(i);
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                XacNhanXoa(i);
                return false;
            }
        });
    }

    public void XacNhanXoa(final int i){
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("NguyenLeMinh_1911505310235");
        b.setIcon(R.mipmap.ic_launcher);
        b.setMessage("Ban co xoa item nay?");
        b.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                foods.remove(i);
                foodAdapter.notifyDataSetChanged();
            }
        });
        b.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        b.show();
    }

    public void doDetailActivity(int i){
        Intent intent = new Intent(ListActivity.this, SecondActivity.class);
        Food food = foods.get(i);
        Bundle bundle = new Bundle();
        bundle.putSerializable("foodName", (Serializable) food);
        intent.putExtra("data", bundle);
        startActivity(intent);
    }
}