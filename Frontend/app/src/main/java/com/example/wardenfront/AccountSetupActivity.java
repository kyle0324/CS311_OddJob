package com.example.wardenfront;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.wardenfront.app.AppController;
import com.example.wardenfront.net_utils.Const;

import org.json.JSONException;
import org.json.JSONObject;

public class AccountSetupActivity extends AppCompatActivity {

    private TextView Email;
    private TextView UserType;
    private TextView UserName;
    private TextView Password1;
    private TextView Password2;

    private Button finish;

    private JSONObject user;

    private String TAG = com.example.wardenfront.AccountSetupActivity.class.getSimpleName();
    private String tag_create_user_req = "account_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setup);

        Email = findViewById(R.id.InputEmail);
        UserType = findViewById(R.id.InputUserType);
        UserName = findViewById(R.id.InputUserName);
        Password1 = findViewById(R.id.InputPassword);
        Password2 = findViewById(R.id.ConfirmPassword);

        finish = findViewById(R.id.button);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = new JSONObject();
                try {
                    user.put("userName", UserName.getText().toString());
                    user.put("password", Password1.getText().toString());
                    user.put("userType", UserType.getText().toString());
                    user.put("email", Email.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                createUserReq();


            }
        });


    }

    private void createUserReq() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Const.URL_Create_Account,
                user, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                finish();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "error: " + error.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjectRequest, tag_create_user_req);

    }
}