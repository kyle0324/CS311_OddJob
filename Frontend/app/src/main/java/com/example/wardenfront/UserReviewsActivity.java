package com.example.wardenfront;


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

/**
 * @author Ayden Boehme
 *
 * User reviews page with a recycler view that updates with the
 * newest list of the user's reviews.
 */

public class UserReviewsActivity extends AppCompatActivity {

    private ArrayList reviewsArrayList = new ArrayList<UserReviewsCard_Model>();

    private Button backToProfileBtn;

    private UserReviews_Adapter userReviewsAdapter;
    private JSONArray reviewsArr;

    private LinearLayoutManager linearLayoutManager;

    private RecyclerView reviewsRV;

    private String userID;
    private String tag_arr_Req = "jsonArr_req";
    private String TAG = com.example.wardenfront.UserReviewsActivity.class.getSimpleName();
    // These tags will be used to cancel the requests
    private String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reviews);

        this.userID = getIntent().getStringExtra("userID");
        reviewsRV = findViewById(R.id.reviewsRV);
        reviewsArr = new JSONArray();

        // Here, we have created new array list and added data to it
        reviewsArrayList = new ArrayList<UserReviewsCard_Model>();

        // we are initializing our adapter class and passing our arraylist to it.
        userReviewsAdapter = new UserReviews_Adapter(this, reviewsArrayList);

        // below line is for setting a layout manager for our recycler view.
        // here we are creating vertical list so we will provide orientation as vertical
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        // Setting up the layoutManager and Adapter to our recycler view.
        reviewsRV.setLayoutManager(linearLayoutManager);
        reviewsRV.setAdapter(userReviewsAdapter);

        getReviews();

        backToProfileBtn = findViewById(R.id.btn_exitReviews);
        backToProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * JsonArrayRequest for the newest list of reviews.
     *    onResponse - 1. Clears reviews list to prepare for a new array request
     *                 2. Searches the whole JSONArray for the needed values of each JSONObject
     *                        in the array in order to make a review CardView model for each JSONObject
     *                 3. Notifies the Adapter that changes have been made to the reviewsArrayList
     */
    private void getReviews() {
        JsonArrayRequest req = new JsonArrayRequest(
                Const.getReviewsOfUser + userID,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        reviewsArr = response;

                        reviewsArrayList.clear();
                        for(int i = 0; i < reviewsArr.length(); i++){
                            reviewsArrayList.add(
                                    new UserReviewsCard_Model(
                                            /**
                                             * TODO : Match keys to Backend values
                                             */
                                            reviewsArr.optJSONObject(i).optString("title"),
                                            reviewsArr.optJSONObject(i).optString("reviewer"),
                                            reviewsArr.optJSONObject(i).optString("rating"),
                                            reviewsArr.optJSONObject(i).optString("description")
                                    ));
                        }
                        userReviewsAdapter.notifyDataSetChanged();
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
}
