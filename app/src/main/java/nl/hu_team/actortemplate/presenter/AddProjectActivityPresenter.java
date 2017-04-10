package nl.hu_team.actortemplate.presenter;

import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
        String key = databaseReference.child("projects").push().getKey();

        databaseReference.child("projects").child(key).setValue(project);
        databaseReference.child("project_members").child(key).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("role").setValue("analysist");

        this.view.toProjectActivity();
    }
}
