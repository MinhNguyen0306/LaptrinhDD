package com.example.thtuan3_nguyenleminh;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thtuan3_nguyenleminh.AdminUI.OrderAdminAdapter;
import com.example.thtuan3_nguyenleminh.Model.Order;
import com.example.thtuan3_nguyenleminh.Model.User;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>{
    private List<Order> orderList;

    public void setData(List<Order> orders){
        this.orderList = orders;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_admin, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
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

    public class HistoryViewHolder extends RecyclerView.ViewHolder{

        private TextView username;
        private TextView totalItems;
        private TextView totalPrices;
        private TextView itemsName;
        private TextView fromAdsress;
        private TextView createAt;

        public HistoryViewHolder(@NonNull View itemView){
            super(itemView);
            username = itemView.findViewById(R.id.order_username);
            totalItems = itemView.findViewById(R.id.order_total_items);
            totalPrices = itemView.findViewById(R.id.order_total_prices);
            itemsName = itemView.findViewById(R.id.order_items_name);
            fromAdsress = itemView.findViewById(R.id.order_from_address);
            createAt = itemView.findViewById(R.id.order_create_at);
        }
    }
}
