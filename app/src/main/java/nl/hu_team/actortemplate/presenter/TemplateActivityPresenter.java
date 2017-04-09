package nl.hu_team.actortemplate.presenter;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import nl.hu_team.actortemplate.activity.TemplateActivity;
import nl.hu_team.actortemplate.model.ActorTemplate;
import nl.hu_team.actortemplate.model.Project;

public class TemplateActivityPresenter extends BasePresenter {

    private TemplateActivity view;
    private DatabaseReference databaseReference;

    public TemplateActivityPresenter(TemplateActivity view, Project project){
        super(project);
        this.view = view;

        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public interface AddTemplateView{
    }

    public void submitTemplate(ActorTemplate actorTemplate){
        databaseReference.child("projects").child(project.getName()).child("actor_templates").child(actorTemplate.getName()).setValue(actorTemplate);
        view.finish();
    }

}
