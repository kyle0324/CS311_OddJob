package com.example.wardenfront;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.wardenfront.app.AppController;
import com.example.wardenfront.net_utils.Const;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Kyle Clements
 *
 * displays the jobs details by taking in the data from the intent
 */
public class JobPostDisplay extends AppCompatActivity {

    private String TAG = com.example.wardenfront.JobPostDisplay.class.getSimpleName();
    private String tag_json_obj = "json_obj_req";
    private String userID;
    private String jobID;
    private String profileOwnerID;
    private JSONObject Interested;
    private JSONObject NotInterested;

    private TextView title;
    private TextView author;
    private TextView desc;
    private TextView time;
    private TextView pay;

    private Button BInterested;
    private Button BnotInterested;
    private Button toAuthorProfile;
    private Button Abort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_post_display);

        title = findViewById(R.id.Title);
        author = findViewById(R.id.Author);
        desc = findViewById(R.id.Desc);
        pay = findViewById(R.id.Pay);
        time = findViewById(R.id.Time);

        Intent intent = getIntent();
        jobID = intent.getStringExtra("jobID"); //will need to code other side.  Need to put string in intent with intent.putextra
        userID = intent.getStringExtra("userID");

        getjobJson(); //grabs json
        getAuthorJson(); //grabs author user

        BInterested = findViewById(R.id.interested);

        BInterested.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (BInterested.getText().toString().contentEquals("success")){

                }
                else {
                    /*
                    //this is where we send the post request to update.
                    Interested = new JSONObject();
                    try {
                        Interested.put("userID", userID);
                        Interested.put("jobPostsID", jobID);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    showInterestRequest();
                } */
                    BInterested.setText("success");
                    BnotInterested.setText("Not Interested");
                }

            }
        });

        BnotInterested = findViewById(R.id.NotInterested);
        BnotInterested.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //this is where we send the post request to not want
                NotInterested = new JSONObject();
                try {
                    NotInterested.put("userID", userID);
                    NotInterested.put("jobPostsID", jobID);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                NotInterestedRequest();
                BnotInterested.setText("Success");
                BInterested.setText("Interested");
            }
        });

        toAuthorProfile = findViewById(R.id.ToAuProfile);
        toAuthorProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(JobPostDisplay.this, ViewProfilePage.class);
                intent2.putExtra("userID", userID);
                intent2.putExtra("profileOwnerID", profileOwnerID);
                startActivity(intent2);
            }
        });

        Abort = findViewById(R.id.abort);
        Abort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getAuthorJson() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Const.URL_GetAuthor + jobID,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                try {
                    author.setText(response.get("userName").toString());
                    profileOwnerID = response.get("id").toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjectRequest, tag_json_obj);
    }

    /**
     * sends a post request to the server that will remove them from the interested list for this job in the database.
     */
    private void NotInterestedRequest() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                Const.Url_interested,
                NotInterested, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String s = null;
                try {
                    s = response.get("response").toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (s.contentEquals("success")){
                    BnotInterested.setText(s);
                    BInterested.setText("Interested");
                };

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjectRequest, tag_json_obj);
    }

    /**
     * This grabs the data needed to fill the screen.  We get the jobId from intent.
     */
    private void getjobJson() {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                Const.Url_jobPost_display + jobID, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        try {
                            title.setText(response.get("title").toString());
                            pay.append(" " + response.get("hourlyPay").toString());
                            desc.append("\n " + response.get("description").toString());
                            author.append(" " + response.get("owner").toString());
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

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

        // Cancelling request
        // ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_obj);
    }

    /**
     * This sends a request to add a user to the list of interested users for this particular job request.
     */
    private void showInterestRequest(){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Const.Url_interested,
                Interested, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String s = null;
                try {
                    s = response.get("response").toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (s.contentEquals("success")){
                    BInterested.setText(s);
                    BnotInterested.setText("Not Interested");
                };

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjectRequest, tag_json_obj);
    }
}