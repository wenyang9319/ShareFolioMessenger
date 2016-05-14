package com.example.wenyang.sharefoliomessenger.engine;

import android.util.EventLog;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.wenyang.sharefoliomessenger.app.MyApplication;
import com.example.wenyang.sharefoliomessenger.constants.Constants;
import com.example.wenyang.sharefoliomessenger.event_bus.FetchChatsEvent;
import com.example.wenyang.sharefoliomessenger.model.Friend;
import com.example.wenyang.sharefoliomessenger.model.User;
import com.example.wenyang.sharefoliomessenger.utilities.VolleyRequestWrapper;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by WenYang on 13/05/2016.
 */
public class FriendEngine {

    public void fetchFriendsList(){

        Map<String, String> params = new HashMap<String, String>();
        params.put("user_id", MyApplication.getInstance().getPrefManager().getUser().getId());
        VolleyRequestWrapper jsObjRequest = new VolleyRequestWrapper(Request.Method.POST, Constants.URL_FETCHFRIENDS, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject responsedJsonObject) {

                ArrayList<Friend> friendArrayList = new ArrayList<>();
                try {

                    JSONArray peopleJsonArray = responsedJsonObject.getJSONArray("available_users");

                    for (int i = 0; i < peopleJsonArray.length(); i++) {
                        JSONObject c = peopleJsonArray.getJSONObject(i);

                        Friend friend = new Friend(c.getString("name"),c.getString("gcm_registration_id"));

                        friendArrayList.add(friend);

                    }

                    EventBus.getDefault().post(new FetchChatsEvent(friendArrayList));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });

        MyApplication.getInstance().getRequestQueue().add(jsObjRequest);

    }


    public interface CompletionHandler{
        void onCompleted(ArrayList<Friend> friendArrayList);
        void onFailed(String errorMessage);
    }

}
