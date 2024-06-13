package com.example.wardenfront;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.wardenfront.app.AppController;
import com.example.wardenfront.net_utils.Const;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ayden Boehme
 *
 *
 */
public class ProfilePage_test extends AppCompatActivity {

    private JSONObject profileUpdate;

    private String myUserID;
    private String TAG = ProfilePage_test.class.getSimpleName();
    private String tag_json_obj = "jobj_req";

    private Button toMyReviews;
    private Button saveProfile;

    private EditText username;
    private EditText location;
    private EditText email;
    private TextView userType;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        Intent intent = getIntent();
        myUserID = intent.getStringExtra("userID");
        username = findViewById(R.id.editMyUsername);
        location = findViewById(R.id.editLocation);
        userType = findViewById(R.id.myUserTypeView);
        email = findViewById(R.id.editMyDescription);
        getProfile();

        toMyReviews = findViewById(R.id.btn_toMyReviews);
        toMyReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilePage_test.this, UserReviewsActivity.class);
                intent.putExtra("userID", myUserID);
                startActivity(intent);
            }
        });

        saveProfile = findViewById(R.id.btn_saveProfile);
        saveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileUpdate = new JSONObject();
                try {
                    profileUpdate.put("userName", username.getText().toString());
                    profileUpdate.put("address", location.getText().toString());
                    profileUpdate.put("email", email.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                updateProfilePostRequest();
            }
        });
    }

    private void updateProfilePostRequest() {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Const.URL_UpdateUserProfile + myUserID,
                profileUpdate,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "Done: " + response.toString());
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("userName", username.getText().toString());
                params.put("address", location.getText().toString());
                params.put("email", email.getText().toString());

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

        // Cancelling request
        // ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_obj);
    }

    private void getProfile() {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                Const.Url_getUser + myUserID,
                null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "Done: " + response.toString());
                        try {
                            username.setText(response.get("userName").toString());
                            location.setText(response.get("address").toString());
                            userType.setText(response.get("userType").toString());
                            email.setText(response.get("email").toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        }) {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("userName", username.toString());
                params.put("address", location.toString());
                params.put("email", email.toString());

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

        // Cancelling request
        // ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_obj);
    }
}