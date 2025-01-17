package com.nextgen.restaurantmanagement.Adapter;

import android.content.Context;
import android.content.DialogInterface;
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
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.nextgen.restaurantmanagement.GlobalPreference;
import com.nextgen.restaurantmanagement.HomeActivity;
import com.nextgen.restaurantmanagement.ModelClass.CartModelClass;
import com.nextgen.restaurantmanagement.R;
import com.nextgen.restaurantmanagement.ui.home.CartFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder>{

    private static String TAG ="CartAdapter";

    ArrayList<CartModelClass> list;
    Context context;
    String ip,uid;
    private FragmentManager fragmentManager;
    CartFragment fragment;

    public CartAdapter(ArrayList<CartModelClass> list, Context context,CartFragment fragment) {
        this.list = list;
        this.context = context;
        this.fragment = fragment;

        GlobalPreference globalPreference = new GlobalPreference(context);
        ip = globalPreference.getIP();
        uid = globalPreference.getID();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_cart,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        CartModelClass cartList = list.get(position);
        holder.foodNameTV.setText(cartList.getItemName());
        holder.foodQuantityTV.setText(cartList.getQuantity());


        Glide.with(context).load("http://" + ip +"/restaurantManagement/food_tbl/" + cartList.getImage()).into(holder.foodIV);

        //setting up price for each item with quantity
        if (cartList.getQuantity().equals("1")){

            holder.foodPriceTV.setText("₹ "+cartList.getPrice());

        }else {

            int amount = Integer.valueOf(cartList.getPrice());
            int price;
            int quantity = Integer.parseInt(holder.foodQuantityTV.getText().toString());

            price = quantity * amount;

            holder.foodPriceTV.setText("₹ "+String.valueOf(price));

        }

        holder.addIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int quantity = Integer.parseInt(holder.foodQuantityTV.getText().toString());
                int price;

                if (quantity < 10) {
                    quantity = quantity + 1;
                    holder.foodQuantityTV.setText(String.valueOf(quantity));

                    int amount = Integer.valueOf(cartList.getPrice());

                    price = quantity * amount;

                    // Toast.makeText(context, "" + price, Toast.LENGTH_SHORT).show();

                    holder.foodPriceTV.setText("₹ "+String.valueOf(price));

                    updateQuantity(cartList.getId(),quantity);

                } else {
                    Toast.makeText(context, "Maximum quantity reached", Toast.LENGTH_SHORT).show();
                }

            }
        });

        holder.subIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = Integer.parseInt(holder.foodQuantityTV.getText().toString());
                int price;

                if (quantity > 1) {
                    quantity = quantity - 1;
                    holder.foodQuantityTV.setText(String.valueOf(quantity));

                    int amount = Integer.valueOf(cartList.getPrice());

                    price = quantity * amount;

                    // Toast.makeText(context, "" + price, Toast.LENGTH_SHORT).show();

                    holder.foodPriceTV.setText("₹ "+String.valueOf(price));

                    updateQuantity(cartList.getId(),quantity);


                } else {
                    Toast.makeText(context, "Minimum quantity required", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.deleteIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                showAlert(cartList.getId());
            }
        });

    }




    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView foodNameTV;
        TextView foodPriceTV;
        TextView foodQuantityTV;
        ImageView foodIV;
        ImageView addIV;
        ImageView subIV;
        ImageView deleteIV;


        public MyViewHolder(@NonNull View itemview) {
            super(itemview);

            foodNameTV = itemview.findViewById(R.id.cItemNameTextView);
            foodPriceTV = itemview.findViewById(R.id.cItemPriceTextView);
            foodQuantityTV = itemview.findViewById(R.id.cItemQuantityTextView);
            foodIV = itemview.findViewById(R.id.cFoodImageView);
            addIV = itemview.findViewById(R.id.cAddItemImageView);
            subIV = itemview.findViewById(R.id.cSubItemImageView);
            deleteIV = itemview.findViewById(R.id.cDeleteImageView);


        }
    }

    private void updateQuantity(String id, int quantity) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://" + ip +"/restaurantManagement/api/updateQuantity.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: "+response);

                if (response.equals("success")){
                    Toast.makeText(context, "Quantity Updated", Toast.LENGTH_SHORT).show();

                    fragment.getCartDetails();
                }else{

                    Toast.makeText(context, "Failed to Update", Toast.LENGTH_SHORT).show();
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
                params.put("id",id);
                params.put("quantity", String.valueOf(quantity));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void showAlert(String id) {

        new AlertDialog.Builder(context)
                .setTitle(" Delete Item")
                .setIcon(R.drawable.delete)
                .setMessage("Do you want to Delete Item?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        deleteItem(id);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void deleteItem(String itemId) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://" + ip +"/restaurantManagement/api/deleteItem.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: "+response);

                if (response.equals("success")){
                    Toast.makeText(context, "Quantity Deleted", Toast.LENGTH_SHORT).show();

                    fragment.getCartDetails();
                }else{

                    Toast.makeText(context, "Failed to Delete", Toast.LENGTH_SHORT).show();
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
                params.put("itemId",itemId);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        requestQueue.add(stringRequest);


    }
}
