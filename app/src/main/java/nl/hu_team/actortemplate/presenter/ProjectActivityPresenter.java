package nl.hu_team.actortemplate.presenter;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import nl.hu_team.actortemplate.activity.ProjectActivity;
import nl.hu_team.actortemplate.model.Project;
import nl.hu_team.actortemplate.model.User;

public class ProjectActivityPresenter extends BasePresenter{

    private final ProjectActivity view;
    private User user;
    private DatabaseReference database;


    public ProjectActivityPresenter(ProjectActivity view){
        this.view = view;
        this.database = FirebaseDatabase.getInstance().getReference();
    }

    public interface ProjectActivityView {
        void toLoginActivity();
        void addProject(Project project);
    }

    public void saveProject(Project project){
        database.child("projects").push().setValue(project);
    }

    public void setProjects(){
        database.child("projects").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Project project = dataSnapshot.getValue(Project.class);

                /*Iterable<DataSnapshot> teamChilderen = dataSnapshot.child("teammembers").getChildren();
                for(DataSnapshot member : teamChilderen){
                    User user = member.getValue(User.class);
                    project.addTeamMembers(user);
                }

                User analyst = dataSnapshot.child("analyst").getValue(User.class);
                project.setAnalyst(analyst);*/

                view.addProject(project);
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

            }
        });
    }

}
