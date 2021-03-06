package nl.hu_team.actortemplate.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nl.hu_team.actortemplate.R;
import nl.hu_team.actortemplate.adapter.ActorAdapter;
import nl.hu_team.actortemplate.model.Actor;
import nl.hu_team.actortemplate.model.ActorTemplate;
import nl.hu_team.actortemplate.model.Project;
import nl.hu_team.actortemplate.presenter.TemplateDetailActivityPresenter;

public class TemplateDetailActivity extends AfterSignedInBaseActivity implements TemplateDetailActivityPresenter.TemplateDetailView{

    private TemplateDetailActivityPresenter presenter;

    @BindView(R.id.templateDetailActivity) protected RelativeLayout activityRoot;

    @BindView(R.id.detail_actortemplate_project_name) protected TextView projectName;
    @BindView(R.id.detail_actortemplate_project_summary) protected TextView projectSummary;

    @BindView(R.id.add_actor_button) protected FloatingActionButton addActorButton;
    @BindView(R.id.archive_actor_template) protected FloatingActionButton archiveTemplateButton;

    @BindView(R.id.detail_actortemplate_name) protected TextView templateName;
    @BindView(R.id.detail_actortemplate_description) protected TextView templateDescription;
    @BindView(R.id.detail_actortemplate_photo) protected ImageView templatePhoto;

    @BindView(R.id.detail_actor_list) protected RecyclerView actorList;
    private ActorAdapter actorAdapter;

    private Project project;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template_detail);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        project = (Project) getIntent().getSerializableExtra("project");
        if(project == null || project.getActorTemplate() == null){
            finish();
        }

        FirebaseDatabase.getInstance().getReference().child("projects")
                .child(project.getProjectId())
                .child("actor_templates")
                .orderByChild("name")
                .equalTo(project.getActorTemplate().getName())
                .limitToFirst(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Log.d("OUTPUT", "onDataChange: " + child.getKey());
                    project.getActorTemplate().setTemplateId(child.getKey());
                }
                presenter = new TemplateDetailActivityPresenter(TemplateDetailActivity.this, project);

                activityRoot.setBackgroundColor(getColor(project.getCardColor()));

                setProjectDetails();
                setTemplateDetails();

                initActors();
                setActors();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void initActors(){
        actorAdapter = new ActorAdapter(this, project);
        actorList.setLayoutManager(new LinearLayoutManager(this));
        actorList.setAdapter(actorAdapter);
    }

    @OnClick(R.id.archive_actor_template)
    public void archiveTemplate(){
        presenter.archiveTemplate();
    }

    @OnClick(R.id.add_actor_button)
    public void addActorActivity(){
        Intent intent = new Intent(this, ActorActivity.class);
//        project.getActorTemplate().setActor(null);
        intent.putExtra("project", project);
        startActivity(intent);
    }

    @OnClick(R.id.detail_template_container)
    public void editTemplate(){
        if(project.isEditable()){
            Intent intent = new Intent(this, TemplateActivity.class);
            intent.putExtra("project", project);
            startActivity(intent);
        }
    }

    @Override
    public void addActorToAdapter(Actor actor) {
        this.actorAdapter.addActor(actor);
    }

    @Override
    public void removeActorFromAdapter(Actor actor) {
        actorAdapter.removeActor(actor);
    }

    private void setProjectDetails(){
        projectName.setText(project.getName());
        projectSummary.setText(project.getSummary());

        if(!project.isEditable()){
            addActorButton.setVisibility(View.GONE);
            archiveTemplateButton.setVisibility(View.GONE);
        }
    }

    private void setTemplateDetails(){
        templateName.setText(project.getActorTemplate().getName());
        templateDescription.setText(project.getActorTemplate().getDescription());

        if(project.getActorTemplate().getImage() != null){
            if(project.getActorTemplate().getImage().contains("http")){
                try {
                    Bitmap image = presenter.decodeFromBase64(project.getActorTemplate().getImage());
                    templatePhoto.setImageBitmap(image);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                //hier komt picasso
            }
        }
    }

    private void setActors(){
        presenter.setActors();
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
