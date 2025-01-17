package com.nextgen.restaurantmanagement;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaymentActivity extends AppCompatActivity {

    private static final String TAG = "PaymentActivity";

    TextView amountTV;
    EditText cardNameET;
    EditText cardNumberET;
    EditText cvvET;
    Button makePaymentBT;
    RadioGroup cardTypeRG;

    private GlobalPreference globalPreference;
    private String ip,uid;

    List<String> month = new ArrayList<>();

    List<String> year = new ArrayList<>();
    private Spinner MMspin;
    private Spinner YYspin;

    String amount,note,tableNO;
    String cardType;
    private TextView appBarTitleTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        globalPreference = new GlobalPreference(this);
        ip = globalPreference.getIP();
        uid = globalPreference.getID();

        iniit();

        amount = getIntent().getStringExtra("amount");
        note = getIntent().getStringExtra("note");
        tableNO = getIntent().getStringExtra("tableNo");
        appBarTitleTV.setText("Card Details");

        Toast.makeText(PaymentActivity.this, amount+""+note, Toast.LENGTH_SHORT).show();


        cardTypeRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                RadioButton radioButton = findViewById(checkedId);
                cardType = radioButton.getText().toString();

            }
        });

        month.add("MM");
        for (int i = 1 ; i <= 12 ; i++)
        {
            month.add(String.valueOf(i));
        }

        year.add("YY");
        for(int i = 2022 ; i<=2027 ; i++ )
            year.add(String.valueOf(i));


        // Getting value from Spinners


        MMspin = (Spinner) findViewById(R.id.mmspinner);
        ArrayAdapter MM = new ArrayAdapter(this,android.R.layout.simple_spinner_item,month);
        MM.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        MMspin.setAdapter(MM);



        YYspin = (Spinner) findViewById(R.id.yyspinner);
        ArrayAdapter YY = new ArrayAdapter(this,android.R.layout.simple_spinner_item,year);
        YY.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        YYspin.setAdapter(YY);

        // Getting value from Radio Button

        amountTV.setText("₹ "+amount);

        makePaymentBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cardTypeRG.getCheckedRadioButtonId() == -1)
                {
                    Toast.makeText(PaymentActivity.this, "Please select a payment method ", Toast.LENGTH_SHORT).show();
                }
                else if(cardTypeRG.getCheckedRadioButtonId() == -1 && cardNumberET.getText().toString().equals("") ||
                        cvvET.getText().toString().equals("") || MMspin.getSelectedItem().toString().equals("MM") ||
                        YYspin.getSelectedItem().toString().equals("YY"))
                {
                    Toast.makeText(PaymentActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
                else if (cardNumberET.getText().toString().length() < 16){
                    Toast.makeText(PaymentActivity.this, "Please Enter a Valid Card Number", Toast.LENGTH_SHORT).show();

                }
                else {
                    payNow();

                }
            }
        });


    }

    private void payNow() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+ ip +"/restaurantManagement/api/payment.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: "+response);
                if (response.equals("success")){

                   // Toast.makeText(PaymentActivity.this, "Payment Success", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(PaymentActivity.this,OrderSuccessActivity.class);
                    startActivity(intent);
                    finish();

                }
                else{
                    Toast.makeText(PaymentActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PaymentActivity.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            @Nullable
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userId",uid);
                params.put("note",note);
                params.put("tableNo",tableNO);
                params.put("amount",amount);
                params.put("cardname",cardNameET.getText().toString());
                params.put("cardnumber",cardNumberET.getText().toString());
                params.put("cardtype",cardType);
                params.put("cardmonth",MMspin.getSelectedItem().toString());
                params.put("cardyear",YYspin.getSelectedItem().toString());
                params.put("cvv",cvvET.getText().toString());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(PaymentActivity.this);
        requestQueue.add(stringRequest);

    }

    private void iniit() {

        amountTV = findViewById(R.id.amountTextView);
        cardNameET = findViewById(R.id.cardNameEditText);
        cardNumberET = findViewById(R.id.cardNumberEditText);
        cvvET = findViewById(R.id.cvvEditText);
        makePaymentBT = findViewById(R.id.makePaymentButton);
        cardTypeRG = findViewById(R.id.cardTypeRadioGroup);
        appBarTitleTV = findViewById(R.id.appBarTitleTextView);
    }
}