package nl.hu_team.actortemplate.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;
import nl.hu_team.actortemplate.R;
import nl.hu_team.actortemplate.presenter.ProfileActivityPresenter;

public class ProfileActivity extends BaseActivity implements ProfileActivityPresenter.ProfileActivityView{

    private static final int GALlERY_INTENT = 2;
    private ProfileActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        presenter = new ProfileActivityPresenter(this);
    }

    @OnClick(R.id.user_profile_photo) protected void changePhoto(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, GALlERY_INTENT);

    }

}
