package nl.hu_team.actortemplate.model;

public class Person {

    protected String name;
    protected String email;
    protected String photoReference;
    protected String function;
    protected String notes;

    public Person(){

    }

    //setters

    public void setName(String name){
        this.name = name;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setPhotoReference(String photoReference){
        this.photoReference = photoReference;
    }

    public void setFunction(String function){
        this.function = function;
    }

    public void setNotes(String notes){
        this.notes = notes;
    }

    //getters

    public String getName(){
        return this.name;
    }

    public String getEmail(){
        return this.email;
    }

    public String getPhotoReference(){
        return this.photoReference;
    }

    public String getFunction(){
        return this.function;
    }

    public String getNotes(){
        return this.notes;
    }


}
