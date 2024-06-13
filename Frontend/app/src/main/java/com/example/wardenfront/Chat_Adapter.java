package com.example.wardenfront;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Chat_Adapter extends RecyclerView.Adapter<Chat_Adapter.ViewHolder>{

    private final Context context;
    private final ArrayList<ChatCard_Model> chat;

    public Chat_Adapter(Context Context, ArrayList<ChatCard_Model> arr){
        context = Context;
        chat = arr;
    }

    @NonNull
    @Override
    public Chat_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_cardview_layout, parent, false);
        return new Chat_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Chat_Adapter.ViewHolder holder, int position) {
        ChatCard_Model model = chat.get(position);
        holder.userName.setText(model.getName().toString() + "     " + model.getTime().toString());
        holder.message.setText(model.getMessage().toString());

    }

    @Override
    public int getItemCount() {
        return chat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView userName;
        private final TextView message;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            userName = itemView.findViewById(R.id.userAndDate);
            message = itemView.findViewById(R.id.Message);
        }
    }
}
