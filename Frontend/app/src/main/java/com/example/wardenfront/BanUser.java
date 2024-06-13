package com.example.wardenfront;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
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
import java.util.HashMap;
import java.util.Map;

/**
 * @author Kyle Clements
 *
 * This allows the admin to ban a account
 */
public class BanUser extends AppCompatActivity {

    private Button clearName;
    private TextView name;
    private String TAG = com.example.wardenfront.BanUser.class.getSimpleName();
    private String tag_ban_req = "ban_req";
    private String tag_arr_Req = "jsonArr_req";
    private String tag_unban_req = "unban_req";
    private JSONObject clearing;
    private String mode = "";

    private Ban_Adapter banAdapter;
    private Ban_Adapter.RecyclerViewClickListener listener;
    private JSONArray banArr;
    private ArrayList<BanCard_Model> banList;

    private LinearLayoutManager linearLayoutManager;

    private RecyclerView banRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ban_user);
        mode = getIntent().getStringExtra("mode");
        clearing = new JSONObject();

        clearName = findViewById(R.id.clear);

        clearName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = (TextView) findViewById(R.id.username);
                if (clearName.getText().toString().contentEquals("UnBan")){
                    try {
                        clearing.put("userName", name.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    makeUnbanReq();
                }
                else{
                    try {
                        clearing.put("username", name.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    makeClearReq();
                }

            }
        });

        banRV = findViewById(R.id.BanList);
        banArr = new JSONArray();

        banList = new ArrayList<BanCard_Model>();
        setOnClickListener();

        banAdapter = new Ban_Adapter(this, banList, listener);

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        banRV.setLayoutManager(linearLayoutManager);
        banRV.setAdapter(banAdapter);


        if(mode != null) {
            if (mode.contentEquals("ban")) {
                buildreportedList();
            } else {
                buildBannedList();
                clearName.setText("UnBan");
            }
        }

    }

    private void makeUnbanReq() {
        JsonObjectRequest JsonReq = new JsonObjectRequest(Method.POST, Const.URL_UnBan, clearing, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                VolleyLog.d(TAG, response.toString());
                try {
                    if (response.get("response").toString().contentEquals("success")){
                        int i = 0;
                        while(!banList.get(i).getName().contentEquals(name.getText().toString()) &&
                                i < banList.size() - 1){
                            i++;
                        }
                        banList.remove(i);
                        banAdapter.notifyDataSetChanged();
                        name.setText("successful");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "error: " + error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String,String> params = new HashMap<String, String>();
                params.put("userName", name.getText().toString());
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(JsonReq, tag_unban_req);
    }


    private void buildBannedList() {
        JsonArrayRequest req = new JsonArrayRequest(Const.URL_Banned_List, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());

                banList.clear();
                banArr = response;
                for(int i = 0; i < response.length(); i++) {

                    JSONArray reports = new JSONArray();
                    try {
                        reports = banArr.optJSONObject(i).getJSONArray("reports");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    int numRep = reports.length();
                    if (!reports.isNull(0)) {
                        banList.add(new BanCard_Model(
                                banArr.optJSONObject(i).optString("id")
                                , "number of reports: " + numRep, "reasons: " +
                                reports.optJSONObject(0).optString("description") + "...",
                                banArr.optJSONObject(i).optString("userName")));
                    }
                }

                banAdapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });

        AppController.getInstance().addToRequestQueue(req, tag_arr_Req);
    }

    private void setOnClickListener() {
        listener = new Ban_Adapter.RecyclerViewClickListener() {
            @Override
            public void onclick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), ViewProfilePage.class);
                BanCard_Model model = (BanCard_Model) banList.get(position);

                intent.putExtra("profileOwnerID", model.getUserID());
                intent.putExtra("userType", "admin");

                startActivity(intent);
            }
        };
    }

    /**
     * This sends a request to ban a user.  After words the database will deactivate that user.
     */
    private void makeClearReq(){
        JsonObjectRequest JsonReq = new JsonObjectRequest(Method.POST, Const.URL_CleaReport, clearing, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                VolleyLog.d(TAG, response.toString());
                try {
                    if (response.get("response").toString().contentEquals("success")){
                        int i = 0;
                        while(!banList.get(i).getName().contentEquals(name.getText().toString()) &&
                                i < banList.size() - 1){
                            i++;
                        }
                        banList.remove(i);
                        banAdapter.notifyDataSetChanged();
                        name.setText("successful");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "error: " + error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String,String> params = new HashMap<String, String>();
                params.put("userName", name.getText().toString());
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(JsonReq, tag_ban_req);
    }

    /**
     * This grabs the list of users that have been reported from the database.
     */
    private void buildreportedList(){
        JsonArrayRequest req = new JsonArrayRequest(Const.Url_Ban_List, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());

                banList.clear();
                banArr = response;
                for(int i = 0; i < response.length(); i++){

                    JSONArray reports = new JSONArray();
                    try {
                        reports = banArr.optJSONObject(i).getJSONArray("reports");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    int numRep = reports.length();
                    banList.add(new BanCard_Model(
                            banArr.optJSONObject(i).optString("id")
                            , "number of reports: " + numRep, "reasons: " +
                            reports.optJSONObject(0).optString("description") + "...",
                            banArr.optJSONObject(i).optString("userName")));
                }

                banAdapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });

        AppController.getInstance().addToRequestQueue(req, tag_arr_Req);
    }
}