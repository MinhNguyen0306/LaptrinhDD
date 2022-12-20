package com.example.thtuan3_nguyenleminh.AdminUI;

import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thtuan3_nguyenleminh.Model.Order;
import com.example.thtuan3_nguyenleminh.Model.OrderItem;
import com.example.thtuan3_nguyenleminh.Model.User;
import com.example.thtuan3_nguyenleminh.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class OrderAdminAdapter extends RecyclerView.Adapter<OrderAdminAdapter.OrderAdminViewHolder>{

    private List<Order> orderList;

    public void setData(List<Order> orders){
        this.orderList = orders;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderAdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_admin, parent, false);
        return new OrderAdminViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdminViewHolder holder, int position) {
        Order order = orderList.get(position);
        if(order == null)
            return;
        User user = order.getUser();
        if(user == null){
            Log.e("ERROR", "USER IS NULL");
        }else {
            holder.username.setText(order.getUser().getUsername());
        }
        holder.totalItems.setText("Số lượng: " + String.valueOf(order.getTotalItems()));
        holder.totalPrices.setText("Thành tiền: " + String.valueOf(order.getTotalPrices()) + " VNĐ");
//        Set<OrderItem> orderItems = order.getOrderItems();
//        if(orderItems == null){
//            holder.itemsName.setText("HIHIHI");
//        }else {
//            String title = "";
//            for (OrderItem orderItem : orderItems) {
//                title += orderItem.getFood().getName() + ", ";
//            }
//            holder.itemsName.setText(title);
//        }
        holder.itemsName.setText(order.getFoodsName());
        holder.fromAdsress.setText(order.getFromAddress());
        int status = order.getStatus();
        switch (status){
            case 0:
                holder.status.setText("Giỏ hàng");
                break;
            case 1:
                holder.status.setText("Đặt mua");
                break;
        }
//        DateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss", Locale.ENGLISH);
        holder.createAt.setText(order.getCreated_at());
    }

    @Override
    public int getItemCount() {
        if(orderList != null){
            return orderList.size();
        }
        return 0;
    }

    public class OrderAdminViewHolder extends RecyclerView.ViewHolder{

        private TextView username;
        private TextView totalItems;
        private TextView totalPrices;
        private TextView itemsName;
        private TextView fromAdsress;
        private TextView createAt;
        private TextView status;

        public OrderAdminViewHolder(@NonNull View itemView){
            super(itemView);
            username = itemView.findViewById(R.id.order_username);
            totalItems = itemView.findViewById(R.id.order_total_items);
            totalPrices = itemView.findViewById(R.id.order_total_prices);
            itemsName = itemView.findViewById(R.id.order_items_name);
            fromAdsress = itemView.findViewById(R.id.order_from_address);
            createAt = itemView.findViewById(R.id.order_create_at);
//            status = itemView.findViewById(R.id.order_status);
        }
    }
}
