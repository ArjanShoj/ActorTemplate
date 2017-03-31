package nl.hu_team.actortemplate.presenter;

import android.support.annotation.StringRes;

import com.google.firebase.auth.FirebaseUser;

import nl.hu_team.actortemplate.model.User;


public class BasePresenter {

    public boolean checkUser(FirebaseUser user){
        return user != null;
    }

    public interface BaseView{
        void showError(@StringRes int stringId, String errorTitle);


    }

}
