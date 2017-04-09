package nl.hu_team.actortemplate.presenter;

import nl.hu_team.actortemplate.activity.ProfileActivity;

public class ProfileActivityPresenter extends BasePresenter{

    private final ProfileActivity view;

    public ProfileActivityPresenter(ProfileActivity view) {
        this.view = view;
    }

    public interface ProfileActivityView{

    }



}
