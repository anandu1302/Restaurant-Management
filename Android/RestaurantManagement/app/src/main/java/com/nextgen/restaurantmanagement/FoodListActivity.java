package com.nextgen.restaurantmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nextgen.restaurantmanagement.Adapter.FoodAdapter;
import com.nextgen.restaurantmanagement.ModelClass.FoodModelClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FoodListActivity extends AppCompatActivity {

    private static String TAG ="FoodListActivity";

    RecyclerView foodRV;
    ArrayList<FoodModelClass> list;

    private GlobalPreference globalPreference;
    private String ip,uid;

    private ImageView BackIV;
    private TextView appBarTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        globalPreference = new GlobalPreference(this);
        ip = globalPreference.getIP();
        uid = globalPreference.getID();

        Intent intent = getIntent();
        String response = intent.getStringExtra("response");
        int pos = intent.getIntExtra("pos",0);

        foodRV = findViewById(R.id.foodListRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        foodRV.setLayoutManager(layoutManager);

        BackIV = findViewById(R.id.BackImageButton);
        appBarTV = findViewById(R.id.appBarTitle);
        appBarTV.setText("Food Menu");

        getFoodList(response);

        BackIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(FoodListActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void getFoodList(String response) {

        list = new ArrayList<>();

        try{
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int i=0; i< jsonArray.length();i++){
                JSONObject object = jsonArray.getJSONObject(i);
                String id = object.getString("id");
                String name = object.getString("name");
                String price = object.getString("price");
                String category = object.getString("category");
                String description = object.getString("description");
                String image = object.getString("food_image");


                list.add(new FoodModelClass(id,name,price,category,description,image));

            }

            FoodAdapter adapter = new FoodAdapter(list,FoodListActivity.this);
            foodRV.setAdapter(adapter);

        } catch(JSONException e){
            e.printStackTrace();
        }
    }
}