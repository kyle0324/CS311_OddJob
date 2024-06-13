package com.example.wardenfront;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.wardenfront.app.AppController;

import org.json.JSONArray;

import com.example.wardenfront.JobBoardCard_Model;
import com.example.wardenfront.net_utils.Const;

import java.util.ArrayList;

/**
 * @author Ayden Boehme
 *
 * Job discover page with a recycler view that can be updated with the
 * newest list of jobs from the backend at a click of a button.
 */

public class JobDiscoveryActivity extends AppCompatActivity {

    private ArrayList jobCardArrayList = new ArrayList<JobBoardCard_Model>();

    private Button discoverBtn;

    private JobBoard_Adapter jobBoardAdapter;
    private JobBoard_Adapter.RecyclerViewClickListener listener;
    private JSONArray jobArr;

    private LinearLayoutManager linearLayoutManager;

    private RecyclerView jobBoardRV;

    private String userID;
    private String tag_arr_Req = "jsonArr_req";
    private String TAG = com.example.wardenfront.JobDiscoveryActivity.class.getSimpleName();
    // These tags will be used to cancel the requests
    private String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_discovery);

        userID = getIntent().getStringExtra("userID");
        jobBoardRV = findViewById(R.id.jobBoardRV);
        jobArr = new JSONArray();

        // Here, we have created new array list and added data to it
        jobCardArrayList = new ArrayList<JobBoardCard_Model>();


        setOnClickListener();
        // we are initializing our adapter class and passing our arraylist to it.
        jobBoardAdapter = new JobBoard_Adapter(this, jobCardArrayList, listener);

        //Put into the Discover Jobs intent

        // below line is for setting a layout manager for our recycler view.
        // here we are creating vertical list so we will provide orientation as vertical
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        // Setting up the layoutManager and Adapter to our recycler view.
        jobBoardRV.setLayoutManager(linearLayoutManager);
        jobBoardRV.setAdapter(jobBoardAdapter);


        /**
         * "Discover jobs" button - onClick this button updates the list of jobs
         */
        discoverBtn = findViewById(R.id.discoverBtn);
        discoverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeJsonArrayReq();
            }
        });
    }

    /**
     * JsonArrayRequest for the newest list of jobs.
     *    onResponse - 1. Clears job list to prepare for a new array request
     *                 2. Searches the whole JSONArray for the needed values of each JSONObject
     *                        in the array in order to make a jobCard model for each JSONObject
     *                 3. Notifies the Adapter that changes have been made to the jobCardArrayList
     */
    private void makeJsonArrayReq() {
        JsonArrayRequest req = new JsonArrayRequest(
                Const.URL_jobPosts,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        jobArr = response;

                        jobCardArrayList.clear();
                        for(int i = 0; i < jobArr.length(); i++){
                            jobCardArrayList.add(
                                    new JobBoardCard_Model(
                                    jobArr.optJSONObject(i).optString("title"),
                                    jobArr.optJSONObject(i).optString("jobDate"),
                                    jobArr.optJSONObject(i).optString("hourlyPay"),
                                    String.valueOf(jobArr.optJSONObject(i).optInt("id"))
                            ));
                        }
                        jobBoardAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                    }
                }
        );

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req, tag_json_arry);
    }

    /**
     * Click listener set for job cards that does the following:
     *   When a job clicked, the job's associated details page open's up.
     *
     * (@author Kyle Clements)
     */
    private void setOnClickListener() {
        listener = new JobBoard_Adapter.RecyclerViewClickListener() {
            @Override
            public void onclick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), JobPostDisplay.class);
                intent.putExtra("userId", userID);

                JobBoardCard_Model thisJob = (JobBoardCard_Model) jobCardArrayList.get(position);
                intent.putExtra("jobID", thisJob.getID());

                intent.putExtra("userType", "poster");
                startActivity(intent);
            }
        };
    }
}