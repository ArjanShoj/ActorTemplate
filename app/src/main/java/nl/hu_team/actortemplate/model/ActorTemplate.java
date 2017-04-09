package nl.hu_team.actortemplate.model;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class ActorTemplate implements Serializable{

    private String name;
    private String description;
    private boolean archive;

    @Exclude private Actor actor;

    public ActorTemplate(String name, String description){
        this.name = name;
        this.description = description;
    }

    public ActorTemplate(){

    }

    //setters

    public void setArchive(boolean archive){
        this.archive = archive;
    }

    public void setActor(Actor actor){
        this.actor = actor;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setDescription(String description){
        this.description = description;
    }

    //getters

    public boolean isArchive(){
        return this.archive;
    }

    public String getName(){
        return this.name;
    }

    public String getDescription(){
        return this.description;
    }

    public Actor getActor(){
        return this.actor;
    }

}
