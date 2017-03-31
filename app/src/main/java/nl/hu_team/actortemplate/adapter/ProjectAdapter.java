package nl.hu_team.actortemplate.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import nl.hu_team.actortemplate.R;
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
        Project project = data.get(position);

        holder.projectName.setText(project.getName());
        holder.projectSummary.setText(project.getSummary());

        if(position % 2 == 0){
            holder.rootView.setBackgroundResource(R.color.first_project_card);
        }else{
            holder.rootView.setBackgroundResource(R.color.second_project_card);
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

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class ProjectViewHolder extends RecyclerView.ViewHolder{

        private TextView projectName;
        private TextView projectSummary;
        private LinearLayout rootView;

        public ProjectViewHolder(View itemView) {
            super(itemView);
            rootView = (LinearLayout) itemView.findViewById(R.id.root_project_card);

            projectName = (TextView) itemView.findViewById(R.id.project_name);
            projectSummary = (TextView) itemView.findViewById(R.id.project_summary);
        }
    }
}
