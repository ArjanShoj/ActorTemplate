package nl.hu_team.actortemplate.model;

import android.net.Uri;

import com.google.firebase.database.Exclude;

public class User {

    private String username;
    private String email;

    @Exclude
    private String userId;

    private String profilePhoto;

    public User(String userId, String email, String username, String profilePhoto){
        this.userId = userId;
        this.email = email;
        this.username = username;
        this.profilePhoto = profilePhoto;
    }

    public User(){
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

    public void setUserId(String userId){
        this.userId = userId;
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
