package nl.hu_team.actortemplate.activity;

import android.content.Intent;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import nl.hu_team.actortemplate.R;
import nl.hu_team.actortemplate.adapter.ProjectAdapter;
import nl.hu_team.actortemplate.model.Project;
import nl.hu_team.actortemplate.presenter.ProjectActivityPresenter;

public class ProjectActivity extends AppCompatActivity implements ProjectActivityPresenter.ProjectActivityView{

    @BindView(R.id.project_list) protected RecyclerView projectList;
    private ProjectAdapter projectAdapter;

    private ProjectActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        ButterKnife.bind(this);

        presenter = new ProjectActivityPresenter(this);

        if(presenter.checkUser(FirebaseAuth.getInstance().getCurrentUser())){
            this.toLoginActivity();
        }

        initProjects();
        testProjects();
        //generateProjects();
    }

    @Override
    public void toLoginActivity() {
        Toast.makeText(this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void addProject(Project project) {
        projectAdapter.addProject(project);
    }

    private void initProjects() {
        projectAdapter = new ProjectAdapter(this);
        projectList.setLayoutManager(new LinearLayoutManager(this));
        projectList.setAdapter(projectAdapter);

    }

    private void testProjects(){

    }

    private void generateProjects(){
        presenter.setProjects();
    }
}
