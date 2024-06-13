package com.example.wardenfront;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;


public class ChatMenu_Adapter extends RecyclerView.Adapter<ChatMenu_Adapter.ViewHolder>{

    private final Context context;
    private final ArrayList<ChatMenuCard_model> chatOptionsArrayList;
    private ChatMenu_Adapter.RecyclerViewClickListener listener;

    public ChatMenu_Adapter(Context Context, ArrayList<ChatMenuCard_model> arr, ChatMenu_Adapter.RecyclerViewClickListener Listener){
        context = Context;
        chatOptionsArrayList = arr;
        listener = Listener;
    }

    @NonNull
    @Override
    public ChatMenu_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chatmenu_cardview_layout, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ChatMenu_Adapter.ViewHolder holder, int position) {
        ChatMenuCard_model model = chatOptionsArrayList.get(position);
        holder.modelTitle.setText(model.getTalkingTo().toString());
        holder.modelDate.setText("" + model.getLastSent().toString());

    }

    @Override
    public int getItemCount() {
        return chatOptionsArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener { //return to static here if errors
        private final TextView modelTitle;
        private final TextView modelDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            modelTitle =  itemView.findViewById(R.id.userAndDate);
            modelDate = itemView.findViewById(R.id.Message);
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
