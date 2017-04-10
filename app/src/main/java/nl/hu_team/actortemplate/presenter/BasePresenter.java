package nl.hu_team.actortemplate.presenter;

import android.support.annotation.StringRes;
import android.util.Log;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import nl.hu_team.actortemplate.model.ActorTemplate;
import nl.hu_team.actortemplate.model.Project;
import nl.hu_team.actortemplate.model.User;


public abstract class BasePresenter {

    protected Project project;

    public BasePresenter(Project project){
        this.project = project;
    }

    public BasePresenter(){

    }

    public boolean checkUser(FirebaseUser user){
        return user != null;
    }

    public User createUser(FirebaseUser firebaseUser){
        return new User(
                firebaseUser.getUid(),
                firebaseUser.getEmail(),
                usernameFromEmail(firebaseUser.getEmail()),
                (firebaseUser.getPhotoUrl() != null ? firebaseUser.getPhotoUrl().toString() : null)
        );
    }

    private String usernameFromEmail(String email) {
        return email.contains("@") ? email.split("@")[0] : email;
    }

    public String getActorKey(Project project){
        final String[] key = new String[1];

        FirebaseDatabase.getInstance().getReference()
                .child("projects")
                .child(project.getProjectId())
                .child("actor_templates")
                .child(project.getActorTemplate().getTemplateId())
                .child("actors")
                .orderByChild("name").equalTo(project.getName()).limitToFirst(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    key[0] = child.getKey();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return key[0];
    }

    public String getTemplateKey(Project project){
        final String[] key = new String[1];

        FirebaseDatabase.getInstance().getReference().child("projects").child(project.getProjectId()).child("actor_templates").orderByChild("name").equalTo(project.getName()).limitToFirst(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    key[0] = child.getKey();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return key[0];
    }

    public String getProjectKey(Project project){
        final String[] key = new String[1];

        FirebaseDatabase.getInstance().getReference().child("projects").orderByChild("name").equalTo(project.getName()).limitToFirst(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    key[0] = child.getKey();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return key[0];
    }

    public interface BaseView{
        void showError(@StringRes int stringId, String errorTitle);


    }

}
