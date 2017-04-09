package nl.hu_team.actortemplate.presenter;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import nl.hu_team.actortemplate.activity.ActorActivity;
import nl.hu_team.actortemplate.model.Actor;
import nl.hu_team.actortemplate.model.ActorTemplate;
import nl.hu_team.actortemplate.model.Project;

public class ActorActivityPresenter extends BasePresenter {

    private ActorActivity view;
    private DatabaseReference databaseReference;

    public ActorActivityPresenter(ActorActivity view, Project project){
        super(project);
        this.view = view;
        this.databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public interface ActorView {

    }

    public void submitActor(Actor actor){
        databaseReference.child("projects").child(project.getName()).child("actor_templates").child(project.getActorTemplate().getName()).child("actors").child(actor.getName()).setValue(actor);
    }
}
