package nl.hu_team.actortemplate.presenter;

import android.support.annotation.StringRes;

import com.google.firebase.auth.FirebaseUser;

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

    public interface BaseView{
        void showError(@StringRes int stringId, String errorTitle);


    }

}
