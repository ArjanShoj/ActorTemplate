package nl.hu_team.actortemplate.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import nl.hu_team.actortemplate.R;
import nl.hu_team.actortemplate.activity.ActorActivity;
import nl.hu_team.actortemplate.model.Actor;
import nl.hu_team.actortemplate.model.Project;

public class ActorAdapter extends RecyclerView.Adapter<ActorAdapter.ActorViewHolder>{

    private Context context;
    private ArrayList<Actor> data = new ArrayList<Actor>();

    private Project project;

    public ActorAdapter(Context context, Project project){
        this.context = context;
        this.project = project;
    }

    public ActorAdapter(Context context, ArrayList<Actor> data){
        this.context = context;
        this.data = data;
    }

    @Override
    public ActorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ActorViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_actor, parent, false));
    }

    @Override
    public void onBindViewHolder(ActorViewHolder holder, int position) {
        final Actor actor = this.data.get(position);

        holder.actorName.setText(actor.getName());
        holder.actorEmail.setText(actor.getEmail());
        holder.actorFunction.setText(actor.getFunction());
        holder.actorNote.setText(actor.getNote());

        holder.actorCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(project.isEditable()){
                    project.getActorTemplate().setActor(actor);

                    Intent intent = new Intent(context, ActorActivity.class);
                    intent.putExtra("project", project);
                    context.startActivity(intent);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return this.data == null || this.data.isEmpty() ? 0 : this.data.size();
    }


    public void addActor(Actor actor){
        this.data.add(actor);
        this.notifyDataSetChanged();
    }

    public class ActorViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.card_actor) protected CardView actorCard;
        @BindView(R.id.card_actor_name) protected TextView actorName;
        @BindView(R.id.card_actor_function) protected TextView actorFunction;
        @BindView(R.id.card_actor_email) protected TextView actorEmail;
        @BindView(R.id.card_actor_note) protected TextView actorNote;

        public ActorViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
