package nl.hu_team.actortemplate.model;

import java.util.ArrayList;

public class Project {

    private String name;
    private String summary;

    private User analyst;
    private ArrayList<User> teamMembers;

    public Project(){

    }

    public Project(String name, String summary){
        this.name = name;
        this.summary = summary;
    }

    //setters

    public void setName(String name){
        this.name = name;
    }

    public void setSummary(String summary){
        this.summary = summary;
    }

    public void setAnalyst(User user){
        this.analyst = user;
    }

    public void addTeamMembers(User user){
        this.teamMembers.add(user);
    }

    public void removeTeamMembers(User user){
        for (int i = 0; i < this.teamMembers.size(); i++){
            if(this.teamMembers.get(i).getUserId().matches(user.getUserId())){
                this.teamMembers.remove(i);
            }
        }
    }

    //getters

    public String getName(){
        return this.name;
    }

    public String getSummary(){
        return this.summary;
    }

    public ArrayList<User> getTeamMembers(){
        return this.teamMembers;
    }

    public User getAnalyst(){
        return this.analyst;
    }

    public boolean isMember(User user){
        for (User u: this.teamMembers) {
            if(u.getUserId().matches(user.getUserId())){
                return true;
            }
        }
        return false;
    }
}
