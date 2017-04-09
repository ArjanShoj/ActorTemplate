package nl.hu_team.actortemplate.presenter;

import android.support.design.widget.Snackbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import nl.hu_team.actortemplate.activity.AddProjectActivity;
import nl.hu_team.actortemplate.model.Project;
import nl.hu_team.actortemplate.model.User;

public class AddProjectActivityPresenter extends BasePresenter{

    private AddProjectActivity view;
    private DatabaseReference databaseReference;

    public AddProjectActivityPresenter(AddProjectActivity view){
        this.view = view;
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public interface AddProjectView{

        void toProjectActivity();

        boolean validateForm();
    }

    public void createProject(String name, String summary){

        Project project = new Project(name, summary);
        databaseReference.child("projects").child(project.getName()).setValue(project);
        databaseReference.child("project_members").child(project.getName()).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("role").setValue("analysist");

        this.view.toProjectActivity();
    }



}
