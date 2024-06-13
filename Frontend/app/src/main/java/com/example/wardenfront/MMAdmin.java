package com.example.wardenfront;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.example.wardenfront.app.AppController;

import com.example.wardenfront.net_utils.Const;

/**
 * @author Kyle Clements
 *
 * This is the main menu for admin
 */
public class MMAdmin extends AppCompatActivity {

    private TextView username;
    private Button toBan;
    private Button toUnban;

    private TextView numTotalReports;
    private TextView numReportsWeekly;

    private String Tag = MMAdmin.class.getSimpleName();
    private String tag_total_reports_req = "string_req";
    private String tag_weekly_reports_req = "string_req";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_m_admin);

        toBan = findViewById(R.id.ToBanUser);
        toBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MMAdmin.this, BanUser.class);
                intent.putExtra("mode", "ban");
                startActivity(intent);

            }
        });

        toUnban = findViewById(R.id.ToUnban);
        toUnban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MMAdmin.this, BanUser.class);
                intent.putExtra("mode", "unban");
                startActivity(intent);
            }
        });


        numTotalReports = findViewById(R.id.ReportsInDB);
        TotalReportsReq();

        numReportsWeekly = findViewById(R.id.NumReportWeekly);
        WeeklyReportsReq();
    }

    private void WeeklyReportsReq() {
        StringRequest stringRequest = new StringRequest(Method.GET, Const.URL_Weekly_Reports_Num, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(Tag, response.toString());
                numReportsWeekly.append("\n" + response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("error: "+ error.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(stringRequest, tag_weekly_reports_req);
    }

    private void TotalReportsReq() {
        StringRequest stringRequest = new StringRequest(Method.GET, Const.URL_Total_Reports_Num, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(Tag, response.toString());
                numTotalReports.append("\n" + response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("error: "+ error.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(stringRequest, tag_total_reports_req);
    }
}