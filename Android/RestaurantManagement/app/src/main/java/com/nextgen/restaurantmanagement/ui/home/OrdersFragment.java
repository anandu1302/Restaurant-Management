package com.nextgen.restaurantmanagement.ui.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nextgen.restaurantmanagement.Adapter.OrdersAdapter;
import com.nextgen.restaurantmanagement.GlobalPreference;
import com.nextgen.restaurantmanagement.ModelClass.OrderedFoodModelClass;
import com.nextgen.restaurantmanagement.ModelClass.OrdersModelClass;
import com.nextgen.restaurantmanagement.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
   OrdersFragment
 */
public class OrdersFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match

    private static String TAG ="OrdersFragment";
    View view;
    RecyclerView ordersRV;
    ArrayList<OrdersModelClass> list;
    private GlobalPreference globalPreference;
    private String ip,uid;

    public OrdersFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static OrdersFragment newInstance(String param1, String param2) {
        OrdersFragment fragment = new OrdersFragment();
        Bundle args = new Bundle();

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
        view = inflater.inflate(R.layout.fragment_orders, container, false);

        ordersRV = view.findViewById(R.id.myOrdersRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        ordersRV.setLayoutManager(layoutManager);

        getOrders();



        return view;
    }

    private void getOrders() {

        list = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://" + ip + "/restaurantManagement/api/viewOrders.php?uid=" + uid, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: " + response);

                if (response.equals("failed")) {
                    Toast.makeText(getContext(), "No Orders Available", Toast.LENGTH_SHORT).show();

                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            String id = object.getString("id");
                            String orderId = object.getString("order_id");
                            String price = object.getString("total_price");
                            String date = object.getString("date");
                            String itemCount = object.getString("items_count");
                            String status = object.getString("status");

                            List<OrderedFoodModelClass> foodList = new ArrayList<>();

                            if (object.has("food_details")) {
                                JSONArray foodDetailsArray = object.getJSONArray("food_details");
                                for (int j = 0; j < foodDetailsArray.length(); j++) {
                                    JSONObject foodObject = foodDetailsArray.getJSONObject(j);
                                    String foodName = foodObject.getString("name");
                                    String restName = foodObject.getString("rest_name");
                                    String restPlace = foodObject.getString("rest_place");
                                    String restImage = foodObject.getString("rest_image");

                                    // Add food details to the list
                                    foodList.add(new OrderedFoodModelClass(foodName, restName,restPlace,restImage));
                                }
                            }


                            list.add(new OrdersModelClass(id, orderId, price,date,status,itemCount,foodList));

                        }

                        OrdersAdapter adapter = new OrdersAdapter(list, getContext());
                        ordersRV.setAdapter(adapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: " + error);
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
}