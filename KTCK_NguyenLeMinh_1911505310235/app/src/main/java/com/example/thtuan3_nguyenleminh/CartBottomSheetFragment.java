package com.example.thtuan3_nguyenleminh;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.thtuan3_nguyenleminh.Model.OrderItem;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import vn.momo.momo_partner.AppMoMoLib;
//import vn.momo.momo_partner.MoMoParameterNameMap;

public class CartBottomSheetFragment extends BottomSheetDialogFragment {
    private List<OrderItem> orderItemList;
    private IClickListener iClickListener;
    private RecyclerView rcvCart;
    private CartAdapter cartAdapter;
    private Button btnOrder;
    private EditText edtAddress;
    private TextView tvTotalPrice;
    private int userId;

    public CartBottomSheetFragment(){
    }

    public CartBottomSheetFragment(List<OrderItem> orderItemList, int userId, IClickListener iClickListener) {
        this.orderItemList = orderItemList;
        this.userId = userId;
        this.iClickListener = iClickListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_bottom_sheet_cart, null);
        bottomSheetDialog.setContentView(view);

        tvTotalPrice = bottomSheetDialog.findViewById(R.id.total_prices_cart);
        btnOrder = bottomSheetDialog.findViewById(R.id.btn_order_cart);

        rcvCart = view.findViewById(R.id.rcv_cart);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL);
        rcvCart.addItemDecoration(itemDecoration);

        rcvCart.setLayoutManager(linearLayoutManager);

        cartAdapter = new CartAdapter(orderItemList, userId, this.getContext(), this.tvTotalPrice, new IClickListener() {
            @Override
            public void clickOrderItems(OrderItem orderItem) {
                iClickListener.clickOrderItems(orderItem);
            }
        });

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openOrderDialog(Gravity.CENTER);
            }
        });

        if (orderItemList != null){
            double total = 0.0;
            for(OrderItem orderItem : orderItemList){
                total += orderItem.getTotalprice();
            }
            tvTotalPrice.setText("BILL: " + total);
        }

        rcvCart.setAdapter(cartAdapter);
        return bottomSheetDialog;
    }

//    private String amount = "10000";
//    private String fee = "0";
//    int environment = 0;//developer default
//    private String merchantName = "Demo SDK";
//    private String merchantCode = "SCB01";
//    private String merchantNameLabel = "Nhà cung cấp";
//    private String description = "Thanh toán dịch vụ ABC";

    private void openOrderDialog(int gravity) {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_order);


        Window window = dialog.getWindow();
        if(window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        dialog.setCancelable(false);

        edtAddress = dialog.findViewById(R.id.edt_address);
        Button btnCancel = dialog.findViewById(R.id.btn_cancel_dialog);
        Button btnAccept = dialog.findViewById(R.id.btn_accept_order_dialog);
        Spinner spinner = dialog.findViewById(R.id.spn_type_payment);

        String[] valueSpinner = {"Tiền mặt", "Ví Momo"};
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(valueSpinner));
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.style_spinner, arrayList);
        spinner.setAdapter(arrayAdapter);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paymentOrder(dialog);
            }
        });

        dialog.show();
    }

    //Get token through MoMo app
