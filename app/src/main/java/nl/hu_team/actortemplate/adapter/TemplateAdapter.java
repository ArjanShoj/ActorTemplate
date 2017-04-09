package nl.hu_team.actortemplate.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import nl.hu_team.actortemplate.R;
import nl.hu_team.actortemplate.activity.ActorActivity;
import nl.hu_team.actortemplate.activity.TemplateDetailActivity;
import nl.hu_team.actortemplate.model.ActorTemplate;
import nl.hu_team.actortemplate.model.Project;

public class TemplateAdapter extends RecyclerView.Adapter<TemplateAdapter.TemplateViewHolder>{

    private ArrayList<ActorTemplate> data = new ArrayList<ActorTemplate>();
    private Context context;

    private Project project;

    public TemplateAdapter(Context context, ArrayList<ActorTemplate> data){
        this.context = context;
        this.data = data;
    }

    public TemplateAdapter(Context context, Project project){
        this.context = context;
        this.project = project;
    }

    @Override
    public TemplateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TemplateViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_actortemplates, parent, false));
    }

    @Override
    public void onBindViewHolder(TemplateViewHolder holder, final int position) {
        final ActorTemplate actorTemplate = data.get(position);

        Log.d("OUTPUT", "onBindViewHolder: " + actorTemplate.getDescription());

        holder.templateName.setText(actorTemplate.getName());
        holder.templateDescription.setText(actorTemplate.getDescription());

        holder.templateCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TemplateDetailActivity.class);
                project.setActorTemplate(actorTemplate);
                intent.putExtra("project", project);

                context.startActivity(intent);
            }
        });

    }

    public void addTemplate(ActorTemplate actorTemplate){
        data.add(actorTemplate);
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.isEmpty() || data == null ? 0 : data.size();
    }

    public class TemplateViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.actortemplate_card) protected CardView templateCard;
        @BindView(R.id.actortemplate_name) protected TextView templateName;
        @BindView(R.id.actortemplate_description) protected TextView templateDescription;

        public TemplateViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
