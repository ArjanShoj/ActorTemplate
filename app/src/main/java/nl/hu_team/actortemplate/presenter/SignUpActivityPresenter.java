package nl.hu_team.actortemplate.presenter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import nl.hu_team.actortemplate.activity.SignUpActivity;
import nl.hu_team.actortemplate.model.User;

public class SignUpActivityPresenter extends BasePresenter{

    private final SignUpActivity view;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    public SignUpActivityPresenter(SignUpActivity view){
        this.view = view;
        this.databaseReference = FirebaseDatabase.getInstance().getReference();
        this.firebaseAuth = FirebaseAuth.getInstance();
    }

    public interface SignUpActivityView extends BaseView{
        void signedIn();
    }

    public void createUserWithEmail(String email, String password){

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(view, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d("OUTPUT", "onComplete: " + firebaseAuth.getCurrentUser());

                    User user = createUser(firebaseAuth.getCurrentUser());
                    databaseReference.child("users").child(user.getUserId()).setValue(user);

                    view.signedIn();
                }else{
                    Toast.makeText(view, "Registration failed.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }



}
