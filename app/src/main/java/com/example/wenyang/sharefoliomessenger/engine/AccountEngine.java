package com.example.wenyang.sharefoliomessenger.engine;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.wenyang.sharefoliomessenger.R;
import com.example.wenyang.sharefoliomessenger.app.MyApplication;
import com.example.wenyang.sharefoliomessenger.constants.Constants;
import com.example.wenyang.sharefoliomessenger.event_bus.LoginEvent;
import com.example.wenyang.sharefoliomessenger.gcm.GcmIntentService;
import com.example.wenyang.sharefoliomessenger.model.User;
import com.example.wenyang.sharefoliomessenger.utilities.VolleyRequestWrapper;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by WenYang on 12/05/2016.
 */
public class AccountEngine {

    public String TAG = AccountEngine.class.getSimpleName();

    public Context mContext;
    public AccountEngine(Context context){
        mContext = context;
    }


    public void register(Context context, String email, String password) {

        Map<String, String> params = new HashMap<String, String>();
        params.put("email", email);
        params.put("name", password);

        VolleyRequestWrapper jsObjRequest = new VolleyRequestWrapper(Request.Method.POST, Constants.URL_LOGIN, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject responsedJsonObject) {
                try {
                    boolean success = !responsedJsonObject.getBoolean("error");
                    if (success) {
                        JSONObject userObject = responsedJsonObject.getJSONObject("user");

                        User user = new User(userObject.getString("user_id"),
                                userObject.getString("name"),
                                userObject.getString("email"));

                        Log.d("user",user.toString());

                        MyApplication.getInstance().getPrefManager().storeUser(user);


                        Intent intent = new Intent(mContext, GcmIntentService.class);
                        intent.putExtra("key", "register");
                        mContext.startService(intent);


                    } else {
                        //TODO : onFailedExecution
                        //routeEngine_CompletionHandler.onFailed("Failed retrieve through Internet Connection");

                    }
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

//
//    private void fetchChatRooms() {
//        StringRequest strReq = new StringRequest(Request.Method.GET,
//                EndPoints.CHAT_ROOMS, new Response.Listener<String>() {
//
//            @Override
//            public void onResponse(String response) {
//                Log.e(TAG, "response: " + response);
//
//                try {
//                    JSONObject obj = new JSONObject(response);
//
//                    // check for error flag
//                    if (obj.getBoolean("error") == false) {
//                        JSONArray chatRoomsArray = obj.getJSONArray("chat_rooms");
//                        for (int i = 0; i < chatRoomsArray.length(); i++) {
//                            JSONObject chatRoomsObj = (JSONObject) chatRoomsArray.get(i);
//                            ChatRoom cr = new ChatRoom();
//                            cr.setId(chatRoomsObj.getString("chat_room_id"));
//                            cr.setName(chatRoomsObj.getString("name"));
//                            cr.setLastMessage("");
//                            cr.setUnreadCount(0);
//                            cr.setTimestamp(chatRoomsObj.getString("created_at"));
//
//                            chatRoomArrayList.add(cr);
//                        }
//
//                    } else {
//                        // error in fetching chat rooms
//                        Toast.makeText(getApplicationContext(), "" + obj.getJSONObject("error").getString("message"), Toast.LENGTH_LONG).show();
//                    }
//
//                } catch (JSONException e) {
//                    Log.e(TAG, "json parsing error: " + e.getMessage());
//                    Toast.makeText(getApplicationContext(), "Json parse error: " + e.getMessage(), Toast.LENGTH_LONG).show();
//                }
//
//                mAdapter.notifyDataSetChanged();
//
//                // subscribing to all chat room topics
//                subscribeToAllTopics();
//            }
//        }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                NetworkResponse networkResponse = error.networkResponse;
//                Log.e(TAG, "Volley error: " + error.getMessage() + ", code: " + networkResponse);
//                Toast.makeText(getApplicationContext(), "Volley error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        //Adding request to request queue
//        MyApplication.getInstance().addToRequestQueue(strReq);
//    }
}
