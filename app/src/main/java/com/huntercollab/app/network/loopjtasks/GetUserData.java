package com.huntercollab.app.network.loopjtasks;

import android.content.Context;

import com.huntercollab.app.config.GlobalConfig;
import com.huntercollab.app.utils.GeneralTools;
import com.huntercollab.app.utils.Interfaces;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class GetUserData {

    private RequestParams requestParams;
    private Context context;
    private ArrayList<String> skillStringList;
    private ArrayList<String> classStringList;
    private String username;
    private String github;
    private String linkedIn;
    private String userNickname;
    private String userProfileLink;
    private Interfaces.DownloadComplete dataDownloadComplete;
    private Interfaces.DownloadProfleComplete downloadProfleComplete;
    private Interfaces.OwnerDownloadComplete ownerDownloadComplete;

    public GetUserData(Context context, Interfaces.DownloadComplete listener, Interfaces.DownloadProfleComplete listener1, Interfaces.OwnerDownloadComplete listener2){
        this.context = context;
        this.dataDownloadComplete = listener;
        this.downloadProfleComplete = listener1;
        this.ownerDownloadComplete = listener2;

        requestParams = new RequestParams();
        skillStringList = new ArrayList<>();
        classStringList = new ArrayList<>();
        username = new String();
        github = new String();
        linkedIn = new String();
        userNickname = new String();
        userProfileLink = new String();
    }

    public void getUserData(){
        AsyncHttpClient asyncHttpClient = GeneralTools.createAsyncHttpClient(context);

        asyncHttpClient.get(GlobalConfig.BASE_API_URL + "/user/", requestParams, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                setUserSkills(response);
                setUserClasses(response);
                setUserName(response);
                setUserLinkedIn(response);
                setUserGithub(response);
                setUserNickname(response);
                setUserProfile(response);
                dataDownloadComplete.downloadComplete(true);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                dataDownloadComplete.downloadComplete(false);
            }
        });
    }

    public void getOtherUserData(String userEmail){
        AsyncHttpClient asyncHttpClient = GeneralTools.createAsyncHttpClient(context);

        asyncHttpClient.get(GlobalConfig.BASE_API_URL + "/user/" + userEmail, requestParams, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                setUserSkills(response);
                setUserClasses(response);
                setUserName(response);
                setUserLinkedIn(response);
                setUserGithub(response);
                setUserNickname(response);
                setUserProfile(response);
                downloadProfleComplete.downloadProfileComplete(true);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                downloadProfleComplete.downloadProfileComplete(false);
            }
        });
    }

    public void getOwnerUserData(String userEmail){
        AsyncHttpClient asyncHttpClient = GeneralTools.createAsyncHttpClient(context);

        asyncHttpClient.get(GlobalConfig.BASE_API_URL + "/user/" + userEmail, requestParams, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                setUserSkills(response);
                setUserClasses(response);
                setUserName(response);
                setUserLinkedIn(response);
                setUserGithub(response);
                setUserNickname(response);
                setUserProfile(response);
                ownerDownloadComplete.ownerDownloadComplete(true);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                ownerDownloadComplete.ownerDownloadComplete(false);
            }
        });
    }

    private void setUserProfile(JSONObject response){
        try {
            userProfileLink = response.getString("profilePicture");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setUserName(JSONObject response){
        try {
            username = response.getString("username");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setUserNickname(JSONObject response){
        try {
            userNickname = response.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setUserGithub(JSONObject response){
        try {
            github = response.getString("github");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void setUserLinkedIn(JSONObject response){
        try {
            linkedIn = response.getString("linkedin");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void setUserSkills(JSONObject response){
        try {
            JSONArray terms = null;
            terms = response.getJSONArray("skills");

            for(int i=0; i < terms.length(); i++){
                String term = terms.getString(i);
                skillStringList.add(term);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setUserClasses(JSONObject response){
        try {
            JSONArray terms = null;
            terms = response.getJSONArray("classes");

            for(int i=0; i < terms.length(); i++){
                String term = terms.getString(i);
                classStringList.add(term);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getUserProfileLink() {
        System.out.println("I AM HERE" + userProfileLink);
        return userProfileLink; }

    public String getUserNickname(){
        return userNickname;
    }

    public String getUserName(){
        return username;
    }

    public String getUserGithub(){
        return github;
    }

    public String getUserLinkedIn(){
        return linkedIn;
    }

    public ArrayList<String> getUserClasses(){
        return classStringList;
    }

    public ArrayList<String> getUserSkills(){
        return skillStringList;
    }

}