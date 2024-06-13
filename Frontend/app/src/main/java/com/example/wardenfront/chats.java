package com.example.wardenfront;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.widget.TextView;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class chats extends AppCompatActivity {

    private WebSocketClient mWebSocketClient;
    private TextView mInput;
    private TextView mOutput;
    private RecyclerView chat;

    private String ownerID;
    private ArrayList<ChatCard_Model> chatlog;
    private Chat_Adapter adapter;
    private LinearLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);

        chat = findViewById(R.id.chat);

        if (getIntent().hasExtra("id")) {
            ownerID = getIntent().getStringExtra("id");
        }

        chatlog = new ArrayList<ChatCard_Model>();

        chat = findViewById(R.id.chat);
        adapter = new Chat_Adapter(this, chatlog);

        chatlog.add(new ChatCard_Model("jimmy", "this should be a very long message and I dont want to read it" +
                " Why did pressing enter make a plus and end the quotations?.  regardless" +
                " this needs to go further and beyond", "2014"));
        chatlog.add(new ChatCard_Model("john", "short message", "11/27/3030"));
        chatlog.add(new ChatCard_Model("jimmy", "I need to stop typing this much", "2015"));

        manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        chat.setAdapter(adapter);
        chat.setLayoutManager(manager);
    }

    private void connectWebSocket() {
        URI uri;
        try {
            /**
             * To test the clientside w/o the backend, connect to an echo server
             * Example: "ws://echo.websocket.org"
             */
            // uri = new URI("ws://10.0.2.2:8080/example"); //10.0.2.2 = localhost
            uri = new URI("ws://echo.websocket.org");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        mWebSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake handshake){
                Log.i("Websocket", "Opened");
            }
            @Override
            public void onMessage(String msg){
                Log.i("Websocket", "Message Received");

                // Appends the message received to the previous messages
                mOutput.append("\n" + msg);

            }
            @Override
            public void onClose(int errorCode, String reason, boolean remote){
                Log.i("Websocket", "Closed " + reason);
            }
            @Override
            public void onError(Exception e){
                Log.i("Websocket", "Error " + e.getMessage());
            }
        };
        mWebSocketClient.connect();


        /**
         * TODO : FIRST UN-BLOCK COMMENT LINES 71-103
         * Connect correct view ID
         *//*
         // Get the editText
         mInput = findViewById(R.id.m_input);

        *//**
         * TODO
         * Edit connect functions to occur when current chat activity starts
         *//*
        // Add handlers to the buttons
        btn_Connect.setOnClickListener((v) -> { connectWebSocket(); });

        *//**
         * TODO
         * Edit disconnect functions to occur when current chat activity finishes
         *//*
        btn_Disconnect.setOnClickListener((v) -> {
            mWebSocketClient.close();
            mOutput.setText("");
        });

        *//**
         * TODO
         * Connect correct button ID
         *//*
        btn_SendMsg.setOnClickListener((v) -> {
            // Get the message from the input
            String message = mInput.getText().toString();

            // If the message is not empty, send the message
            if(message  != null && message.length() > 0) {
                mWebSocketClient.send(message);
            }
        });*/
    }

}