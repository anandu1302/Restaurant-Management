package com.nextgen.restaurantmanagement;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FoodDetailsActivity extends AppCompatActivity {

    private static final String TAG = "FoodDetailsActivity";

    TextView foodNameTV;
    TextView foodDescTV;
    TextView foodQuantityTV;
    TextView foodTypeTV;
    TextView foodPriceTV;
    ImageView foodIV;
    ImageView backIV;
    ImageView addIV;
    ImageView subIV;
    Button addCartBT;
    String foodId;
    private GlobalPreference globalPreference;
    private String ip,uid;
    String totalAmount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);

        globalPreference = new GlobalPreference(this);
        ip = globalPreference.getIP();
        uid = globalPreference.getID();

        foodNameTV = findViewById(R.id.foodNameTextView);
        foodTypeTV = findViewById(R.id.foodTypeTextView);
        foodDescTV = findViewById(R.id.foodDescTextView);
        foodQuantityTV = findViewById(R.id.foodQuantityTextView);
        foodPriceTV = findViewById(R.id.foodPriceTextView);
        foodIV = findViewById(R.id.foodImageView);
        backIV = findViewById(R.id.foodBackImageView);
        addCartBT = findViewById(R.id.addCartButton);
        addIV = findViewById(R.id.addFoodImageView);
        subIV = findViewById(R.id.subFoodImageView);

        loadFoodDetails();

        addCartBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addCart();
            }
        });

        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(FoodDetailsActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }



    private void loadFoodDetails() {


            Intent intent = getIntent();

            foodId = intent.getStringExtra("id");
            String name = intent.getStringExtra("name");
            String price = intent.getStringExtra("name");
            String category = intent.getStringExtra("name");
            String desc = intent.getStringExtra("name");
            String image = intent.getStringExtra("name");

          //  Toast.makeText(this, foodId+"\n"+name+"\n"+price+""+category+"\n"+desc+"\n"+image, Toast.LENGTH_SHORT).show();

            foodNameTV.setText(name);
            foodTypeTV.setText(category);
            foodDescTV.setText(desc);
            foodPriceTV.setText("₹ "+price);

            totalAmount = price;

            if (!image.equals("")) {
                Glide.with(getApplicationContext())
                        .load("http://" + ip + "/restaurantManagement/food_tbl/" + image)
                        .into(foodIV);
            }

            addIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int quantity = Integer.parseInt(foodQuantityTV.getText().toString());
                    int foodPrice;

                    if (quantity < 10){

                        quantity = quantity + 1;
                        foodQuantityTV.setText(String.valueOf(quantity));

                        int amount = Integer.parseInt(price);

                        foodPrice = quantity * amount;

                        totalAmount = String.valueOf(foodPrice);

                        foodPriceTV.setText("₹ "+totalAmount);
                    }else {
                        Toast.makeText(FoodDetailsActivity.this, "Maximum quantity reached", Toast.LENGTH_SHORT).show();
                    }
                }
            });


            subIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int quantity = Integer.parseInt(foodQuantityTV.getText().toString());
                    int foodPrice;

                    if (quantity > 1) {
                        quantity = quantity - 1;
                        foodQuantityTV.setText(String.valueOf(quantity));

                        int amount = Integer.parseInt(price);

                        foodPrice = quantity * amount;

                        totalAmount = String.valueOf(foodPrice);

                        foodPriceTV.setText("₹ "+totalAmount);


                    } else {
                        Toast.makeText(FoodDetailsActivity.this, "Minimum quantity required", Toast.LENGTH_SHORT).show();
                    }

                }
            });
    }

    private void addCart() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://" + ip + "/restaurantManagement/api/addCart.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: "+response);

                if (response.equals("success")){

                    Toast.makeText(FoodDetailsActivity.this, "Successfully Added to Cart", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(FoodDetailsActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();


                }else {

                    Toast.makeText(FoodDetailsActivity.this, ""+response, Toast.LENGTH_SHORT).show();

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
                params.put("quantity", foodQuantityTV.getText().toString());
                params.put("totalAmount",totalAmount);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(FoodDetailsActivity.this);
        requestQueue.add(stringRequest);
    }
}