package nl.hu_team.actortemplate.presenter;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import nl.hu_team.actortemplate.activity.TemplateActivity;
import nl.hu_team.actortemplate.model.ActorTemplate;
import nl.hu_team.actortemplate.model.Project;

public class TemplateActivityPresenter extends BasePresenter {

    private TemplateActivity view;
    private DatabaseReference databaseReference;

    public TemplateActivityPresenter(TemplateActivity view, Project project){
        super(project);
        this.view = view;

        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public interface AddTemplateView{
    }

    public void submitTemplate(final ActorTemplate actorTemplate, boolean newTemplate){
        if(newTemplate){
            databaseReference.child("projects").child(project.getProjectId()).child("actor_templates").push().setValue(actorTemplate);
        }else{
            databaseReference.child("projects")
                    .child(project.getProjectId())
                    .child("actor_templates").orderByChild("name").equalTo(project.getActorTemplate().getName()).limitToFirst(1).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot child : dataSnapshot.getChildren()){
                        actorTemplate.setTemplateId(child.getKey());
                    }
                    Log.d("OUTPUT", "onDataChange: template key " + actorTemplate.getTemplateId());
                    databaseReference.child("projects").child(project.getProjectId()).child("actor_templates").child(actorTemplate.getTemplateId()).setValue(actorTemplate);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }
    }

}
