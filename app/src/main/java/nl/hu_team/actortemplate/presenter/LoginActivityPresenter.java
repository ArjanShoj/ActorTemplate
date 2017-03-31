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
import com.google.firebase.auth.GoogleAuthProvider;

import nl.hu_team.actortemplate.R;
import nl.hu_team.actortemplate.activity.LoginActivity;

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

    public void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);

        mFirebaseAuth.signInWithCredential(credential).addOnCompleteListener(view, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(view, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                } else {
                    view.openProjectActivity();
                }
            }
        });
    }

    public void signInWithEmail(String email, String password){
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

    private GoogleSignInOptions getGoogleSignInOptions(){
        return new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(view.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
    }

    public GoogleApiClient getGoogleApiClient(){
        return new GoogleApiClient.Builder(view)
                .enableAutoManage(view, view)
                .addApi(Auth.GOOGLE_SIGN_IN_API, getGoogleSignInOptions())
                .build();
    }
}
