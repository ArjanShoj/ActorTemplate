package nl.hu_team.actortemplate.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

import nl.hu_team.actortemplate.R;

public abstract class AfterSignedInBaseActivity extends BaseActivity {

    protected boolean editable;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                return true;

            case R.id.logoutUser:
                FirebaseAuth.getInstance().signOut();
                finishAffinity();
                startActivity(new Intent(this, LoginActivity.class));

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
