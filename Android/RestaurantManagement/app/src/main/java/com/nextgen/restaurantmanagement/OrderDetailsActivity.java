package com.nextgen.restaurantmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nextgen.restaurantmanagement.Adapter.CartAdapter;
import com.nextgen.restaurantmanagement.Adapter.FoodAdapter;
import com.nextgen.restaurantmanagement.Adapter.FoodListAdapter;
import com.nextgen.restaurantmanagement.ModelClass.CartModelClass;
import com.nextgen.restaurantmanagement.ModelClass.FoodListModelClass;
import com.nextgen.restaurantmanagement.ui.home.CartFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OrderDetailsActivity extends AppCompatActivity {

    private static String TAG ="OrderDetailsActivity";

    TextView restNameTV;
    TextView restPlaceTV;
    TextView orderStatusTV;
    TextView itemCountTV;
    TextView orderNoTV;
    TextView orderDateTV;
    TextView complaintTV;
    TextView totalTV;
    LinearLayout backHomeLL;
    ImageView backIV;
    RecyclerView foodListRV;
    ArrayList<FoodListModelClass> list;
    String orderId;
    String restName;
    String restPlace;
    String orderStatus;
    String date;
    String totalAmount;
    String itemCount;

    private GlobalPreference globalPreference;
    private String ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        globalPreference = new GlobalPreference(this);
        ip = globalPreference.getIP();

        backIV = findViewById(R.id.orderBackImageView);

        orderId = getIntent().getStringExtra("orderId");
        restName = getIntent().getStringExtra("restoName");
        restPlace = getIntent().getStringExtra("restoPlace");
        orderStatus = getIntent().getStringExtra("status");
        date = getIntent().getStringExtra("date");
        totalAmount = getIntent().getStringExtra("amount");
        itemCount = getIntent().getStringExtra("itemCount");

        restNameTV = findViewById(R.id.dRestNameTextView);
        restPlaceTV = findViewById(R.id.dRestPlaceTextView);
        orderStatusTV = findViewById(R.id.dOrderStatusTextView);
        itemCountTV = findViewById(R.id.dItemCountTextView);
        orderNoTV = findViewById(R.id.dOrderNumberTextView);
        orderDateTV = findViewById(R.id.dOrderDateTextView);
        complaintTV = findViewById(R.id.dComplaintTV);
        foodListRV = findViewById(R.id.orderFoodListRecyclerView);
        totalTV = findViewById(R.id.dTotalTextView);
        backHomeLL = findViewById(R.id.dBackHomeLL);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        foodListRV.setLayoutManager(layoutManager);

        restNameTV.setText(restName);
        restPlaceTV.setText(restPlace);
        orderStatusTV.setText("Order Status : "+orderStatus);
        orderNoTV.setText("#"+orderId);
        orderDateTV.setText(date);
        totalTV.setText("₹ "+totalAmount);


        if (itemCount.equals("1")){

           itemCountTV.setText(itemCount + " Item");

        }else{
            itemCountTV.setText(itemCount + " Items");
        }

        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(OrderDetailsActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        complaintTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(OrderDetailsActivity.this,ComplaintActivity.class);
                intent.putExtra("orderId",orderId);
                startActivity(intent);
            }
        });

        getFoodList();

        backHomeLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(OrderDetailsActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    private void getFoodList() {

        list = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://"+ ip +"/restaurantManagement/api/orderHistory.php?orderId="+orderId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: "+response);

                if (response.equals("failed")){

                    Toast.makeText(OrderDetailsActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                }
                else{
                    try{
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i=0; i< jsonArray.length();i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            String id = object.getString("id");
                            String fname = object.getString("fname");
                            String quantity = object.getString("quantity");
                            String price = object.getString("price");

                            list.add(new FoodListModelClass(id,fname,quantity,price));

                        }

                        FoodListAdapter adapter = new FoodListAdapter(list,OrderDetailsActivity.this);
                        foodListRV.setAdapter(adapter);

                    } catch(JSONException e){
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: "+error);
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(OrderDetailsActivity.this);
        requestQueue.add(stringRequest);

    }
}