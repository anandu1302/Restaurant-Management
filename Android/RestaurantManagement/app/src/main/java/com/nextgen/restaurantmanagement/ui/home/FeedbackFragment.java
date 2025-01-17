package com.nextgen.restaurantmanagement.ui.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nextgen.restaurantmanagement.GlobalPreference;
import com.nextgen.restaurantmanagement.HomeActivity;
import com.nextgen.restaurantmanagement.R;

import java.util.HashMap;
import java.util.Map;

/**
   FeedbackFragment
 */
public class FeedbackFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    View view;
    private static final String TAG = "FeedbackActivity";

    RatingBar mRatingBar;
    TextView mRatingScale;
    EditText commentET;
    Button submitBT;
    private GlobalPreference globalPreference;
    private String ip,uid;

    public FeedbackFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static FeedbackFragment newInstance(String param1, String param2) {
        FeedbackFragment fragment = new FeedbackFragment();
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
        view = inflater.inflate(R.layout.fragment_feedback, container, false);

        mRatingBar = view.findViewById(R.id.ratingBar);
        mRatingScale = view.findViewById(R.id.tvRatingScale);
        commentET = view.findViewById(R.id.commentEditText);
        submitBT =  view.findViewById(R.id.feedbackButton);

        mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                mRatingScale.setText(String.valueOf(v));
                switch ((int) ratingBar.getRating()) {
                    case 1:
                        mRatingScale.setText("Very bad");
                        break;
                    case 2:
                        mRatingScale.setText("Need some improvement");
                        break;
                    case 3:
                        mRatingScale.setText("Good");
                        break;
                    case 4:
                        mRatingScale.setText("Great");
                        break;
                    case 5:
                        mRatingScale.setText("Awesome. I love it");
                        break;
                    default:
                        mRatingScale.setText("");
                }
            }
        });

        submitBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                submitFeedback();
            }
        });

        return view;
    }

    private void submitFeedback() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+ ip +"/restaurantManagement/api/feedback.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("success")){

                    Toast.makeText(getContext(), "Feedback Submitted", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getContext(), HomeActivity.class);
                    startActivity(intent);

                }else{
                    Toast.makeText(getContext(), ""+response, Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), ""+error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            @Nullable
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("uid",uid);
                params.put("rating",String.valueOf(mRatingBar.getRating()));
                params.put("comments",commentET.getText().toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
}