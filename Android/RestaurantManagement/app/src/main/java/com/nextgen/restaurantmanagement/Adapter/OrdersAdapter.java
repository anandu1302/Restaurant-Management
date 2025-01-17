package com.nextgen.restaurantmanagement.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nextgen.restaurantmanagement.GlobalPreference;
import com.nextgen.restaurantmanagement.ModelClass.OrderedFoodModelClass;
import com.nextgen.restaurantmanagement.ModelClass.OrdersModelClass;
import com.nextgen.restaurantmanagement.OrderDetailsActivity;
import com.nextgen.restaurantmanagement.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.MyViewHolder> {

    ArrayList<OrdersModelClass> list;
    Context context;
    String ip;
    String restName,restPlace;
    String formattedDate;

    public OrdersAdapter(ArrayList<OrdersModelClass> list, Context context) {
        this.list = list;
        this.context = context;

        GlobalPreference globalPreference = new GlobalPreference(context);
        ip = globalPreference.getIP();
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_orders,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        OrdersModelClass orderList = list.get(position);
        holder.orderIdTV.setText("#"+orderList.getOrderId());
        holder.amountTV.setText("₹ "+orderList.getPrice());
        holder.statusTV.setText(orderList.getStatus());

        if (orderList.getItemCount().equals("1")){

            holder.itemCountTV.setText(orderList.getItemCount() + " Item");

        }else{
            holder.itemCountTV.setText(orderList.getItemCount() + " Items");
        }



        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            SimpleDateFormat outputFormat = new SimpleDateFormat("MMMM dd yyyy", Locale.getDefault());

            Date date = inputFormat.parse(orderList.getDate());
             formattedDate = outputFormat.format(date);
            holder.dateTV.setText(formattedDate);

            System.out.println(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<OrderedFoodModelClass> foodList = orderList.getFoodList();

        StringBuilder foodDetails = new StringBuilder();
        for (OrderedFoodModelClass food : foodList) {
            foodDetails.append(food.getFoodName())
                    .append("\n");

             restName = food.getRestName();
             restPlace = food.getRestPlace();

            holder.restNameTV.setText(restName);
            holder.restPLaceTV.setText(restPlace);
            Glide.with(context).load("http://" + ip +"/restaurantManagement/restaurant_tbl/" + food.getFoodImage()).into(holder.restoIV);
        }
        holder.foodDetailsTV.setText(foodDetails.toString());

        holder.ordersCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, OrderDetailsActivity.class);
                intent.putExtra("orderId",orderList.getOrderId());
                intent.putExtra("status",orderList.getStatus());
                intent.putExtra("restoName",restName);
                intent.putExtra("restoPlace",restPlace);
                intent.putExtra("date",formattedDate);
                intent.putExtra("amount",orderList.getPrice());
                intent.putExtra("itemCount",orderList.getItemCount());
                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView orderIdTV;
        TextView amountTV;
        TextView statusTV;
        TextView dateTV;
        TextView itemCountTV;
        TextView foodDetailsTV;
        TextView restNameTV;
        TextView restPLaceTV;
        ImageView restoIV;
        CardView ordersCV;



        public MyViewHolder(@NonNull View itemview) {
            super(itemview);

            orderIdTV = itemview.findViewById(R.id.oOrderIdTextView);
            amountTV = itemview.findViewById(R.id.oAmountTextView);
            statusTV = itemview.findViewById(R.id.oStatusTextView);
            dateTV = itemview.findViewById(R.id.oDateTextView);
            foodDetailsTV = itemview.findViewById(R.id.oFoodDetailsTextView);
            restNameTV = itemview.findViewById(R.id.oRestNameTextView);
            restPLaceTV = itemview.findViewById(R.id.oRestPlaceTextView);
            restoIV = itemview.findViewById(R.id.oRestImageView);
            itemCountTV = itemview.findViewById(R.id.oItemCountTextView);
            ordersCV = itemview.findViewById(R.id.card_orders);
        }
    }
}
