package com.example.wardenfront;

public class ChatCard_Model {
    private String name;
    private String message;
    private String Time;

    public ChatCard_Model(String name, String message, String Time){
        this.name = name;
        this.message = message;
        this.Time = Time;
    }

    public String getName(){return name;}
    public String getMessage(){return message;}
    public String getTime(){return Time;}

    public void setName(String name){
        this.name = name;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTime(String time) {
        Time = time;
    }
}
