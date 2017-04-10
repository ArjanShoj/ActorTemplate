package nl.hu_team.actortemplate.model;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.ArrayList;

public class Project implements Serializable{

    private String name;
    private String summary;

    @Exclude private String projectId;
    @Exclude private ActorTemplate actorTemplate;
    @Exclude private boolean editable;
    @Exclude private int cardColor;

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

    public void setEditable(boolean editable){
        this.editable = editable;
    }

    public void setActorTemplate(ActorTemplate actorTemplate){
        this.actorTemplate = actorTemplate;
    }

    public void setCardColor(int cardColor) {
        this.cardColor = cardColor;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    //getters

    public ActorTemplate getActorTemplate(){
        return this.actorTemplate;
    }

    public String getName(){
        return this.name;
    }

    public String getSummary(){
        return this.summary;
    }

    public boolean isEditable(){
        return this.editable;
    }

    public int getCardColor() {
        return cardColor;
    }

    public String getProjectId() {
        return projectId;
    }
}
