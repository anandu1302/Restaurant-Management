package com.nextgen.restaurantmanagement.ui.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nextgen.restaurantmanagement.Adapter.CartAdapter;
import com.nextgen.restaurantmanagement.GlobalPreference;
import com.nextgen.restaurantmanagement.ModelClass.CartModelClass;
import com.nextgen.restaurantmanagement.PaymentActivity;
import com.nextgen.restaurantmanagement.R;
import com.nextgen.restaurantmanagement.SplashActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
    CartFragment
 */
public class CartFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    View view;

    private static String TAG ="CartFragment";
    RecyclerView cartRV;
    ArrayList<CartModelClass> list;

    TextView cartSubTotalTV;
    TextView cartTotalTV;
    Button confirmOrderBT;

    private GlobalPreference globalPreference;
    private String ip,uid;
    int total;
    LinearLayout emptyCartLL;
    ScrollView cartSV;
    TextView basketTV;
    LinearLayout orderDetailsLL;
    EditText noteET;
    EditText tableNumberET;
    boolean orderTaken = false;


    public CartFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        globalPreference = new GlobalPreference(getContext());
        ip = globalPreference.getIP();
        uid = globalPreference.getID();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_cart, container, false);

        cartRV = view.findViewById(R.id.cartRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        cartRV.setLayoutManager(layoutManager);

        getCartDetails();

        cartSubTotalTV = view.findViewById(R.id.cartSubTotalTextView);
        cartTotalTV = view.findViewById(R.id.cartTotalTextView);
        confirmOrderBT = view.findViewById(R.id.confirmOrderButton);
        basketTV = view.findViewById(R.id.basketTextView);
        emptyCartLL = view.findViewById(R.id.emptyCartLL);
        cartSV = view.findViewById(R.id.cartScrollView);
        orderDetailsLL = view.findViewById(R.id.orderDetailsLL);
        noteET = view.findViewById(R.id.noteEditText);
        tableNumberET = view.findViewById(R.id.tableNumberEditText);

        confirmOrderBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (orderTaken){

                    if (tableNumberET.getText().toString().equals("")){

                        tableNumberET.setError("Enter Table Number");
                        Toast.makeText(getContext(), "Enter Table Number", Toast.LENGTH_SHORT).show();

                    }else{

                        Toast.makeText(getContext(), "order", Toast.LENGTH_SHORT).show();

                        orderNow();

                    }



                }
                orderDetailsLL.setVisibility(View.VISIBLE);

                orderTaken = true;

            }
        });


        return view;
    }

    private void orderNow() {

        Intent intent = new Intent(getContext(), PaymentActivity.class);
        intent.putExtra("amount",String.valueOf(total));
        intent.putExtra("note",noteET.getText().toString());
        intent.putExtra("tableNo",tableNumberET.getText().toString());
        startActivity(intent);
    }


    public void getCartDetails() {

        list = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://"+ ip +"/restaurantManagement/api/viewCart.php?uid="+uid, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: "+response);

                if (response.equals("failed")){

                    cartSV.setVisibility(View.GONE);
                    emptyCartLL.setVisibility(View.VISIBLE);
                    basketTV.setVisibility(View.GONE);
                }
                else{
                    cartSV.setVisibility(View.VISIBLE);
                    emptyCartLL.setVisibility(View.GONE);

                    try{
                        total = 0;

                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i=0; i< jsonArray.length();i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            String id = object.getString("id");
                            String itemName = object.getString("itemName");
                            String price = object.getString("price");
                            String totalPrice = object.getString("totalPrice");
                            String image = object.getString("image");
                            String quantity = object.getString("quantity");

                            total += Integer.parseInt(totalPrice);

                            Log.d(TAG, "onResponse: "+total);

                            list.add(new CartModelClass(id,itemName,price,image,quantity));

                        }

                        CartAdapter adapter = new CartAdapter(list,getContext(), CartFragment.this);
                        cartRV.setAdapter(adapter);
                        cartSubTotalTV.setText("₹ "+total);
                        cartTotalTV.setText("₹ "+total);

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
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }
}