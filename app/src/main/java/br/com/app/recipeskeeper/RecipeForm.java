package br.com.app.recipeskeeper;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class RecipeForm extends AppCompatActivity {
    String[] arraySpinner = new String[] {
            "cafe-da-manha", "almoço", "lanche", "janta", "salada", "sobremesa",
    };
    Spinner spinnerType;
    ImageButton imgButton;
    EditText inputName, inputSteps;
    CheckBox checkLactose, checkGluten;
    Uri recipePicture;
    Button buttonSubmit;

    ActivityResultLauncher<Intent> resultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_form);
        imgButton = findViewById(R.id.foodImage);
        registerImageButtonResult();
        spinnerType = findViewById(R.id.type);
        buttonSubmit = findViewById(R.id.submit);
        inputName = findViewById(R.id.nome);
        inputSteps = findViewById(R.id.steps);
        checkLactose = findViewById(R.id.lactose);
        checkGluten = findViewById(R.id.gluten);

        //spinner options
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapter);

        //image button
        imgButton.setOnClickListener(view -> pickImage());

        //submit button
        buttonSubmit.setOnClickListener(this::onSubmitClick);
    }

    private void onSubmitClick(View view){
        Database db = new Database(RecipeForm.this);
        String name = inputName.getText().toString().trim();
        String steps = inputSteps.getText().toString().trim();
        boolean lactose = checkLactose.isChecked();
        boolean gluten = checkGluten.isChecked();
        String type = spinnerType.getSelectedItem().toString();

        boolean response = db.addRecipe(name, steps, recipePicture, type, lactose, gluten );

        if (response){
            Toast.makeText(RecipeForm.this,"DADOS INSERIDOS COM SUCESSO", Toast.LENGTH_LONG).show();
            Intent i=new Intent(
                    RecipeForm.this,
                    OneDetailed.class);
            startActivity(i);
        }else{
            Toast.makeText(RecipeForm.this, "FALHOU", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.go_back){
            Intent i=new Intent(
                    RecipeForm.this,
                    MainActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    private void pickImage(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R && android.os.ext.SdkExtensions.getExtensionVersion(android.os.Build.VERSION_CODES.R) >= 2) {
            Intent i = new Intent(MediaStore.ACTION_PICK_IMAGES);
            resultLauncher.launch(i);
        }
    }

    private void registerImageButtonResult(){
        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult o) {
                        try {
                            Uri imageUri = o.getData().getData();
                            recipePicture = imageUri;
                            imgButton.setImageURI(imageUri);
                        }catch(Exception e){
                            Toast.makeText(RecipeForm.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        };
                    }
                }
        );
    }

}