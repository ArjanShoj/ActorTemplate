package nl.hu_team.actortemplate.activity;

import android.content.Intent;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nl.hu_team.actortemplate.R;
import nl.hu_team.actortemplate.adapter.ProjectAdapter;
import nl.hu_team.actortemplate.model.Project;
import nl.hu_team.actortemplate.presenter.ProjectActivityPresenter;

public class ProjectActivity extends AfterSignedInBaseActivity implements ProjectActivityPresenter.ProjectActivityView{

    @BindView(R.id.project_list) protected RecyclerView projectList;
    @BindView(R.id.project_layout) protected RelativeLayout projectLayout;
    private ProjectAdapter projectAdapter;

    private ProjectActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        ButterKnife.bind(this);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        presenter = new ProjectActivityPresenter(this);

        initProjects();
        generateProjects();
    }

    @Override
    public void toLoginActivity() {
        Toast.makeText(this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @OnClick(R.id.add_project_button) protected void toAddProjectActivity(){
        startActivity(new Intent(this, AddProjectActivity.class));
    }

    @Override
    public void addProject(Project project, boolean analysist) {
        if(analysist){
            projectAdapter.addProjectWithButton(project);
        }else{
            projectAdapter.addProject(project);
        }
    }

    @Override
    public void removeProject(Project project) {
        projectAdapter.removeProject(project);
    }

    private void initProjects() {
        projectAdapter = new ProjectAdapter(this);
        projectList.setLayoutManager(new LinearLayoutManager(this));
        projectList.setAdapter(projectAdapter);

    }


    private void generateProjects(){
        presenter.setProjects();
    }
}
