package com.example.wardenfront;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.widget.TextView;

import android.app.ProgressDialog;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.wardenfront.app.AppController;
import com.example.wardenfront.net_utils.Const;

import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * @author Kyle Clements
 */
public class SignIn extends AppCompatActivity {

    public String userID;

    private Button button;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button gotocreateACC;

    private String TAG = com.example.wardenfront.SignIn.class.getSimpleName();
    private String tag_login_req = "login_req";
    private String tag_get_req = "getUser_req";

    private TextView userName;
    private TextView password;
    private TextView test;

    private ProgressDialog pDialog;
    private JSONObject Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


        userName = (TextView) findViewById(R.id.editTextTextPersonName);
        password = (TextView) findViewById(R.id.editTextTextPassword);
        Login = new JSONObject();

        test = (TextView) findViewById(R.id.textView2);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

        button = findViewById(R.id.ToMMP);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignIn.this, MMPoster.class);
                intent.putExtra("userID", "1345"); //User Ronald McDonald
                startActivity(intent);


            }
        });
        button2 = findViewById(R.id.ToMMA);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignIn.this, MMAdmin.class);

                intent.putExtra("userID", "1341"); //User Kyle
                startActivity(intent);

            }
        });
        button3 = findViewById(R.id.ToMM);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //makeStringReq();
                try {
                    Login.put("userName", userName.getText().toString());
                    Login.put("password", password.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                makeloginRequest();

            }
        });

        button4 = findViewById(R.id.getReq);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeGetUserReq();
            }
        });

        button5 = findViewById(R.id.ToMMS);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignIn.this, MMSeeker.class);
                intent.putExtra("userID", "1343"); //User Bob
                startActivity(intent);
            }
        });


        gotocreateACC = findViewById(R.id.GoToCreateAcc);
        gotocreateACC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignIn.this, AccountSetupActivity.class);
                startActivity(intent);
            }
        });


    }

    /**
     * this makes a request and validates a login.  It will then take the user to the correct screen.
     */
    private void makeloginRequest(){
        showProgressDialog();

        JsonObjectRequest jsonReq = new JsonObjectRequest(Method.POST, Const.URL_Sign_In, Login, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                VolleyLog.d(TAG, response.toString());
                test.setText(response.toString());
                try {
                    userID = response.get("id").toString();
                    if (response.get("userType").toString().contentEquals("poster")){
                        Intent intent = new Intent(SignIn.this, MMPoster.class);
                        intent.putExtra("userID", userID);
                        startActivity(intent);
                    }

                    else if (response.get("userType").toString().contentEquals("admin")){
                        Intent intent = new Intent(SignIn.this, MMAdmin.class);
                        intent.putExtra("userID", userID);
                        startActivity(intent);
                    }

                    else if (response.get("userType").toString().contentEquals("seeker")){
                        Intent intent = new Intent(SignIn.this, MMSeeker.class);
                        intent.putExtra("userID", userID);
                        startActivity(intent);
                    }

                    else {
                        test.setText("error");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                hideProgressDialog();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "error: " + error.getMessage());
                test.setText("None");
                hideProgressDialog();
            }
        }){
           @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();

                params.put("userName", userName.getText().toString());
                params.put("password", password.getText().toString());

                return params;
            };
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        AppController.getInstance().addToRequestQueue(jsonReq, tag_login_req);
    }

    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }



    public void makeGetUserReq(){

        JsonObjectRequest JsonObjectReq = new JsonObjectRequest(Method.GET, Const.Url_planB2, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        test.setText(response.toString());

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());

                    }
                });
        AppController.getInstance().addToRequestQueue(JsonObjectReq, tag_get_req);
    }

    /**
     * when switching from a activity to a fragment and there was no fragment present, you need to
     * hide everything otherwise the two screens overlap.
     */
    public void onDestroyView(){
        button.setVisibility(View.GONE);
        button2.setVisibility(View.GONE);
        button3.setVisibility(View.GONE);
        TextView t = findViewById(R.id.editTextTextPersonName);
        TextView t2 = findViewById(R.id.editTextTextPassword);
        TextView t3 = findViewById(R.id.textView2);
        t.setVisibility(View.GONE);
        t2.setVisibility(View.GONE);
        t3.setVisibility(View.GONE);
        button4.setVisibility(View.GONE);
        button5.setVisibility(View.GONE);
    }
}