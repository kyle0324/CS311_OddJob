package com.example.wardenfront;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

public class CreateUserReviewActivity extends AppCompatActivity {

    private String TAG = CreateUserReviewActivity.class.getSimpleName();
    private String reviewerID;
    private String reviewedUserID;
    private String emptyReviewID;
    private String tag_json_obj = "jobj_req";

    private ProgressDialog pDialog;

    private Button btnFinishReview;

    private TextView title;
    private TextView rating;
    private TextView description;

    private JSONObject emptyReviewObj;
    private JSONObject reviewObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user_review);

        Intent intent = getIntent();
        reviewerID = intent.getStringExtra("reviewerID");
        reviewedUserID = intent.getStringExtra("reviewedUserID");

        title = (TextView) findViewById(R.id.insertReviewTitle);
        rating = (TextView) findViewById(R.id.insertReviewRating);
        description = (TextView) findViewById(R.id.insertReviewDetails);

        emptyReviewObj = new JSONObject();
        reviewObj = new JSONObject();

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);


        /**
         * Upon clicking the "Finish Review" button, the inputted text-box values are
         * put into a new user review JSONObject with the correct keys for each value.
         * After that, the new, filled out JSONObject is posted to the backend's
         * review list for the user that was reviewed
         */
        btnFinishReview = (Button) findViewById(R.id.btn_finishReview);
        btnFinishReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(title.getText().toString().length() < 1 || title.getText().toString()
                        .contentEquals("Please enter a title")
                ){
                    title.setText("Please enter a title");
                }
                else if (rating.getText().toString().isEmpty() || rating.getText().toString()
                        .contentEquals("Rating?")){
                    rating.setText("Rating?");
                }
                else if (description.getText().toString().isEmpty() || description.getText().toString()
                        .contentEquals("Please explain!")){
                    description.setText("Please explain!");
                }
                else {
                    try {
                        reviewObj.put("user", reviewedUserID);
                        reviewObj.put("reviewer", reviewerID);
                        reviewObj.put("title", title.getText().toString());
                        reviewObj.put("rating", rating.getText().toString());
                        reviewObj.put("description", description.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    createEmptyReviewPost();
                    writeToEmptyReview();
                    finish();
                }
            }
        });
    }

    private void createEmptyReviewPost() {
        showProgressDialog();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Const.createEmptyReviewUrl + reviewedUserID + "/" + reviewerID,
                emptyReviewObj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "Done: " + response.toString());
                        try {
                            response.get("id");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        hideProgressDialog();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hideProgressDialog();
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
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

        // Cancelling request
        // ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_obj);
    }

    private void writeToEmptyReview() {
        showProgressDialog();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Const.writeToReviewUrl + emptyReviewID + "/" + title + "/" + rating,
                reviewObj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "Done: " + response.toString());
                        hideProgressDialog();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hideProgressDialog();
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
                params.put("user", title.toString());
                params.put("reviewer", title.toString());
                params.put("title", rating.toString());
                params.put("rating", rating.toString());
                params.put("description", description.toString());

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

        // Cancelling request
        // ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_obj);
    }

    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }
}
