package nl.hu_team.actortemplate.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nl.hu_team.actortemplate.R;
import nl.hu_team.actortemplate.activity.ProjectDetailActivity;
import nl.hu_team.actortemplate.model.Project;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder> {

    private ArrayList<Project> data = new ArrayList<Project>();
    private Context context;

    public ProjectAdapter(Context context){
        this.context = context;
    }

    public ProjectAdapter(Context context, ArrayList<Project> data){
        this.context = context;
        this.data = data;
    }

    @Override
    public ProjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ProjectViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_project, parent, false));
    }

    @Override
    public void onBindViewHolder(ProjectViewHolder holder, int position) {
        final Project project = data.get(position);;

        holder.projectName.setText(project.getName());
        holder.projectSummary.setText(project.getSummary());

        holder.projectCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProjectDetailActivity.class);
                intent.putExtra("project", project);
                context.startActivity(intent);
            }
        });

        if(!project.isEditable()){
            holder.modifyButton.setVisibility(View.GONE);
        }

        if(position % 2 == 0){
            holder.rootView.setBackgroundResource(R.color.first_project_card);
            project.setCardColor(R.color.first_project_card);
        }else{
            holder.rootView.setBackgroundResource(R.color.second_project_card);
            project.setCardColor(R.color.second_project_card);
        }
    }

    public void replaceData(ArrayList<Project> newData){
        this.data = newData;
        this.notifyDataSetChanged();
    }

    public void addProject(Project project){
        this.data.add(project);
        this.notifyDataSetChanged();
    }

    public void addProjectWithButton(Project project){
        project.setEditable(true);
        this.data.add(project);
        this.notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class ProjectViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.project_card) protected CardView projectCard;
        @BindView(R.id.project_name) protected TextView projectName;
        @BindView(R.id.project_summary) protected TextView projectSummary;
        @BindView(R.id.root_project_card) protected LinearLayout rootView;
        @BindView(R.id.analyst_button) protected FloatingActionButton modifyButton;

        public ProjectViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
