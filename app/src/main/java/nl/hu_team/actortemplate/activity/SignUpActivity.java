package nl.hu_team.actortemplate.activity;

import android.content.Intent;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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

        if(!validateForm()){
           return;
        }

        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        String passwordConfirm = inputPasswordConfirm.getText().toString();

        email = email.trim();
        password = password.trim();
        passwordConfirm = passwordConfirm.trim();

        if(this.validatePasswordConfirm(password, passwordConfirm)){
            presenter.createUserWithEmail(email, password);
        }
    }

    private boolean validatePasswordConfirm(String password1, String password2){
        boolean result = true;

        if(!password1.matches(password2) && !password2.matches(password1)){
            result = false;
            inputPasswordConfirm.setError("Password doesn't match.");
        }else{
            inputPasswordConfirm.setError(null);
        }

        return  result;
    }

    private boolean validateForm(){
        boolean result = true;

        if(TextUtils.isEmpty(inputEmail.getText().toString())){
            inputEmail.setError("Required");
            result = false;
        }else{
            inputEmail.setError(null);
        }

        if(TextUtils.isEmpty(inputPassword.getText().toString())){
            inputPassword.setError("Required");
            result = false;
        }else{
            inputPassword.setError(null);
        }

        if(TextUtils.isEmpty(inputPasswordConfirm.getText().toString())){
            inputPasswordConfirm.setError("Required");
            result = false;
        }else{
            inputPasswordConfirm.setError(null);
        }

        return result;

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
