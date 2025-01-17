package com.nextgen.restaurantmanagement;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

import java.util.HashMap;
import java.util.Map;

public class ComplaintActivity extends AppCompatActivity {

    TextView orderIdTV;
    String orderId;
    TextView appBarTV;
    ImageView backIV;
    EditText complaintET;
    Button submitBT;

    private GlobalPreference globalPreference;
    String ip,uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);

        globalPreference = new GlobalPreference(this);
        ip = globalPreference.getIP();
        uid = globalPreference.getID();

        orderId = getIntent().getStringExtra("orderId");

        orderIdTV = findViewById(R.id.cOrderNumberTextView);
        appBarTV = findViewById(R.id.appBarTitle);
        backIV = findViewById(R.id.BackImageButton);
        complaintET = findViewById(R.id.cDescriptionEditText);
        submitBT = findViewById(R.id.cSubmitButton);

        appBarTV.setText("Complaint");

        orderIdTV.setText(orderId);

        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ComplaintActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        submitBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                makeComplaint();
            }
        });



    }

    private void makeComplaint() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+ ip +"/restaurantManagement/api/complaint.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("success")){

                    Toast.makeText(ComplaintActivity.this,"Submitted",Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(ComplaintActivity.this,HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(ComplaintActivity.this,""+response,Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ComplaintActivity.this,""+error,Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            @Nullable
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("uid",uid);
                params.put("orderId",orderId);
                params.put("description",complaintET.getText().toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(ComplaintActivity.this);
        requestQueue.add(stringRequest);
    }
}