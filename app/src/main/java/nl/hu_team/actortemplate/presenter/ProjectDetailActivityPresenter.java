package nl.hu_team.actortemplate.presenter;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import nl.hu_team.actortemplate.activity.ProjectDetailActivity;
import nl.hu_team.actortemplate.model.ActorTemplate;
import nl.hu_team.actortemplate.model.Project;

public class ProjectDetailActivityPresenter extends BasePresenter {

    private ProjectDetailActivity view;
    private DatabaseReference database;

    public ProjectDetailActivityPresenter(ProjectDetailActivity view, Project project){
        super(project);
        this.view = view;
        database = FirebaseDatabase.getInstance().getReference();
    }

    public interface ProjectDetailView {

        void addTemplateToAdapter(ActorTemplate actorTemplate);

    }

    public void setActorTemplates(){
        database.child("projects").child(project.getName()).child("actor_templates").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ActorTemplate actorTemplate = dataSnapshot.getValue(ActorTemplate.class);
                Log.d("OUTPUT", "onChildAdded: TOEGEVOEGD " + actorTemplate.getName());
                view.addTemplateToAdapter(actorTemplate);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("OUTPUT", "onCancelled: " + databaseError.getMessage());
            }
        });
    }
}
