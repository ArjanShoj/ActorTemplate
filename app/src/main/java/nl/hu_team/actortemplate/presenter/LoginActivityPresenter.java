package nl.hu_team.actortemplate.presenter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import nl.hu_team.actortemplate.R;
import nl.hu_team.actortemplate.activity.LoginActivity;
import nl.hu_team.actortemplate.model.User;

public class LoginActivityPresenter extends BasePresenter{

    private final LoginActivity view;
    private FirebaseAuth mFirebaseAuth;

    public LoginActivityPresenter(LoginActivity view){
        this.view = view;
        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    public interface LoginActivityView extends BaseView{

        void openProjectActivity();

    }

    public void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {
        Log.d("OUTPUT", "firebaseAuthWithGoogle: " + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mFirebaseAuth.signInWithCredential(credential).addOnCompleteListener(view, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    FirebaseDatabase.getInstance().getReference().child("users").child(mFirebaseAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(!dataSnapshot.exists()){
                                User user = createUser(mFirebaseAuth.getCurrentUser());
                                FirebaseDatabase.getInstance().getReference().child("users").child(user.getUserId()).setValue(user);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    view.openProjectActivity();
                } else {
                    Toast.makeText(view, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void firebaseAuthWithEmail(final String email, final String password){
        mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(view, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(view, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                }else{
                    view.openProjectActivity();
                }
            }
        });
    }
}
