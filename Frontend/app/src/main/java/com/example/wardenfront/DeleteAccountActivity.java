package com.example.wardenfront;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.wardenfront.app.AppController;
import com.example.wardenfront.net_utils.Const;

import org.json.JSONObject;

import java.lang.reflect.Method;

/**
 * @author Ayden Boehme
 *
 * Basic placeholder fragment for the delete account page.
 * No requests have been added yet, but it should be one simple post
 * request that will tell the backend to delete the account.
 */

public class DeleteAccountActivity extends AppCompatActivity {

    private String userId;
    private Button delete;
    private String TAG = com.example.wardenfront.DeleteAccountActivity.class.getSimpleName();
    private String tag_delete_req = "delete_req";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_account);
        userId = getIntent().getStringExtra("id");
        delete = findViewById(R.id.button2);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAccountReq();
            }
        });
    }

    private void deleteAccountReq() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                Const.URL_Delete_Account + userId, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        Intent intent = new Intent(DeleteAccountActivity.this, SignIn.class);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "error: ");
            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjectRequest, tag_delete_req);
    }
}