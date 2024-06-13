package com.example.wardenfront;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.wardenfront.app.AppController;
import com.example.wardenfront.net_utils.Const;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ayden Boehme
 *
 * Activity page where the user sets the values for the a new job (title,
 * date, hourlyPay, and description) and then posts this new job to backend.
 */
public class CreateJobPostActivity extends AppCompatActivity {

    private String TAG = CreateJobPostActivity.class.getSimpleName();
    private String userID;
    private TextView title;
    private TextView jobDate;
    private TextView hourlyPay;
    private TextView description;
    private ProgressDialog pDialog;
    private Button btnPost;
    private TextView jobsList;

    private JSONObject jobPost;

    private String tag_json_obj = "jobj_req";
    private String postJResponse;
    private String getJResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_job_post);

        Intent intent = getIntent();
        userID = intent.getStringExtra("userID");

        btnPost = (Button) findViewById(R.id.btn_postJob);
        jobsList = (TextView) findViewById(R.id.jobsCreated);

        title = (TextView) findViewById(R.id.insertJobTitle);
        jobDate = (TextView) findViewById(R.id.insertJobDate);
        hourlyPay = (TextView) findViewById(R.id.insertJobPrice);
        description = (TextView) findViewById(R.id.insertDetails);

        jobPost = new JSONObject();

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);


        /**
         * Upon clicking the "Create Job" button, the inputted text-box values are
         * put into a new job post JSONObject with the correct keys for each value.
         * After that, the new, filled out JSONObject is posted to the backend's
         * job list
         */
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    jobPost.put("title", title.getText().toString());
                    jobPost.put("jobDate", jobDate.getText().toString());
                    jobPost.put("hourlyPay", hourlyPay.getText().toString());
                    jobPost.put("description", description.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                makeJsonObjPostReq();
                finish();
            }
        });
    }

    private void makeJsonObjPostReq() {
        showProgressDialog();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Const.URL_JSON_JOB_CREATE + "/" + userID, jobPost,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "Done: " + response.toString());
                        postJResponse = response.toString();
                        hideProgressDialog();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hideProgressDialog();
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
                params.put("title", title.toString());
                params.put("jobDate", jobDate.toString());
                params.put("hourlyPay", hourlyPay.toString());
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