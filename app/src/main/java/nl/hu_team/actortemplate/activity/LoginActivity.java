package nl.hu_team.actortemplate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nl.hu_team.actortemplate.R;
import nl.hu_team.actortemplate.presenter.LoginActivityPresenter;

public class LoginActivity extends BaseActivity implements GoogleApiClient.OnConnectionFailedListener, LoginActivityPresenter.LoginActivityView{

    private static final String TAG = "OUTPUT";
    private static final int RC_SIGN_IN = 9001;

    private GoogleApiClient googleApiClient;

    private LoginActivityPresenter presenter;

    @BindView(R.id.input_email) protected EditText inputEmail;
    @BindView(R.id.input_password) protected EditText inputPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        presenter = new LoginActivityPresenter(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @OnClick(R.id.signin_google)
    protected void googleSignIn(){
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @OnClick(R.id.new_signin)
    protected void signUp(){
        this.startActivity(new Intent(this, SignUpActivity.class));
    }

    @OnClick(R.id.signin_button)
    protected void signIn(){
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();

        email = email.trim();
        password = password.trim();

        if(email.isEmpty() || password.isEmpty()){
            showError(R.string.error_signup_empty, "Error");
        }else{
            presenter.firebaseAuthWithEmail(email, password);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign-In was successful, authenticate with Firebase
                presenter.firebaseAuthWithGoogle(result.getSignInAccount());
            } else {
                // Google Sign-In failed
                Log.e(TAG, "Google Sign-In failed.");
            }
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void openProjectActivity() {
        startActivity(new Intent(LoginActivity.this, ProjectActivity.class));
        finish();
    }

    @Override
    public void showError(@StringRes int stringId, String errorTitle) {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setMessage(stringId).setTitle(errorTitle).setPositiveButton("Ok", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
