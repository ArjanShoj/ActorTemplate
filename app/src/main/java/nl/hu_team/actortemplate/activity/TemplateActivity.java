package nl.hu_team.actortemplate.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nl.hu_team.actortemplate.R;
import nl.hu_team.actortemplate.adapter.TemplateAdapter;
import nl.hu_team.actortemplate.model.ActorTemplate;
import nl.hu_team.actortemplate.model.Project;
import nl.hu_team.actortemplate.presenter.TemplateActivityPresenter;

public class TemplateActivity extends AfterSignedInBaseActivity {

    @BindView(R.id.addTemplateActivityRoot) protected RelativeLayout activityRoot;

    @BindView(R.id.template_project_name) protected TextView projectName;
    @BindView(R.id.template_project_summary) protected TextView projectSummary;

    @BindView(R.id.template_input_name) protected AppCompatEditText inputName;
    @BindView(R.id.template_input_description) protected AppCompatEditText inputDescription;

    private TemplateActivityPresenter presenter;
    private Project project;

    private boolean newTemplate = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_template);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);

        project = (Project) getIntent().getSerializableExtra("project");
        if(project == null){
            finish();
        }

        this.presenter = new TemplateActivityPresenter(this, project);

        activityRoot.setBackgroundColor(getColor(project.getCardColor()));

        setUpProjectDetails();
        if(project.getActorTemplate() != null){
            fillTemplateDetails();
            newTemplate = false;
        }
    }



    @OnClick(R.id.submit_template)
    public void submitTemplate(){
        if(validateForm()){
            String name = inputName.getText().toString();
            String description = inputDescription.getText().toString();

            ActorTemplate actorTemplate = new ActorTemplate(name, description);
            presenter.submitTemplate(actorTemplate, newTemplate);
            finish();
        }
    }

    private boolean validateForm(){
        boolean result = true;

        if(TextUtils.isEmpty(inputName.getText().toString())){
            inputName.setError("Required");
            result = false;
        }else{
            inputName.setError(null);
        }

        if(TextUtils.isEmpty(inputDescription.getText().toString())){
            inputDescription.setError("Required");
            result = false;
        }else{
            inputDescription.setError(null);
        }

        return result;
    }

    private void fillTemplateDetails(){
        inputName.setText(project.getActorTemplate().getName());
        inputDescription.setText(project.getActorTemplate().getDescription());
    }


    private void setUpProjectDetails(){
        projectName.setText(project.getName());
        projectSummary.setText(project.getSummary());
    }

    @Override
    public void finish() {
        getIntent().removeExtra("project");
        super.finish();
    }

}
