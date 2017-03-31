package nl.hu_team.actortemplate.activity;

import android.content.Intent;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nl.hu_team.actortemplate.R;
import nl.hu_team.actortemplate.presenter.SignUpActivityPresenter;

public class SignUpActivity extends AppCompatActivity implements SignUpActivityPresenter.SignUpActivityView{

    private SignUpActivityPresenter presenter;

    @BindView(R.id.signup_email) protected EditText inputEmail;
    @BindView(R.id.signup_password) protected EditText inputPassword;
    @BindView(R.id.signup_password_confirm) protected EditText inputPasswordConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ButterKnife.bind(this);
        this.presenter = new SignUpActivityPresenter(this);
    }

    @OnClick(R.id.signup_button) public void signUp(){
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        String passwordConfirm = inputPasswordConfirm.getText().toString();

        email = email.trim();
        password = password.trim();
        passwordConfirm = passwordConfirm.trim();

        if(email.isEmpty() || password.isEmpty() || passwordConfirm.isEmpty()){
            showError(R.string.error_signup_empty, "Error");
        }else{
            if(!password.equals(passwordConfirm) || !password.matches(passwordConfirm)){
                showError(R.string.error_signup_password_confirm, "Password");
            }else {
                presenter.createUserWithEmail(email, password);
            }
        }
    }

    @Override
    public void showError(@StringRes int stringId, String errorTitle) {
        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
        builder.setMessage(stringId).setTitle(errorTitle).setPositiveButton("Ok", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void signedIn() {
        this.startActivity(new Intent(this, ProjectActivity.class));
    }
}
