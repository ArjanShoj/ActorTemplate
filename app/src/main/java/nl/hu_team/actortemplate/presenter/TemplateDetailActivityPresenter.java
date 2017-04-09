package nl.hu_team.actortemplate.presenter;

import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import nl.hu_team.actortemplate.R;
import nl.hu_team.actortemplate.activity.TemplateDetailActivity;
import nl.hu_team.actortemplate.model.Actor;
import nl.hu_team.actortemplate.model.ActorTemplate;
import nl.hu_team.actortemplate.model.Project;

public class TemplateDetailActivityPresenter extends BasePresenter {

    private TemplateDetailActivity view;
    private DatabaseReference databaseReference;

    public TemplateDetailActivityPresenter(TemplateDetailActivity view, Project project){
        super(project);
        this.view = view;
        this.databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public interface TemplateDetailView {
        void addActorToAdapter(Actor actor);
    }

    public void setActors(){
        databaseReference.child("projects").child(project.getName()).child("actor_templates").child(project.getActorTemplate().getName()).child("actors").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Actor actor = dataSnapshot.getValue(Actor.class);
                view.addActorToAdapter(actor);
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
                Log.d("output", "onCancelled: " + databaseError.getMessage());
            }
        });
    }



}
