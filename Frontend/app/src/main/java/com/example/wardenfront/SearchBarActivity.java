package com.example.wardenfront;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.wardenfront.app.AppController;
import com.example.wardenfront.net_utils.Const;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchBarActivity extends AppCompatActivity {

    private ArrayList jobSearchArrayList = new ArrayList<JobBoardCard_Model>();
    private ArrayList userSearchArrayList = new ArrayList<>();//TODO

    private Button searchUsers;
    private Button searchJobs;
    private EditText inputBar;

    private Search_Adapter searchAdapter;
    private Search_Adapter.RecyclerViewClickListener listener;
    private JSONArray searchArr;

    private LinearLayoutManager linearLayoutManager;

    private RecyclerView searchRV;

    private String userID;
    private String inputString;
    private String tag_arr_Req = "jsonArr_req";
    private String TAG = com.example.wardenfront.JobDiscoveryActivity.class.getSimpleName();
    // These tags will be used to cancel the requests
    private String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);
        inputBar = findViewById(R.id.searchBar);

        userID = getIntent().getStringExtra("userID");
        searchRV = findViewById(R.id.searchRV);
        searchArr = new JSONArray();

        // Here, we have created new array list and added data to it
        jobSearchArrayList = new ArrayList<JobBoardCard_Model>();
        userSearchArrayList = new ArrayList<>();//TODO

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        searchJobs = findViewById(R.id.btn_searchJobs);
        searchJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputString = "";
                inputString = inputBar.getText().toString();
                setJobClickListener();

                searchAdapter = new Search_Adapter(this, userSearchArrayList, listener);
                searchRV.setLayoutManager(linearLayoutManager);
                searchRV.setAdapter(searchAdapter);

                searchJobsGet();
            }
        });

        searchUsers = findViewById(R.id.btn_searchUsers);
        searchUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputString = "";
                inputString = inputBar.getText().toString();
                setUserClickListener();

                searchAdapter = new Search_Adapter(this, userSearchArrayList, listener);
                searchRV.setLayoutManager(linearLayoutManager);
                searchRV.setAdapter(searchAdapter);

                searchUsersGet();
            }
        });
    }

    private void searchUsersGet() {
        JsonArrayRequest req = new JsonArrayRequest(
                Const.searchUserUrl + inputString,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        searchArr = response;

                        userSearchArrayList.clear();
                        for(int i = 0; i < searchArr.length(); i++){
                            userSearchArrayList.add(
                                    new JobBoardCard_Model(
                                            searchArr.optJSONObject(i).optString("userName"),
                                            searchArr.optJSONObject(i).optString("email"),
                                            searchArr.optJSONObject(i).optString("userType"),
                                            String.valueOf(searchArr.optJSONObject(i).optInt("id"))
                                    ));
                        }
                        searchAdapter.notifyDataSetChanged();
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

    private void searchJobsGet() {
        JsonArrayRequest req = new JsonArrayRequest(
                Const.searchJobUrl + inputBar.getText().toString(),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        searchArr = response;

                        userSearchArrayList.clear();
                        for(int i = 0; i < searchArr.length(); i++){
                            userSearchArrayList.add(
                                    new JobBoardCard_Model(
                                            searchArr.optJSONObject(i).optString("title"),
                                            searchArr.optJSONObject(i).optString("jobDate"),
                                            searchArr.optJSONObject(i).optString("hourlyPay"),
                                            String.valueOf(searchArr.optJSONObject(i).optInt("id"))
                                    ));
                        }
                        searchAdapter.notifyDataSetChanged();
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

    private void setUserClickListener() {
        listener = new Search_Adapter.RecyclerViewClickListener() {
            @Override
            public void onclick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), ViewProfilePage.class);
                intent.putExtra("userID", userID);

                JobBoardCard_Model thisUser = (JobBoardCard_Model) userSearchArrayList.get(position);
                intent.putExtra("ownerID", thisUser.getID());
                startActivity(intent);
            }
        };
    }

    private void setJobClickListener() {
        listener = new Search_Adapter.RecyclerViewClickListener() {
            @Override
            public void onclick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), JobPostDisplay.class);
                intent.putExtra("userId", userID);

                JobBoardCard_Model thisJob = (JobBoardCard_Model) userSearchArrayList.get(position);
                intent.putExtra("jobID", thisJob.getID());

                intent.putExtra("userType", "poster");
                startActivity(intent);
            }
        };
    }
}
