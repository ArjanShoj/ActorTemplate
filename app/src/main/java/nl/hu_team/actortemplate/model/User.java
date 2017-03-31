package nl.hu_team.actortemplate.model;

import android.net.Uri;

public class User {

    private String username;
    private String email;
    private final String userId;
    private String profilePhoto;

    public User(String userId, String email){
        this.userId = userId;
        this.email = email;
    }

    public User(String userId){
        this.userId = userId;
    }

    //setters

    public void setEmail(String email){
        this.email = email;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setProfilePhoto(String profilePhoto){
        this.profilePhoto = profilePhoto;
    }

    //getters

    public String getUsername(){
        return this.username;
    }

    public String getEmail(){
        return this.email;
    }

    public String getUserId(){
        return this.userId;
    }

    public String getProfilePhoto(){
        return this.profilePhoto;
    }

}
