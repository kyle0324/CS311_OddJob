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
import com.example.wardenfront.net_utils.Const;

import org.json.JSONArray;

import java.util.ArrayList;

public class InboxActivity extends AppCompatActivity {
    private ArrayList inboxArrayList = new ArrayList<InboxMessageCard_Model>();

    private Button exit;

    private Inbox_Adapter inboxAdapter;
    private Inbox_Adapter.RecyclerViewClickListener listener;

    private JSONArray inboxArr;

    private LinearLayoutManager linearLayoutManager;

    private RecyclerView inboxRV;

    private String userID;
    private String TAG = com.example.wardenfront.InboxActivity.class.getSimpleName();
    // These tags will be used to cancel the requests
    private String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);

        Intent intent = getIntent();
        userID = intent.getStringExtra("userID");


        inboxRV = findViewById(R.id.inboxRV);
        inboxArr = new JSONArray();

        // Here, we have created new array list and added data to it
        inboxArrayList = new ArrayList<InboxMessageCard_Model>();


        setOnClickListener();
        // we are initializing our adapter class and passing our arraylist to it.
        inboxAdapter = new Inbox_Adapter(this, inboxArrayList, listener);

        // below line is for setting a layout manager for our recycler view.
        // here we are creating vertical list so we will provide orientation as vertical
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        // Setting up the layoutManager and Adapter to our recycler view.
        inboxRV.setLayoutManager(linearLayoutManager);
        inboxRV.setAdapter(inboxAdapter);

        getInboxMessages();

        exit = findViewById(R.id.exitInbox);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getInboxMessages() {
        JsonArrayRequest req = new JsonArrayRequest(
                Const.getUserInboxUrl + userID,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        inboxArr = response;

                        inboxArrayList.clear();
                        for (int i = 0; i < inboxArr.length(); i++) {
                            inboxArrayList.add(
                                    new InboxMessageCard_Model(
                                            inboxArr.optJSONObject(i).optString("messageTopic"),
                                            inboxArr.optJSONObject(i).optString("content"),
                                            String.valueOf(inboxArr.optJSONObject(i).optInt("id"))
                                    ));
                        }
                        inboxAdapter.notifyDataSetChanged();
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
        listener = new Inbox_Adapter.RecyclerViewClickListener() {
            @Override
            public void onclick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), InboxMessageDisplay.class);
                intent.putExtra("userID", userID);

                InboxMessageCard_Model thisMessage = (InboxMessageCard_Model) inboxArrayList.get(position);
                intent.putExtra("messageID", thisMessage.getInboxID());
                startActivity(intent);
            }
        };
    }

}
