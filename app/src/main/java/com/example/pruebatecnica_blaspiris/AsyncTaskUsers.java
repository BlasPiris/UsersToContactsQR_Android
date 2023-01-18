package com.example.pruebatecnica_blaspiris;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;


public class AsyncTaskUsers extends AsyncTask<Void,Void, ArrayList<User>> {

    @SuppressLint("StaticFieldLeak")
    Context context;
    ArrayList<User> users;
    @SuppressLint("StaticFieldLeak")
    RecyclerView recyclerView;
    UserAdapter userAdapter;



    public AsyncTaskUsers(Context context,RecyclerView recyclerView) {
        this.context = context;
        this.recyclerView = recyclerView;
    }

    @Override
    protected ArrayList<User> doInBackground(Void... voids) {

        return  this.getUsers();
    }

    // GET USERS FROM API
    private ArrayList<User> getUsers() {
        users=new ArrayList<>();
        String API="https://randomuser.me/api/?results=100";
        SyncHttpClient client=new SyncHttpClient();
       client.get(API, null, new AsyncHttpResponseHandler() {
           @RequiresApi(api = Build.VERSION_CODES.O)
           @Override
           public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
               try {
                  String result=new String(responseBody);
                   JSONObject json = new JSONObject(result);
                   JSONArray jsonUsers = json.getJSONArray("results");
                   for (int i = 0; i < jsonUsers.length(); i++) {
                       User user = createUser(jsonUsers.getJSONObject(i));
                       users.add(user);
                   }

               } catch (JSONException e) {
                   e.printStackTrace();
               }
           }

           @Override
           public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
           }

       });
        return users;
    }

    @Override
    protected void onPostExecute(ArrayList<User> users) {
        super.onPostExecute(users);
     userAdapter=new UserAdapter(context,users);
      recyclerView.setLayoutManager(new LinearLayoutManager(context));
       recyclerView.setAdapter(userAdapter);
    }

    //CREATE USER FROM JSONOBJECT
    @RequiresApi(api = Build.VERSION_CODES.O)
    private User createUser(JSONObject jsonObject) throws JSONException {
        User user = new User();
        user.setTitleName(jsonObject.getJSONObject("name")
                .optString("title"));
        user.setName(jsonObject.getJSONObject("name")
                .optString("first"));
        user.setSurname(jsonObject.getJSONObject("name")
                .optString("last"));
        user.setPhoto(jsonObject.getJSONObject("picture").optString("large"));
        user.setGender(jsonObject.optString("gender"));
        user.setAddress(jsonObject.getJSONObject("location").getJSONObject("street")
                .optString("number")+", "+jsonObject.getJSONObject("location").getJSONObject("street")
                .optString("name"));
        user.setCity(jsonObject.getJSONObject("location")
                .optString("city"));
        user.setState(jsonObject.getJSONObject("location")
                .optString("state"));
        user.setPostCode(jsonObject.getJSONObject("location")
                .optString("postcode"));
        user.setCountry(jsonObject.getJSONObject("location")
                .optString("country"));
        user.setEmail(jsonObject.optString("email"));
        user.setPhone(jsonObject.optString("phone"));
        user.setPhone2(jsonObject.optString("cell"));
        user.setBirthday( jsonObject.getJSONObject("dob")
                .optString("date"));
        user.setAge( jsonObject.getJSONObject("dob")
                .optString("age"));
        return  user;
    }

    //SEARCH USER FROM ARRAYLIST
    public void searchUser(String search) {
           ArrayList<User> searchUsers=new ArrayList<>();
            for(int i=0;i<users.size();i++){
                String name=users.get(i).getName().toLowerCase();
                String surname=users.get(i).getSurname().toLowerCase(Locale.ROOT);
                if(name.contains(search.toLowerCase(Locale.ROOT)) || surname.contains(search)){
                    searchUsers.add(users.get(i));
                }
            }
        userAdapter=new UserAdapter(context,searchUsers);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(userAdapter);
    }
}
