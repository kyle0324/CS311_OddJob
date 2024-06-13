package com.example.wardenfront;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Ban_Adapter extends RecyclerView.Adapter<Ban_Adapter.ViewHolder> {

    private final Context context;
    private final ArrayList<BanCard_Model> banCard_modelArrayList;
    private RecyclerViewClickListener listener;


    public Ban_Adapter(Context context, ArrayList<BanCard_Model> banCard_modelArrayList, RecyclerViewClickListener listener){
        this.context = context;
        this.banCard_modelArrayList = banCard_modelArrayList;
        this.listener = listener;
    }



    @NonNull
    @Override
    public Ban_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ban_cardview_layout, parent, false);
        return new Ban_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Ban_Adapter.ViewHolder holder, int position) {
        BanCard_Model model = banCard_modelArrayList.get(position);
        holder.user.setText(model.getUserID() + " " + model.getName());
        holder.numRep.setText(model.getNumReports());
        holder.excuses.setText(model.getReportTypes());

    }

    @Override
    public int getItemCount() {
        return banCard_modelArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        private final TextView user;
        private final TextView numRep;
        private final TextView excuses;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            this.user = itemView.findViewById(R.id.user);
            this.numRep = itemView.findViewById(R.id.reportCount);
            this.excuses = itemView.findViewById(R.id.reportExample);
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
