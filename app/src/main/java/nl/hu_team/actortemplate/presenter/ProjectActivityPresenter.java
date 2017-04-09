package nl.hu_team.actortemplate.presenter;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import nl.hu_team.actortemplate.activity.ProjectActivity;
import nl.hu_team.actortemplate.model.Project;
import nl.hu_team.actortemplate.model.User;

public class ProjectActivityPresenter extends BasePresenter{

    private final ProjectActivity view;
    private DatabaseReference database;

    private User user;

    public ProjectActivityPresenter(ProjectActivity view){
        this.view = view;
        this.database = FirebaseDatabase.getInstance().getReference();

        this.user = createUser(FirebaseAuth.getInstance().getCurrentUser());
    }

    public interface ProjectActivityView {
        void toLoginActivity();
        void addProject(Project project, boolean analysist);
    }

    public void setProjects(){
        database.child("projects").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                final Project project = dataSnapshot.getValue(Project.class);

                database.child("project_members").child(project.getName()).orderByKey().equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            if(dataSnapshot.child(user.getUserId()).child("role").getValue().equals("analysist")){
                                project.setEditable(true);
                                view.addProject(project, true);
                            }else{
                                project.setEditable(false);
                                view.addProject(project, false);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d("OUTPUT", "onCancelled in de callback: " + databaseError.getMessage());
                    }
                });
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
