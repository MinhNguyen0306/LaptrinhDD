package com.example.thtuan3_nguyenleminh;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.thtuan3_nguyenleminh.Model.Food;
import com.example.thtuan3_nguyenleminh.Model.Img;
import com.example.thtuan3_nguyenleminh.Model.OrderItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder>{
    private List<OrderItem> orderItemList;
    private IClickListener iClickListener;
    private Context context;
    private TextView tvTotalPrice;
    private boolean isDecrease;
    private int userId;
    private String url;
    String imageUrl;

    public CartAdapter(List<OrderItem> orderItemList, int userId, Context context, TextView totalPrice,IClickListener iClickListener) {
        this.orderItemList = orderItemList;
        this.userId = userId;
        this.iClickListener = iClickListener;
        this.context = context;
        this.tvTotalPrice = totalPrice;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent,false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        OrderItem orderItem = orderItemList.get(position);
        int p = position;
        if(orderItem == null){
            return;
        }
        url = "http://10.0.2.2:8080/api/orders/user/" + userId + "/cart/item/" + orderItem.getId();

        imageUrl = "http://10.0.2.2:8080/api/foods/food/image/" + orderItem.getFood().getImage();

        getImageFoodUrl(imageUrl, holder.imgView);

        holder.cartItemQuantity.setText(String.valueOf(orderItem.getQuantity()));
        holder.cartItemTitle.setText(orderItem.getFood().getName());
        holder.cartItemPrice.setText(String.valueOf(orderItem.getTotalprice()));
        holder.cartItemTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickListener.clickOrderItems(orderItem);
            }
        });

        holder.btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isDecrease = false;
                holder.cartItemQuantity.setText(String.valueOf(Integer.parseInt(holder.cartItemQuantity.getText().toString()) + 1));
                holder.cartItemPrice.setText(String.valueOf(Double.parseDouble(holder.cartItemPrice.getText().toString()) + orderItem.getFood().getGia()));
                int quantity = Integer.parseInt(holder.cartItemQuantity.getText().toString());
                addItemToCart(userId, orderItem.getFood().getId(), quantity, p, isDecrease);
            }
        });

        holder.btnDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isDecrease = true;
                holder.cartItemQuantity.setText(String.valueOf(Integer.parseInt(holder.cartItemQuantity.getText().toString()) - 1));
                holder.cartItemPrice.setText(String.valueOf(Double.parseDouble(holder.cartItemPrice.getText().toString()) - orderItem.getFood().getGia()));
                if(Integer.parseInt(holder.cartItemQuantity.getText().toString()) == 0){
                    deleteOrderItem(url, p);
                }else {
                    int quantity = Integer.parseInt(holder.cartItemQuantity.getText().toString());
                    addItemToCart(userId, orderItem.getFood().getId(), quantity, p, isDecrease);
                }
            }
        });

        holder.btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context)
                    .setTitle("Delete Order Item")
                    .setMessage("Ban co chac muon xoa item khong?")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            deleteOrderItem(url, p);
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
            }
        });

    }

    @Override
    public int getItemCount() {
        if(orderItemList != null){
            return orderItemList.size();
        }
        return 0;
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        private TextView cartItemTitle;
        private TextView cartItemPrice;
        private EditText cartItemQuantity;
        private ImageButton btnIncrease;
        private ImageButton btnDecrease;
        private ImageView imgView;
        private Button btnXoa;

        public CartViewHolder(@NonNull View itemView){
            super(itemView);
            cartItemQuantity = itemView.findViewById(R.id.tbx_quantity_item_in_cart);
            cartItemTitle = itemView.findViewById(R.id.cart_item_title);
            cartItemPrice = itemView.findViewById(R.id.cart_item_price);
            btnIncrease = itemView.findViewById(R.id.btn_increase_in_cart);
            btnDecrease = itemView.findViewById(R.id.btn_decrease_in_cart);
            imgView = itemView.findViewById(R.id.cart_item_img);
            btnXoa = itemView.findViewById(R.id.xoa_cart_item);
        }
    }

    private void deleteOrderItem(String url, int position) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                CartAdapter.this.notifyItemRemoved(position);
                orderItemList.remove(position);
                double total = 0;
                for (OrderItem orderItem : orderItemList) {
                    total += orderItem.getTotalprice();
                }
                tvTotalPrice.setText("BILL: " + total);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Request Error", error.toString());
            }
        });
        queue.add(request);
    }

    private void addItemToCart(int userId, int foodId, int quantity, int position, boolean decrease){
        String url = "http://10.0.2.2:8080/api/orders/user/" + userId + "/food/" + foodId + "?quantity=" + quantity;
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (decrease == true){
                            OrderItem orderItem = orderItemList.get(position);
                            orderItem.setQuantity(quantity);
                            orderItem.setTotalprice(orderItem.getFood().getGia() * quantity);
                            orderItemList.set(position, orderItem);
                            notifyDataSetChanged();
                            double total = 0;
                            for (OrderItem item : orderItemList){
                                total += item.getTotalprice();
                            }
                            tvTotalPrice.setText("BILL: " + total);
                        } else {
                            OrderItem orderItem = orderItemList.get(position);
                            orderItem.setQuantity(quantity);
                            orderItem.setTotalprice(orderItem.getFood().getGia() * quantity);
                            orderItemList.set(position, orderItem);
                            notifyDataSetChanged();
                            double total = 0;
                            for (OrderItem item : orderItemList){
                                total += item.getTotalprice();
                            }
                            tvTotalPrice.setText("BILL: " + total);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERROR", "" + error.getMessage());
            }
        });
        queue.add(stringRequest);
    }

    private void getImageFoodUrl(String imgUrl, ImageView imgView){
        ImageRequest imageRequest = new ImageRequest(imgUrl, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                imgView.setImageBitmap(response);
//                food.setImageBitmap(new Img(response));
            }
        },150,150, ImageView.ScaleType.FIT_XY, null ,new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERROR", "Get Image resource failed!");
            }
        });
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(imageRequest);
    }

}
