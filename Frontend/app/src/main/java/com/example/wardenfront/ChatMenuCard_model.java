package com.example.wardenfront;

public class ChatMenuCard_model {

    private String talkingTo = "";
    private String lastSent = "";

    public ChatMenuCard_model(String TalkingTo,  String LastSent){
        talkingTo = TalkingTo;
        lastSent = LastSent;
    }

    public String getTalkingTo(){
        return talkingTo;
    }

    public String getLastSent(){
        return lastSent;
    }

    public void setTalkingTo(String TalkingTo){
        talkingTo = TalkingTo;
    }

    public void setLastSent(String LastSent){
        lastSent = LastSent;
    }
}
