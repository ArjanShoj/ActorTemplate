package nl.hu_team.actortemplate.activity;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nl.hu_team.actortemplate.R;
import nl.hu_team.actortemplate.presenter.AddProjectActivityPresenter;

public class AddProjectActivity extends AppCompatActivity implements AddProjectActivityPresenter.AddProjectView{

    private AddProjectActivityPresenter presenter;

    @BindView(R.id.addProjectLayout) public LinearLayout rootLayout;
    @BindView(R.id.input_project_name) public AppCompatEditText inputName;
    @BindView(R.id.input_project_summary) public AppCompatEditText inputSummary;

    private String REQUIRED = "Required";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);

        this.presenter = new AddProjectActivityPresenter(this);
    }

    @OnClick(R.id.create_project_button) protected void addProject(){

        if(validateForm()){
            this.presenter.createProject(inputName.getText().toString(), inputSummary.getText().toString());
        }

    }



    @Override
    public void toProjectActivity() {
        finish();
    }

    @Override
    public boolean validateForm() {
        boolean result = true;

        if(TextUtils.isEmpty(inputName.getText().toString())){
            inputName.setError(REQUIRED);
            result = false;
        }else{
            inputName.setError(null);
        }

        if(TextUtils.isEmpty(inputSummary.getText().toString())){
            inputSummary.setError(REQUIRED);
            result = false;
        }else{
            inputSummary.setError(null);
        }

        return result;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
