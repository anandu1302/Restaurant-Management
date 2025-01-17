package com.nextgen.restaurantmanagement.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.nextgen.restaurantmanagement.FoodDetailsActivity;
import com.nextgen.restaurantmanagement.FoodListActivity;
import com.nextgen.restaurantmanagement.GlobalPreference;
import com.nextgen.restaurantmanagement.HomeActivity;
import com.nextgen.restaurantmanagement.ModelClass.FoodModelClass;
import com.nextgen.restaurantmanagement.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.MyViewHolder>{

    private static final String TAG = "FoodAdapter";

    ArrayList<FoodModelClass> list;
    Context context;
    String ip,uid;

    public FoodAdapter(ArrayList<FoodModelClass> list, Context context) {
        this.list = list;
        this.context = context;

        GlobalPreference globalPreference = new GlobalPreference(context);
        ip = globalPreference.getIP();
        uid = globalPreference.getID();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_food,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        FoodModelClass foodList = list.get(position);
        holder.foodNameTV.setText(foodList.getName());
        holder.categoryTV.setText(foodList.getCategory());
        holder.descTV.setText(foodList.getDescription());
        holder.priceTV.setText("₹ "+foodList.getPrice());

        Glide.with(context).load("http://" + ip +"/restaurantManagement/food_tbl/" + foodList.getImage()).into(holder.foodIV);

        holder.addCartTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.addedTV.setVisibility(View.VISIBLE);
                holder.addCartTV.setVisibility(View.GONE);

                addCart(foodList.getId(), foodList.getPrice());
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView foodNameTV;
        TextView categoryTV;
        TextView descTV;
        TextView priceTV;
        TextView addCartTV;
        ImageView foodIV;
        TextView addedTV;
        CardView foodCV;

        public MyViewHolder(@NonNull View itemview) {
            super(itemview);

            foodNameTV = itemview.findViewById(R.id.fItemNameTextView);
            categoryTV = itemview.findViewById(R.id.fItemTypeTextView);
            descTV = itemview.findViewById(R.id.fItemDescTextView);
            priceTV = itemview.findViewById(R.id.fItemPriceTextView);
            addCartTV = itemview.findViewById(R.id.fAddCartTextView);
            foodIV = itemview.findViewById(R.id.fItemImageView);
            addedTV = itemview.findViewById(R.id.fAddedTextView);
            foodCV = itemview.findViewById(R.id.card_food);

        }
    }

    private void addCart(String foodId,String totalAmount) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://" + ip + "/restaurantManagement/api/addCart.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: "+response);

                if (response.equals("success")){

                    Toast.makeText(context, "Successfully Added to Cart", Toast.LENGTH_SHORT).show();



                }else {

                    Toast.makeText(context, ""+response, Toast.LENGTH_SHORT).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d(TAG, "onErrorResponse: "+error);

            }
        }){
            @Override
            @Nullable
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("uid",uid);
                params.put("foodId", foodId);
                params.put("quantity", "1");
                params.put("totalAmount",totalAmount);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
}
