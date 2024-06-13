package com.example.wardenfront;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.wardenfront.app.AppController;
//import com.example.wardenfront.databinding.FragmentMMPosterBinding;
import com.example.wardenfront.net_utils.Const;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Kyle Clements and Ayden Boehme
 *
 * This is the main menu for the poster user
 */
public class MMPoster extends AppCompatActivity {

    private Activity CreatePost;
    private WebSocketClient mWebSocketClient;

    private ArrayList jobCardArrayList = new ArrayList<JobBoardCard_Model>();

    private Button toDelete;
    private Button toCreate;
    private Button toProfile;
    private Button toChat;
    private Button toSearch;
    private ImageButton toInbox;

    private JobBoard_Adapter_MM jobBoardAdapter;
    private JobBoard_Adapter_MM.RecyclerViewClickListener listener;
    private JSONArray jobArr;

    private LinearLayoutManager linearLayoutManager;

    private RecyclerView jobBoardRV;

    private String userID;
    private String tag_arr_Req = "jsonArr_req";
    private String TAG = com.example.wardenfront.MMPoster.class.getSimpleName();
    // These tags will be used to cancel the requests
    private String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";

    private String username;

    private TextView greeting;
    private TextView messageInd;

    Button test;




    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_m_poster);

        Intent intent = getIntent();
        userID = intent.getStringExtra("userID");
        getuser();


        greeting = findViewById(R.id.Greeting);
        greeting.append(" " + userID);
        messageInd = findViewById(R.id.NewMessage);

        toCreate = findViewById(R.id.MMPtoCreate);
        toCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MMPoster.this, CreateJobPostActivity.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });

        toProfile = findViewById(R.id.MMPtoProfile);
        toProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MMPoster.this, ProfilePage_test.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });

        toSearch = findViewById(R.id.btnGetList);
        toSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MMPoster.this, SearchBarActivity.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });

        jobBoardRV = findViewById(R.id.jobsCreated);
        jobArr = new JSONArray();

        // Here, we have created new array list and added data to it
        jobCardArrayList = new ArrayList<JobBoardCard_Model>();


        setOnClickListener();
        // we are initializing our adapter class and passing our arraylist to it.
        jobBoardAdapter = new JobBoard_Adapter_MM(this, jobCardArrayList, listener);

        //Put into the Discover Jobs intent

        toChat = findViewById(R.id.MMPtoChats);
        toChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MMPoster.this, ChatMenu.class);
                intent.putExtra("id", userID);

                startActivity(intent);
            }
        });

        toInbox = findViewById(R.id.MMPtoInbox);
        toInbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MMPoster.this, InboxActivity.class);

                startActivity(intent);
            }
        });


        // below line is for setting a layout manager for our recycler view.
        // here we are creating vertical list so we will provide orientation as vertical
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        // Setting up the layoutManager and Adapter to our recycler view.
        jobBoardRV.setLayoutManager(linearLayoutManager);
        jobBoardRV.setAdapter(jobBoardAdapter);

        makeJsonArrayReq();
        connectWebSocket();

        test = findViewById(R.id.button3);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebSocketClient.send("success");
            }
        });

        toDelete = findViewById(R.id.MMPtoDelete);
        toDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MMPoster.this, DeleteAccountActivity.class);
                intent.putExtra("id", userID);
                startActivity(intent);
            }
        });
    }

    private void getuser() {
        JsonObjectRequest JsonObjectReq = new JsonObjectRequest(Request.Method.GET,
                Const.Url_getUser + userID,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        try {
                            username = response.get("userName").toString();
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
        AppController.getInstance().addToRequestQueue(JsonObjectReq, tag_json_obj);
    }

    protected void onDestroy(Bundle savedInstanceState){

    }




    private void makeJsonArrayReq() {
        JsonArrayRequest req = new JsonArrayRequest(
                Const.myCreatedPostsUrl + userID,
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

    private void connectWebSocket() {
        URI uri;
        try {
            /**
             * To test the clientside w/o the backend, connect to an echo server
             * Example: "ws://echo.websocket.org"
             */
            // uri = new URI("ws://10.0.2.2:8080/example"); //10.0.2.2 = localhost
            uri = new URI(Const.URL_Websocket_Inbox + username);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }


        mWebSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake handshake){
                Log.i("Websocket", "Opened");
            }
            @Override
            public void onMessage(String msg){
                Log.i("Websocket", "Message Received");

                // Appends the message received to the previous messages
                String s = messageInd.getText().toString().substring(0,1);

                if (Integer.parseInt(s) == 9){
                    messageInd.setText("9+ new messages");
                }
                else {
                    messageInd.setText(Integer.parseInt(s) + 1 + " new Messages");
                }

            }
            @Override
            public void onClose(int errorCode, String reason, boolean remote){
                Log.i("Websocket", "Closed " + reason);
            }
            @Override
            public void onError(Exception e){
                Log.i("Websocket", "Error " + e.getMessage());
            }
        };
        mWebSocketClient.connect();
    }
}