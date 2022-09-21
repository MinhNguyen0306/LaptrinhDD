package com.example.bt3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class FoodAdapter extends ArrayAdapter<Food> {
    private Context context;
    private ArrayList<Food> foods;

    public FoodAdapter(@NonNull Context context, int resource, @NonNull List<Food> objects) {
        super(context, resource, objects);
        this.context = context;
        this.foods = new ArrayList<>(objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        if(convertView == null){
            LayoutInflater i = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = i.inflate(R.layout.item_food, null);
        }
        if(foods.size() > 0){
            Food f = foods.get(position);
            ImageView imgFood = convertView.findViewById(R.id.imgFood);
            TextView txtFoodName = convertView.findViewById(R.id.txtFoodName);
            TextView txtSoLuong = convertView.findViewById(R.id.txtSoLuong);
            TextView txtGia = convertView.findViewById(R.id.txtGia);

            imgFood.setImageResource(f.getImage());
            txtFoodName.setText(f.getName());
            txtSoLuong.setText(f.getNumItem() + "Items");
        }
        return convertView;
    }
}
