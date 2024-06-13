package com.example.wardenfront;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.wardenfront.app.AppController;
import com.example.wardenfront.net_utils.Const;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Ayden Boehme
 *
 * Displays the Inbox message's contents.
 */
public class InboxMessageDisplay extends AppCompatActivity {

    private String TAG = com.example.wardenfront.InboxMessageDisplay.class.getSimpleName();
    private String tag_json_obj = "json_obj_req";
    private String userID;
    private String messageID;

    private TextView topic;
    private TextView message;

    private Button back;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox_message_display);

        topic = findViewById(R.id.messageTopicHere);
        message = findViewById(R.id.messageContents);

        Intent intent = getIntent();
        messageID = intent.getStringExtra("jobID"); //will need to code other side.  Need to put string in intent with intent.putextra
        userID = intent.getStringExtra("userID");

        getMsgJson(); //grabs json

        back = findViewById(R.id.btn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




    }

    private void getMsgJson() {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                Const.getUserInboxUrl + userID + "/" + messageID,
                null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        try {
                            topic.setText(response.get("messageTopic").toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            message.append(" " + response.get("message").toString());
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

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

        // Cancelling request
        // ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_obj);
    }
}