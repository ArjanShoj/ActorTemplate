package nl.hu_team.actortemplate.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nl.hu_team.actortemplate.R;
import nl.hu_team.actortemplate.adapter.TemplateAdapter;
import nl.hu_team.actortemplate.model.ActorTemplate;
import nl.hu_team.actortemplate.model.Project;
import nl.hu_team.actortemplate.presenter.TemplateActivityPresenter;

public class TemplateActivity extends AfterSignedInBaseActivity {

    @BindView(R.id.addTemplateActivityRoot) protected RelativeLayout activityRoot;

    @BindView(R.id.template_project_name) protected TextView projectName;
    @BindView(R.id.template_project_summary) protected TextView projectSummary;

    @BindView(R.id.template_input_name) protected AppCompatEditText inputName;
    @BindView(R.id.template_input_description) protected AppCompatEditText inputDescription;

    @BindView(R.id.template_photo) protected ImageButton setPhotoButton;
    private String photoImageCode;

    private TemplateActivityPresenter presenter;
    private Project project;

    private static final int REQUEST_IMAGE_CAPTURE = 111;

    private boolean newTemplate = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_template);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);

        project = (Project) getIntent().getSerializableExtra("project");
        if(project == null){
            finish();
        }

        this.presenter = new TemplateActivityPresenter(this, project);

        activityRoot.setBackgroundColor(getColor(project.getCardColor()));

        setUpProjectDetails();
        if(project.getActorTemplate() != null){
            fillTemplateDetails();
            newTemplate = false;
        }
    }

    @OnClick(R.id.template_photo)
    public void addPhoto(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to use the camera or from gallery?")
                .setTitle("Photo")
                .setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        launchCamera();
                    }
                })
                .setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        AlertDialog choiceDialog = builder.create();
        choiceDialog.show();
    }

    private void launchCamera(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(this.getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            setPhotoButton.setImageBitmap(imageBitmap);
            photoImageCode = presenter.encodeBitmap(imageBitmap);
        }
    }


    @OnClick(R.id.submit_template)
    public void submitTemplate(){
        if(validateForm()){
            String name = inputName.getText().toString();
            String description = inputDescription.getText().toString();

            ActorTemplate actorTemplate = new ActorTemplate(name, description);

            presenter.submitTemplate(actorTemplate, newTemplate, photoImageCode);
            finish();
        }
    }

    private boolean validateForm(){
        boolean result = true;

        if(TextUtils.isEmpty(inputName.getText().toString())){
            inputName.setError("Required");
            result = false;
        }else{
            inputName.setError(null);
        }

        if(TextUtils.isEmpty(inputDescription.getText().toString())){
            inputDescription.setError("Required");
            result = false;
        }else{
            inputDescription.setError(null);
        }

        return result;
    }

    private void fillTemplateDetails(){
        inputName.setText(project.getActorTemplate().getName());
        inputDescription.setText(project.getActorTemplate().getDescription());
    }


    private void setUpProjectDetails(){
        projectName.setText(project.getName());
        projectSummary.setText(project.getSummary());
    }

    @Override
    public void finish() {
        getIntent().removeExtra("project");
        super.finish();
    }

}
