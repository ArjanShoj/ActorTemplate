package nl.hu_team.actortemplate.presenter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    public void submitActor(final Actor actor, boolean newActor){
        if(newActor){
            databaseReference.child("projects").child(project.getProjectId()).child("actor_templates").child(project.getActorTemplate().getTemplateId()).child("actors").push().setValue(actor);
        }else{
            databaseReference.child("projects").child(project.getProjectId()).child("actor_templates").child(project.getActorTemplate().getTemplateId()).child("actors")
                    .orderByChild("name").equalTo(actor.getName()).limitToFirst(1).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot child : dataSnapshot.getChildren()){
                        actor.setActorId(child.getKey());
                    }
                    databaseReference.child("projects").child(project.getProjectId()).child("actor_templates").child(project.getActorTemplate().getTemplateId()).child("actors").child(actor.getActorId()).setValue(actor);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }

    }
}
