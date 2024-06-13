package com.example.wardenfront;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * @author Ayden Boehme
 *
 * Basic Adapter class for the RecyclerView on the UserReviewsActivity page.
 * Used to update the Recycler with the newest data when needed.
 */
public class UserReviews_Adapter extends RecyclerView.Adapter<UserReviews_Adapter.ViewHolder> {

    private final Context context;
    private final ArrayList<UserReviewsCard_Model> reviewsCardArrayList;

    // Constructor
    public UserReviews_Adapter(Context context, ArrayList<UserReviewsCard_Model> reviewsCardArrayList) {
        this.context = context;
        this.reviewsCardArrayList = reviewsCardArrayList;
    }

    @NonNull
    @Override
    public UserReviews_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_reviews_cardview_layout, parent, false);
        return new UserReviews_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserReviews_Adapter.ViewHolder holder, int position) {
        // to set data to textview and imageview of each card layout
        UserReviewsCard_Model model = reviewsCardArrayList.get(position);
        holder.modelTitle.setText(model.getReviewTitle());
        holder.modelReviewer.setText(model.getReviewerUsername());
        holder.modelRating.setText(model.getReviewRating());
        holder.modelMessage.setText(model.getReviewMessage());
    }

    @Override
    public int getItemCount() {
        // this method is used for showing number of card items in recycler view
        return reviewsCardArrayList.size();
    }

    // View holder class for initializing of your views such as TextView and Imageview
    public class ViewHolder extends RecyclerView.ViewHolder { //return to static here if errors
        private final TextView modelTitle;
        private final TextView modelReviewer;
        private final TextView modelRating;
        private final TextView modelMessage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            modelTitle = itemView.findViewById(R.id.reviewTitle);
            modelReviewer = itemView.findViewById(R.id.reviewerUsername);
            modelRating = itemView.findViewById(R.id.reviewScore);
            modelMessage = itemView.findViewById(R.id.reviewMessage);
        }
    }
}
