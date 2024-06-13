package com.example.wardenfront;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.Button;
import android.widget.ImageButton;


import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.wardenfront.app.AppController;
import com.example.wardenfront.net_utils.Const;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * @author Kyle Clements and Ayden Boehme
 *
 * This is the main menu for the seeker user
 */
public class MMSeeker extends AppCompatActivity {

    private ArrayList jobCardArrayList = new ArrayList<JobBoardCard_Model>();

    private Button toProfile;
    private Button toSearch;
    private Button gtJobBoard;
    private Button toChat;
    private Button recommendedJobs;
    private Button subscribedJobs;

    private ImageButton toInbox;

    private JobBoard_Adapter_MM jobBoardAdapter;
    private JobBoard_Adapter_MM.RecyclerViewClickListener listener;
    private JSONArray jobArr;

    private LinearLayoutManager linearLayoutManager;

    private RecyclerView jobBoardRV;

    private String userID;
    private String TAG = com.example.wardenfront.MMSeeker.class.getSimpleName();

    // These tags will be used to cancel the requests
    private String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_m_seeker);

        Intent intent = getIntent();
        userID = intent.getStringExtra("userID");

        gtJobBoard = findViewById(R.id.GoToJobBoard);
        gtJobBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MMSeeker.this, JobDiscoveryActivity.class);
                intent.putExtra("userId", userID);
                startActivity(intent);
            }
        });

        toInbox = findViewById(R.id.MMPtoInbox);
        toInbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MMSeeker.this, InboxActivity.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });

        toProfile = findViewById(R.id.GT_ProfilePage);
        toProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MMSeeker.this, ProfilePage_test.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });

        toSearch = findViewById(R.id.btn_search);
        toSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MMSeeker.this, SearchBarActivity.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });

        recommendedJobs = findViewById(R.id.RecommendedJobs);
        recommendedJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRecommendedJobs();
            }
        });

        subscribedJobs = findViewById(R.id.SeeRequests);
        subscribedJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mySubscribedJobs();
            }
        });


        jobBoardRV = findViewById(R.id.seekerRV);
        jobArr = new JSONArray();

        // Here, we have created new array list and added data to it
        jobCardArrayList = new ArrayList<JobBoardCard_Model>();


        setOnClickListener();
        // we are initializing our adapter class and passing our arraylist to it.
        jobBoardAdapter = new JobBoard_Adapter_MM(this, jobCardArrayList, listener);

        //Put into the Discover Jobs intent

        // below line is for setting a layout manager for our recycler view.
        // here we are creating vertical list so we will provide orientation as vertical
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        // Setting up the layoutManager and Adapter to our recycler view.
        jobBoardRV.setLayoutManager(linearLayoutManager);
        jobBoardRV.setAdapter(jobBoardAdapter);

        myRecommendedJobs();

        toChat = findViewById(R.id.GT_chats);
        toChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MMSeeker.this, ChatMenu.class);

                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });
    }

    private void myRecommendedJobs() {
        JsonArrayRequest req = new JsonArrayRequest(
                Const.URL_jobPosts,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        jobArr = response;

                        jobCardArrayList.clear();
                        for (int i = 0; i < jobArr.length(); i++) {
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

    private void mySubscribedJobs() {
        JsonArrayRequest req = new JsonArrayRequest(
                Const.mySubscribedJobsUrl + userID,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        jobArr = response;

                        jobCardArrayList.clear();
                        for (int i = 0; i < jobArr.length(); i++) {
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
        listener = new JobBoard_Adapter_MM.RecyclerViewClickListener() {
            @Override
            public void onclick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), JobPostDisplay.class);
                intent.putExtra("userId", userID);

                JobBoardCard_Model thisJob = (JobBoardCard_Model) jobCardArrayList.get(position);
                intent.putExtra("jobID", thisJob.getID());
                startActivity(intent);
            }
        };
    }
}

