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
 * Basic Adapter class for the RecyclerView on the Inbox page.
 * Used to update the Recycler with the newest data when needed.
 */
public class Inbox_Adapter extends RecyclerView.Adapter<Inbox_Adapter.ViewHolder> {

    private final Context context;
    private final ArrayList<InboxMessageCard_Model> inboxArrayList;
    private Inbox_Adapter.RecyclerViewClickListener listener;

    // Constructor
    public Inbox_Adapter(Context context, ArrayList<InboxMessageCard_Model> inboxArrayList,
                            Inbox_Adapter.RecyclerViewClickListener listen) {
        this.context = context;
        this.inboxArrayList = inboxArrayList;
        this.listener = listen;
    }

    @NonNull
    @Override
    public Inbox_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inbox_cardview_layout, parent, false);
        return new Inbox_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Inbox_Adapter.ViewHolder holder, int position) {
        // to set data to textview and imageview of each card layout
        InboxMessageCard_Model model = inboxArrayList.get(position);
        holder.modelMessageTopic.setText(model.getInboxMessageTopic());
        //holder.modelMessage.setText("" + model.getInboxMessage());
    }

    @Override
    public int getItemCount() {
        // this method is used for showing number of card items in recycler view
        return inboxArrayList.size();
    }

    // View holder class for initializing of your views such as TextView and Imageview
    public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener { //return to static here if errors
        private final TextView modelMessageTopic;
        //private final TextView modelMessage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            modelMessageTopic = itemView.findViewById(R.id.messageTopicBox);
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
