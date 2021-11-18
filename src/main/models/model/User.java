package model;

import java.util.ArrayList;

public class User {
    String message;
    String name;
    String profileURL;
    ArrayList<String> userHandle = new ArrayList<>();
    String userHandles;

    public User(){

    }
    public String getMessage(){

        return message;
    }
    public String getName(){
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getProfileURL(){
        return profileURL;
    }
    public void setProfileURL(String profileImageUrl) {
        this.profileURL = profileURL;
    }
    public ArrayList<String> getUserHandle() {
        return userHandle;
    }
    public void setUserHandle(String userHandles) {
        this.userHandles = userHandles;
        userHandle.add(userHandles);
    }
}
