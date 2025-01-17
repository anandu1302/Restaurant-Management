package com.nextgen.restaurantmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class OrderSuccessActivity extends AppCompatActivity {

    LinearLayout backHomeLL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_success);

        backHomeLL = findViewById(R.id.backHomeLL);

        backHomeLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderSuccessActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}