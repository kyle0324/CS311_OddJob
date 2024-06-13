package com.example.wardenfront;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.example.wardenfront.app.AppController;
import com.example.wardenfront.net_utils.Const;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * @author Kyle Clements
 *
 * This displays the data of a profile page.  The profile is based off the intent.
 */
public class ViewProfilePage extends AppCompatActivity {

    private String TAG = com.example.wardenfront.ViewProfilePage.class.getSimpleName();
    private String tag_get_req = "get_user_req";
    private String tag_ban_req = "post_ban_req";

    private TextView name;
    private TextView location;
    private TextView role;
    private TextView description;

    private Button report;
    private Button toReviews;
    private Button toCreateReview;

    private String userID;
    private String profileOwnerID;
    private String reportedName;
    private String userType = "";

    private JSONObject banObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile_page);

        name = findViewById(R.id.editTextTextPersonName3);
        location = findViewById(R.id.editTextTextPersonName4);
        role = findViewById(R.id.textView9);
        description = findViewById(R.id.editTextTextMultiLine);

        Intent intent = getIntent();
        userID = intent.getStringExtra("userID");
        profileOwnerID = intent.getStringExtra("profileOwnerID");
        userType = intent.getStringExtra("userType");

        getUser();
        report = findViewById(R.id.toReportUser);
        if(userType != null) {
            if (userType.contentEquals("admin")) {
                report.setText("Ban User");
            }
        }

        //report = findViewById(R.id.toReportUser);
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(report.getText().toString().contentEquals("Ban User")){
                    banObject = new JSONObject();
                    reportedName = name.getText().toString();
                    try {
                        banObject.put("userName", reportedName);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    banrequest();
                }
                else {
                    Intent intent = new Intent(ViewProfilePage.this, ReportUserActivity.class);
                    intent.putExtra("reportedID", profileOwnerID);

                    startActivity(intent);
                }
            }
        });

        toReviews = findViewById(R.id.btn_toReviews);
        toReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewProfilePage.this, UserReviewsActivity.class);
                intent.putExtra("userID", userID);

                startActivity(intent);
            }
        });

        toCreateReview = findViewById(R.id.btn_createReview);
        toCreateReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewProfilePage.this, CreateUserReviewActivity.class);
                intent.putExtra("reviewerID", userID);
                intent.putExtra("reviewedUserID", profileOwnerID);

                startActivity(intent);
            }
        });


    }

    private void banrequest() {
        JsonObjectRequest JsonObjectReq = new JsonObjectRequest(Method.POST, Const.URL_Ban, banObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        try{
                            if(response.get("response").toString().contentEquals("success")){
                                report.setText("successful");
                            }
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        AppController.getInstance().addToRequestQueue(JsonObjectReq, tag_ban_req);
    }

    /**
     * This request grabs info from the database based off the ID we grabbed from the intent.
     */
    private void getUser() {
        JsonObjectRequest JsonObjectReq = new JsonObjectRequest(Method.GET,
                Const.Url_getUser + profileOwnerID,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        try {
                            name.setText(response.get("userName").toString());
                            location.setText(response.get("address").toString());
                            role.setText(response.get("userType").toString());
                            description.setText("Email: " + response.get("email").toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                    }
                });
        AppController.getInstance().addToRequestQueue(JsonObjectReq, tag_get_req);
    }
}