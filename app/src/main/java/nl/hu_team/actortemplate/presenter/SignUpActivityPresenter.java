package nl.hu_team.actortemplate.presenter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import nl.hu_team.actortemplate.activity.SignUpActivity;
import nl.hu_team.actortemplate.model.User;

public class SignUpActivityPresenter extends BasePresenter{

    private final SignUpActivity view;
    private FirebaseAuth firebaseAuth;

    public SignUpActivityPresenter(SignUpActivity view){
        this.view = view;
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public interface SignUpActivityView extends BaseView{
        void signedIn();
    }

    public void createUser(FirebaseUser firebaseUser){
        User user = new User(firebaseUser.getUid(), firebaseUser.getEmail());
        user.setUsername(firebaseUser.getDisplayName());
        user.setProfilePhoto((firebaseUser.getPhotoUrl() != null ? firebaseUser.getPhotoUrl().toString() : null));
    }

    public void createUserWithEmail(String email, String password){

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(view, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d("OUTPUT", "onComplete: " + firebaseAuth.getCurrentUser());
                    view.signedIn();
                }else{

                }
            }
        });

    }



}
