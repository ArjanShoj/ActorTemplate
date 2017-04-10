package nl.hu_team.actortemplate.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nl.hu_team.actortemplate.R;
import nl.hu_team.actortemplate.model.Actor;
import nl.hu_team.actortemplate.model.ActorTemplate;
import nl.hu_team.actortemplate.model.Project;
import nl.hu_team.actortemplate.presenter.ActorActivityPresenter;

public class ActorActivity extends AfterSignedInBaseActivity {

    @BindView(R.id.actorActivityRoot) protected LinearLayout activityRoot;

    @BindView(R.id.actor_project_name) protected TextView projectName;
    @BindView(R.id.actor_project_summary) protected TextView projectSummary;

    @BindView(R.id.actor_actortemplate_name) protected TextView templateName;

    @BindView(R.id.actor_name) protected EditText inputName;
    @BindView(R.id.actor_function) protected EditText inputFunction;
    @BindView(R.id.actor_email) protected EditText inputEmail;
    @BindView(R.id.actor_phone) protected EditText inputPhone;
    @BindView(R.id.actor_note) protected EditText inputNote;

    private ActorActivityPresenter presenter;

    private Project project;

    private boolean newActor = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actor);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        project = (Project) getIntent().getSerializableExtra("project");
        if(project == null || project.getActorTemplate() == null){
            finish();
        }

        presenter = new ActorActivityPresenter(this, project);
        activityRoot.setBackgroundColor(getColor(project.getCardColor()));

        if(project.getActorTemplate().getActor() != null){
            setActorDetails(project.getActorTemplate().getActor());
            newActor = false;
        }

        setProjectDetails();
    }

    private boolean validateForm(){
        boolean result = true;

        if(TextUtils.isEmpty(inputName.getText().toString())){
            inputName.setError("Required");
            result = false;
        }else{
            inputName.setError(null);
        }

        if(TextUtils.isEmpty(inputEmail.getText().toString())){
            inputEmail.setError("Required");
            result = false;
        }else{
            inputEmail.setError(null);
        }

        return result;
    }

    @OnClick(R.id.actor_photo)
    public void takePicture(){

    }

    @OnClick(R.id.submit_actor)
    public void submitActor(){
        if(validateForm()){
            String name = inputName.getText().toString();
            String email = inputEmail.getText().toString();
            String phone = inputPhone.getText().toString();
            String function = inputFunction.getText().toString();
            String note = inputNote.getText().toString();

            Actor actor = new Actor();
            actor.setEmail(email);
            actor.setFunction(function);
            actor.setName(name);
            actor.setPhone(phone);
            actor.setNote(note);

            presenter.submitActor(actor, newActor);

            finish();
        }
    }

    private void setActorDetails(Actor actor){
        inputName.setText(actor.getName());
        inputEmail.setText(actor.getEmail());
        inputFunction.setText(actor.getFunction());
        inputPhone.setText(actor.getPhone());
        inputNote.setText(actor.getNote());
    }

    private void setProjectDetails(){
        projectName.setText(project.getName());
        projectSummary.setText(project.getSummary());
        templateName.setText("Actor: " + project.getActorTemplate().getName());
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
