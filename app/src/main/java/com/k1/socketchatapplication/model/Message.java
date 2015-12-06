package com.k1.socketchatapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by k1 on 12/5/15.
 */
public class Message {

    @SerializedName("username")
    @Expose
    private String userName;

    @SerializedName("message")
    @Expose
    private String content;


    public Message(String userName, String content) {
        this.userName = userName;
        this.content = content;
    }

    public Message(JSONObject arg) throws JSONException {
        this.userName = arg.getString("username");
        this.content = arg.getString("message");
    }


    @Override
    public String toString() {
        return userName + " says : " + content;
    }
}
