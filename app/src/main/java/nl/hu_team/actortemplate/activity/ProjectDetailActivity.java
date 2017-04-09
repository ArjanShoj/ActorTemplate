package nl.hu_team.actortemplate.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nl.hu_team.actortemplate.R;
import nl.hu_team.actortemplate.adapter.TemplateAdapter;
import nl.hu_team.actortemplate.model.ActorTemplate;
import nl.hu_team.actortemplate.model.Project;
import nl.hu_team.actortemplate.presenter.ProjectActivityPresenter;
import nl.hu_team.actortemplate.presenter.ProjectDetailActivityPresenter;

public class ProjectDetailActivity extends AppCompatActivity implements ProjectDetailActivityPresenter.ProjectDetailView{

    private ProjectDetailActivityPresenter presenter;
    private Project project;

    @BindView(R.id.projectDetailActivity) protected RelativeLayout activityRoot;

    @BindView(R.id.detail_name) protected TextView detailName;
    @BindView(R.id.detail_summary) protected TextView detailSummary;

    @BindView(R.id.add_actortemplate_button) protected FloatingActionButton addTemplateButton;

    @BindView(R.id.detail_actortemplates) protected RecyclerView templateList;
    private TemplateAdapter templateAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_detail);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.project = (Project) getIntent().getSerializableExtra("project");
        if(this.project == null){
            finish();
        }

        if(!this.project.isEditable()){
            addTemplateButton.setVisibility(View.GONE);
        }

        activityRoot.setBackgroundColor(getColor(project.getCardColor()));

        this.presenter = new ProjectDetailActivityPresenter(this, project);

        this.setUpProjectDetails();
        this.initTemplates();
        presenter.setActorTemplates();
    }

    @OnClick(R.id.add_actortemplate_button)
    public void addTemplateActivity(){
        Intent intent = new Intent(this, TemplateActivity.class);
//        project.setActorTemplate(null);
        intent.putExtra("project", project);
        startActivity(intent);
    }



    private void setUpProjectDetails(){
        detailName.setText(project.getName());
        detailSummary.setText(project.getSummary());
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

    @Override
    public void addTemplateToAdapter(ActorTemplate actorTemplate) {
        templateAdapter.addTemplate(actorTemplate);
    }

    private void initTemplates() {
        templateAdapter = new TemplateAdapter(this, project);
        templateList.setLayoutManager(new LinearLayoutManager(this));
        templateList.setAdapter(templateAdapter);
    }


}
