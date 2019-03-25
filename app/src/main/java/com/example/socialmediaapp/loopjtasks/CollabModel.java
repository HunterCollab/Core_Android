package com.example.socialmediaapp.loopjtasks;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;


public class CollabModel implements Parcelable {
    public int id;
    String owner;
    Integer size;
    String date;
    String duration;
    String location;
    Boolean status;
    String title;
    String description;
    private ArrayList<String> classes = new ArrayList<>();
    private ArrayList<String> skills = new ArrayList<>();
    private ArrayList<String> applicants = new ArrayList<>();
    private ArrayList<String> members = new ArrayList<>();

    CollabModel(
            int id,
            String owner,
            int size,
            String duration,
            String date,
            String location,
            Boolean status,
            String title,
            String description,
            ArrayList<String> classes,
            ArrayList<String> skills,
            ArrayList<String> applicants,
            ArrayList<String> members){

        this.id = id;
        this.owner = owner;
        this.size = size;
        this.duration = duration;
        this.date = date;
        this.location = location;
        this.status = status;
        this.title = title;
        this.description = description;
        this.classes = classes;
        this.skills = skills;
        this.applicants = applicants;
        this.members = members;
    }

    public CollabModel(Parcel in) {
        id = in.readInt();
        owner = in.readString();
        size = in.readInt();
        duration = in.readString();
        date = in.readString();
        location = in.readString();
        byte tmpStatus = in.readByte();
        status = tmpStatus == 0 ? null : tmpStatus == 1;
        title = in.readString();
        description = in.readString();
        skills = in.createStringArrayList();
        classes = in.createStringArrayList();
        members = in.createStringArrayList();
    }

    public static final Creator<CollabModel> CREATOR = new Creator<CollabModel>() {
        @Override
        public CollabModel createFromParcel(Parcel in) {
            return new CollabModel(in);
        }

        @Override
        public CollabModel[] newArray(int size) {
            return new CollabModel[size];
        }
    };

    public String getOwner(){
        return owner;
    }
    public int getSize(){ return size; }
    public ArrayList<String> getMembers(){
        return members;
    }
    public String getDate(){
        return date;
    }
    public String getDuration(){
        return duration;
    }
    public String getLocation(){
        return location;
    }
    public Boolean getStatus(){
        return status;
    }
    public String getTitle(){
        return title;
    }
    public String getDescription(){
        return description;
    }
    public ArrayList<String> getClasses(){
        return classes;
    }
    public ArrayList<String> getSkills(){
        return skills;
    }
    public ArrayList<String> getApplicants(){
        return applicants;
    }
    public int getId() {return id;}


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(owner);
        dest.writeInt(size);
        dest.writeString(duration);
        dest.writeString(date);
        dest.writeString(location);
        dest.writeByte((byte) (status == null ? 0 : status ? 1 : 2));
        dest.writeString(title);
        dest.writeString(description);
        dest.writeStringList(skills);
        dest.writeStringList(classes);
        dest.writeStringList(members);
    }
}
