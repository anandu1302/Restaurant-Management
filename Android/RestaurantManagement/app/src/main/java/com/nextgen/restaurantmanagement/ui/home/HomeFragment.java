package com.nextgen.restaurantmanagement.ui.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.nextgen.restaurantmanagement.FoodDetailsActivity;
import com.nextgen.restaurantmanagement.FoodListActivity;
import com.nextgen.restaurantmanagement.GlobalPreference;
import com.nextgen.restaurantmanagement.LoginActivity;
import com.nextgen.restaurantmanagement.MainActivity;
import com.nextgen.restaurantmanagement.ProfileActivity;
import com.nextgen.restaurantmanagement.R;
import com.nextgen.restaurantmanagement.databinding.FragmentHomeBinding;

import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";

    private FragmentHomeBinding binding;
    View view;

    TextView userNameTV;
    ImageView qrIV;
    ImageView homeMenuIV;
    private GlobalPreference globalPreference;
    private String ip,name;

    private IntentIntegrator qrScan;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        globalPreference = new GlobalPreference(getContext());
        ip = globalPreference.getIP();
        name = globalPreference.getName();


    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentHomeBinding.inflate(inflater, container, false);
        view = binding.getRoot();

        userNameTV = view.findViewById(R.id.userNameTextView);
        qrIV = view.findViewById(R.id.qrImageView);
        homeMenuIV = view.findViewById(R.id.homeMenuIV);
        userNameTV.setText(name);

        qrIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                qrScan.forSupportFragment(HomeFragment.this).initiateScan();

            
            }
        });

        homeMenuIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PopupMenu popupMenu = new PopupMenu(getContext(), homeMenuIV);

                // Inflating popup menu from popup_menu.xml file
                popupMenu.getMenuInflater().inflate(R.menu.home_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        // Toast message on menu item clicked

                        switch (menuItem.getItemId()){

                            case R.id.nav_driver_profile:
                                Intent aIntent  = new Intent(getContext(), ProfileActivity.class);
                                startActivity(aIntent);
                                return true;

                            case R.id.nav_driver_logout:
                                logout();
                                return true;
                        }
                        return true;
                    }
                });
                // Showing the popup menu
                popupMenu.show();
            }
        });

        return view;


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null){
            if (result.getContents() == null){
                Toast.makeText(getContext(),"Result Not Found",Toast.LENGTH_LONG).show();
            }
            else{
                //if qr contains data

                Toast.makeText(getContext(),"food id"+result.getContents(),Toast.LENGTH_SHORT).show();

                loadFoodDetails(result.getContents().trim());

            }
        } else{
            Toast.makeText(getContext(), "Result null", Toast.LENGTH_LONG).show();
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    private void loadFoodDetails(String restId) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://" + ip + "/restaurantManagement/api/getFoodList.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: "+response);

                if(response.equals("failed")){
                    Toast.makeText(getContext(), ""+response, Toast.LENGTH_SHORT).show();

                }else {

                    Intent intent = new Intent(getActivity(), FoodListActivity.class);
                    intent.putExtra("response", response);
                    intent.putExtra("pos", 0);
                    startActivity(intent);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: " + error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("restId",restId);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void logout() {

        new AlertDialog.Builder(getContext())
                .setMessage("Are you sure you want to Logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        startActivity(intent);

                    }
                })
                .setNegativeButton("No", null)
                .show();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}