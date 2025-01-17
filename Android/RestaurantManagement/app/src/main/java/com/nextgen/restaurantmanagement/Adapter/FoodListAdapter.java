package com.nextgen.restaurantmanagement.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nextgen.restaurantmanagement.GlobalPreference;
import com.nextgen.restaurantmanagement.ModelClass.FoodListModelClass;
import com.nextgen.restaurantmanagement.ModelClass.FoodModelClass;
import com.nextgen.restaurantmanagement.R;

import java.util.ArrayList;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.MyViewHolder>{

    private static final String TAG = "FoodListAdapter";

    ArrayList<FoodListModelClass> list;
    Context context;
    String ip,uid;

    public FoodListAdapter(ArrayList<FoodListModelClass> list, Context context) {
        this.list = list;
        this.context = context;

        GlobalPreference globalPreference = new GlobalPreference(context);
        ip = globalPreference.getIP();
        uid = globalPreference.getID();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_food_list,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        FoodListModelClass foodList = list.get(position);
        holder.nameTV.setText(foodList.getName());
        holder.quantityTV.setText(foodList.getQuantity());
        holder.priceTV.setText(foodList.getPrice());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView nameTV;
        TextView quantityTV;
        TextView priceTV;

        public MyViewHolder(@NonNull View itemview) {
            super(itemview);

            nameTV = itemview.findViewById(R.id.lFoodNameTextView);
            quantityTV = itemview.findViewById(R.id.lFoodQuantityTextView);
            priceTV = itemview.findViewById(R.id.lFoodPriceTextView);


        }
    }
}