//    private void requestPayment(String orderId) {
//        AppMoMoLib.getInstance().setAction(AppMoMoLib.ACTION.PAYMENT);
//        AppMoMoLib.getInstance().setActionType(AppMoMoLib.ACTION_TYPE.GET_TOKEN);
//
//        Map<String, Object> eventValue = new HashMap<>();
//        //client Required
//        eventValue.put("merchantname", merchantName); //Tên đối tác. được đăng ký tại https://business.momo.vn. VD: Google, Apple, Tiki , CGV Cinemas
//        eventValue.put("merchantcode", merchantCode); //Mã đối tác, được cung cấp bởi MoMo tại https://business.momo.vn
//        eventValue.put("amount", amount); //Kiểu integer
//        eventValue.put("orderId", orderId); //uniqueue id cho BillId, giá trị duy nhất cho mỗi BILL
//        eventValue.put("orderLabel", orderId); //gán nhãn
//
//        //client Optional - bill info
//        eventValue.put("merchantnamelabel", "Dịch vụ");//gán nhãn
//        eventValue.put("fee", "0"); //Kiểu integer
//        eventValue.put("description", description); //mô tả đơn hàng - short description
//
//        //client extra data
//        eventValue.put("requestId",  merchantCode+"merchant_billId_"+System.currentTimeMillis());
//        eventValue.put("partnerCode", merchantCode);
//        //Example extra data
//        JSONObject objExtraData = new JSONObject();
//        try {
//            objExtraData.put("site_code", "008");
//            objExtraData.put("site_name", "CGV Cresent Mall");
//            objExtraData.put("screen_code", 0);
//            objExtraData.put("screen_name", "Special");
//            objExtraData.put("movie_name", "Kẻ Trộm Mặt Trăng 3");
//            objExtraData.put("movie_format", "2D");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        eventValue.put("extraData", objExtraData.toString());
//
//        eventValue.put("extra", "");
//        AppMoMoLib.getInstance().requestMoMoCallBack(this, eventValue);
//
//
//    }
    //Get token callback from MoMo app an submit to server side
//    void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == AppMoMoLib.getInstance().REQUEST_CODE_MOMO && resultCode == -1) {
//            if(data != null) {
//                if(data.getIntExtra("status", -1) == 0) {
//                    //TOKEN IS AVAILABLE
//                    tvMessage.setText("message: " + "Get token " + data.getStringExtra("message"));
//                    String token = data.getStringExtra("data"); //Token response
//                    String phoneNumber = data.getStringExtra("phonenumber");
//                    String env = data.getStringExtra("env");
//                    if(env == null){
//                        env = "app";
//                    }
//
//                    if(token != null && !token.equals("")) {
//                        // TODO: send phoneNumber & token to your server side to process payment with MoMo server
//                        // IF Momo topup success, continue to process your order
//                    } else {
//                        tvMessage.setText("message: " + this.getString(R.string.not_receive_info));
//                    }
//                } else if(data.getIntExtra("status", -1) == 1) {
//                    //TOKEN FAIL
//                    String message = data.getStringExtra("message") != null?data.getStringExtra("message"):"Thất bại";
//                    tvMessage.setText("message: " + message);
//                } else if(data.getIntExtra("status", -1) == 2) {
//                    //TOKEN FAIL
//                    tvMessage.setText("message: " + this.getString(R.string.not_receive_info));
//                } else {
//                    //TOKEN FAIL
//                    tvMessage.setText("message: " + this.getString(R.string.not_receive_info));
//                }
//            } else {
//                tvMessage.setText("message: " + this.getString(R.string.not_receive_info));
//            }
//        } else {
//            tvMessage.setText("message: " + this.getString(R.string.not_receive_info_err));
//        }
//    }

    public void paymentOrder(Dialog dialog) {
        String url = "http://10.0.2.2:8080/api/orders/user/" + userId;
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast toast = new Toast(getContext());
                LayoutInflater inflater = getLayoutInflater();
                View view = inflater.inflate(R.layout.layout_custom_toast, (ViewGroup) getDialog().findViewById(R.id.layout_custom_toast));
                TextView txtMessage = view.findViewById(R.id.txt_toast_message);
                txtMessage.setText("Đã đặt" + (int) orderItemList.size() + " món");
                toast.setView(view);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.show();
                dialog.dismiss();
                orderItemList.removeAll(orderItemList);
                cartAdapter.notifyDataSetChanged();
                tvTotalPrice.setText("BILL: " + String.valueOf(0.0));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Request Order Error", error.toString());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("address", edtAddress.getText().toString());
                return map;
            }
        };
        queue.add(request);
    }

}
