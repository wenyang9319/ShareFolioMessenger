package com.example.wenyang.sharefoliomessenger.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.example.wenyang.sharefoliomessenger.R;
import com.example.wenyang.sharefoliomessenger.adapter.ListFriendAdapter;
import com.example.wenyang.sharefoliomessenger.engine.FriendEngine;
import com.example.wenyang.sharefoliomessenger.event_bus.FetchChatsEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MainActivity extends AppCompatActivity {

    public String TAG = MainActivity.class.getSimpleName();

    public ListView listView;

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listFriendView);

        FriendEngine friendEngine = new FriendEngine();

        friendEngine.fetchFriendsList();
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onFetchChatsEvent(FetchChatsEvent fetchChatsEvent){
        ListFriendAdapter listFriendAdapter = new ListFriendAdapter(this,fetchChatsEvent.getFriendArrayList());
        listView.setAdapter(listFriendAdapter);
        Log.d(TAG,fetchChatsEvent.getFriendArrayList().toString());

    }
}
