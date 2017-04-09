package nl.hu_team.actortemplate.model;

import java.io.Serializable;

public class Actor implements Serializable{

    private String name;
    private String function;
    private String email;
    private String phone;
    private String note;
    private String profilePhoto;

    public Actor(){

    }

    public Actor(String name, String function, String email, String phone, String note, String profilePhoto){
        this.name = name;
        this.function = function;
        this.email = email;
        this.phone = phone;
        this.note = note;
        this.profilePhoto = profilePhoto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }
}
