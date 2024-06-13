package com.example.wardenfront;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.wardenfront.app.AppController;
import com.example.wardenfront.net_utils.Const;


import java.util.ArrayList;

public class ChatMenu extends AppCompatActivity {


    private ChatMenu_Adapter.RecyclerViewClickListener listener;
    private String userID;
    private ArrayList<ChatMenuCard_model> arrL; //= new ArrayList<>();

    private RecyclerView chatOptions;
    private ChatMenu_Adapter adapter;
    private LinearLayoutManager manager;

    private Button exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chat_menu);

        arrL = new ArrayList<ChatMenuCard_model>();
        chatOptions = findViewById(R.id.chatOptions);



        arrL.add(new ChatMenuCard_model("Johny", "2019"));
        arrL.add(new ChatMenuCard_model("jim", "2022"));
        arrL.add(new ChatMenuCard_model("Dug", "2034"));

        setOnClickListener();

        adapter = new ChatMenu_Adapter(this, arrL, listener);

        manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        chatOptions.setLayoutManager(manager);
        chatOptions.setAdapter(adapter);


        exit = findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });






    }

    private void setOnClickListener() {
        listener = new ChatMenu_Adapter.RecyclerViewClickListener() {
            @Override
            public void onclick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), chats.class);
                startActivity(intent);
            }
        };
    }
}