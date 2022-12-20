package com.example.thtuan3_nguyenleminh;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.thtuan3_nguyenleminh.Model.Food;
import com.example.thtuan3_nguyenleminh.Model.Img;
import com.google.android.material.appbar.AppBarLayout;

import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {
     Context context;
     List<Food> foodss;
     int userId;
     List<Integer> foodIdList;
     String imageUrl;
     Bitmap imgBitmap;

    public FoodAdapter(Context mcontext, int userId, List<Integer> foodIdList) {
        this.context = mcontext;
        this.userId = userId;
        this.foodIdList = foodIdList;
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

        imageUrl = "http://10.0.2.2:8080/api/foods/food/image/" + food.getImage();
        Log.e("Image", imageUrl);
        getImageFoodUrl(imageUrl, holder.imageFood, food);

        holder.txtFoodName.setText(food.getName());
        holder.txtFoodNum.setText("Còn: " + String.valueOf(food.getNumItem()) + " món");
        holder.txtFoodPrice.setText(String.valueOf((int) food.getGia()) + " vnđ");
        holder.layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickGoToDetail(food);
            }
        });
    }

    private void getImageFoodUrl(String imgUrl, ImageView imgView, Food food){
        ImageRequest imageRequest = new ImageRequest(imgUrl, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                imgView.setImageBitmap(response);
                food.setImageBitmap(new Img(response));
                Log.e("Success", "Get image resource successfully!");
            }
        },90,90, ImageView.ScaleType.FIT_XY, null ,new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERROR", "Get Image resource failed!");
            }
        });
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(imageRequest);
    }

    private void getImageFoodItem(String imgUrl){
        ImageRequest imageRequest = new ImageRequest(imgUrl, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                Log.e("SuccessItem", "Get image resource successfully!");
                imgBitmap = response;
                Log.e("B", imgBitmap.toString());
            }
        },90,90, ImageView.ScaleType.FIT_XY, null ,new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ErrorItem", "Get Image resource failed!");
            }
        });
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(imageRequest);
    }

    private void onClickGoToDetail(Food food){
        Intent intent = new Intent(context, DetailFoodActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("userId", userId);
        String url = "http://10.0.2.2:8080/api/foods/food/image/" + food.getImage();
        getImageFoodItem(url);
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
        private CardView layoutItem;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutItem = itemView.findViewById(R.id.item_food);
            imageFood = itemView.findViewById(R.id.imgFood);
            txtFoodName = itemView.findViewById(R.id.txt_detail_namefood);
            txtFoodPrice = itemView.findViewById(R.id.txt_gia);
            txtFoodNum = itemView.findViewById(R.id.txt_num_item);
        }
    }
}
