package com.example.wardenfront;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.wardenfront.app.AppController;
import com.example.wardenfront.net_utils.Const;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Kyle Clements
 *
 * This is where a user can report a user for something inappropiate
 */

public class ReportUserActivity extends AppCompatActivity {

    private String tag_report_req = "report_req";
    private String TAG = ReportUserActivity.class.getSimpleName();

    private Button submit;
    private Button escape;

    private CheckBox inaPP;
    private CheckBox verbal;
    private CheckBox theft;
    private CheckBox noShow;
    private CheckBox other;

    private EditText otherDesc;

    private String reportID;
    private String reasoning = "";

    private JSONObject report;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_user);

        Intent intent = getIntent();
        reportID = intent.getStringExtra("reportedID");

        escape = findViewById(R.id.escape);
        escape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        inaPP = findViewById(R.id.checkBox);
        verbal = findViewById(R.id.checkBox2);
        theft = findViewById(R.id.checkBox3);
        noShow = findViewById(R.id.checkBox4);
        other = findViewById(R.id.checkBox5);

        otherDesc = findViewById(R.id.editTextTextMultiLine2);

        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (other.isChecked()){
                    otherDesc.setText("");
                }
                else{
                    otherDesc.setText("If other, please describe your reasoning.");
                }
            }
        });

        submit = findViewById(R.id.sendReport);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inaPP.isChecked()){
                    reasoning += inaPP.getText() + ", ";
                }
                if (verbal.isChecked()){
                    reasoning += verbal.getText() + ", ";
                }
                if (theft.isChecked()){
                    reasoning += theft.getText() + ", ";
                }
                if (noShow.isChecked()){
                    reasoning += noShow.getText() + ", ";
                }
                if (other.isChecked()){
                    reasoning += otherDesc.getText().toString();
                }

                if (reasoning.length() <= 1){
                    otherDesc.setText("You need to give a reason to send this report");
                }

                else {
                    report = new JSONObject();
                    try {
                        report.put("description", reasoning);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    sendReport();
                    finish();
                }
            }
        });

    }

    /**
     * sends a post report request with the description of the report in the json
     */
    private void sendReport() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Method.POST, Const.Url_report + reportID, report,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "error: " + error.getMessage());

            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjectRequest, tag_report_req);

    }
}