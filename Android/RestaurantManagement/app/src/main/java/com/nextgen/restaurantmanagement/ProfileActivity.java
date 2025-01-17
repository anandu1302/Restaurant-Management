package com.nextgen.restaurantmanagement;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private static String TAG ="ProfileActivity";

    EditText nameET;
    EditText numberET;
    EditText emailET;
    EditText addressET;
    Button saveProfileBT;
    ImageView backIV;

    private GlobalPreference globalPreference;
    private String ip,uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        globalPreference = new GlobalPreference(this);
        ip = globalPreference.getIP();
        uid = globalPreference.getID();

        nameET = findViewById(R.id.pNameEditText);
        numberET = findViewById(R.id.pNumberEditText);
        emailET = findViewById(R.id.pEmailEditText);
        addressET = findViewById(R.id.pAddressEditText);
        saveProfileBT = findViewById(R.id.pSaveButton);
        backIV = findViewById(R.id.profileBackImageView);

        getUserDetails();

        saveProfileBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updateProfile();

            }
        });

        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(ProfileActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getUserDetails() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://" + ip + "/restaurantManagement/api/profile.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: "+response);

                if (!response.equals("")) ;
                {
                    try {

                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        JSONObject data = jsonArray.getJSONObject(0);

                        String name = data.getString("name");
                        String email = data.getString("email");
                        String number = data.getString("number");
                        String address = data.getString("address");

                        nameET.setText(name);
                        emailET.setText(email);
                        numberET.setText(number);
                        addressET.setText(address);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: "+error);


            }
        }) {
            @Override
            @Nullable

            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("uid", uid);
                return params;


            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(ProfileActivity.this);
        requestQueue.add(stringRequest);

    }

    private void updateProfile() {

        String URL = "http://"+ ip +"/restaurantManagement/api/editProfile.php";



        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(ProfileActivity.this,""+response,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ProfileActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProfileActivity.this,""+error,Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("name",nameET.getText().toString());
                params.put("number",numberET.getText().toString());
                params.put("email",emailET.getText().toString());
                params.put("address",addressET.getText().toString());
                params.put("uid",uid);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}