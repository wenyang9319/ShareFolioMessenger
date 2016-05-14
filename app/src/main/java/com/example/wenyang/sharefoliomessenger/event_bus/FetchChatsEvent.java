package com.example.wenyang.sharefoliomessenger.event_bus;

import com.example.wenyang.sharefoliomessenger.model.Friend;

import java.util.ArrayList;

/**
 * Created by WenYang on 14/05/2016.
 */
public class FetchChatsEvent {
    private ArrayList<Friend> friendArrayList = new ArrayList<>();

    public FetchChatsEvent(ArrayList<Friend> friendArrayList) {
        this.friendArrayList = friendArrayList;
    }

    public ArrayList<Friend> getFriendArrayList() {
        return friendArrayList;
    }
}
