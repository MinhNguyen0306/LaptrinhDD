package com.example.thtuan3_nguyenleminh;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {
     Context context;
     List<Food> foodss;

    public FoodAdapter(Context mcontext) {
        this.context = mcontext;
    }

    public void setData(List<Food> list) {
        this.foodss = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Food food = foodss.get(position);
        if (food == null){
            return;
        }

        holder.imageFood.setImageResource(food.getImage());
        holder.txtFoodName.setText(food.getName());
        holder.txtFoodNum.setText(String.valueOf(food.getNumItem()));
        holder.txtFoodPrice.setText(String.valueOf(food.getGia()));
        holder.layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickGoToDetail(food);
            }
        });
    }

    private void onClickGoToDetail(Food food){
        Intent intent = new Intent(context, DetailFoodActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("foodName", (Serializable) food);
        intent.putExtra("data", bundle);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        if(foodss != null){
            return foodss.size();
        }
        return 0;
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageFood;
        private TextView txtFoodName;
        private TextView txtFoodPrice;
        private TextView txtFoodNum;
        private RelativeLayout layoutItem;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutItem = itemView.findViewById(R.id.item_food);
            imageFood = itemView.findViewById(R.id.imgFood);
            txtFoodName = itemView.findViewById(R.id.txt_name_food);
            txtFoodPrice = itemView.findViewById(R.id.txt_gia);
            txtFoodNum = itemView.findViewById(R.id.txt_num_item);
        }
    }
//    public List<Food> getFoods() {
//        return foods;
//    }

//    public FoodAdapter(ListActivity listActivity, int i, ArrayList<Food> foods) {
//        super(listActivity,i,foods);
//    }
//
//    public FoodAdapter(@NonNull Context context, int resource, @NonNull List<Food> objects) {
//        super(context, resource, objects);
//        this.context = context;
//        this.foods =new ArrayList<>(objects);
//    }
//
//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
//        if(convertView == null){
//            LayoutInflater i = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            convertView = i.inflate(R.layout.item_food, null);
//        }
//        if(foods.size() > 0){
//            Food f = foods.get(position);
//            ImageView imgFood = convertView.findViewById(R.id.imgFood);
//            TextView txtFoodName = convertView.findViewById(R.id.txtFoodName);
//            TextView txtSoLuong = convertView.findViewById(R.id.txtSoLuong);
//            TextView txtGia = convertView.findViewById(R.id.txtGia);
//
//            imgFood.setImageResource(f.getImage());
//            txtFoodName.setText(f.getName());
//            txtSoLuong.setText(f.getNumItem() + "Items");
//        }
//        return convertView;
//    }
}
