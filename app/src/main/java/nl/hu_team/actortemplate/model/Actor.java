package nl.hu_team.actortemplate.model;

public class Actor {

    private String functionName;
    private String description;

    public Actor(){

    }

    //setters

    public void setFunctionName(String functionName){
        this.functionName = functionName;
    }

    public void setDescription(String description){
        this.description = description;
    }

    //getters

    public String getFunctionName(){
        return this.functionName;
    }

    public String getDescription(){
        return this.description;
    }

}
