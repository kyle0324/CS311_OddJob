package com.example.wardenfront;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.wardenfront.JobBoardCard_Model;

import java.util.ArrayList;

/**
 * @author Ayden Boehme
 *
 * Basic Adapter class for the RecyclerViews on the main menu pages.
 * Used to update the Recycler with the newest data when needed.
 */
public class JobBoard_Adapter_MM extends RecyclerView.Adapter<JobBoard_Adapter_MM.ViewHolder> {

    private final Context context;
    private final ArrayList<JobBoardCard_Model> jobCardArrayList;
    private JobBoard_Adapter_MM.RecyclerViewClickListener listener;

    // Constructor
    public JobBoard_Adapter_MM(Context context, ArrayList<JobBoardCard_Model> jobCardArrayList,
                            JobBoard_Adapter_MM.RecyclerViewClickListener listen) {
        this.context = context;
        this.jobCardArrayList = jobCardArrayList;
        this.listener = listen;
    }

    @NonNull
    @Override
    public JobBoard_Adapter_MM.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_cardview_layout_mainmenus, parent, false);
        return new JobBoard_Adapter_MM.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobBoard_Adapter_MM.ViewHolder holder, int position) {
        // to set data to textview and imageview of each card layout
        JobBoardCard_Model model = jobCardArrayList.get(position);
        holder.modelTitle.setText(model.getJobTitle());
        holder.modelDate.setText("" + model.getJobDate());
    }

    @Override
    public int getItemCount() {
        // this method is used for showing number of card items in recycler view
        return jobCardArrayList.size();
    }

    // View holder class for initializing of your views such as TextView and Imageview
    public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener { //return to static here if errors
        private final TextView modelTitle;
        private final TextView modelDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            modelTitle = itemView.findViewById(R.id.titleBox2);
            modelDate = itemView.findViewById(R.id.dateTimeBox2);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onclick(itemView, getBindingAdapterPosition());
        }
    }

    public interface RecyclerViewClickListener{
        void onclick(View v, int position);
    }
}