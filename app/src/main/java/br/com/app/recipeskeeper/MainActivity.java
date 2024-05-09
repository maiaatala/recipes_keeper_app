package br.com.app.recipeskeeper;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    String[] arraySpinner = new String[] {
            "cafe-da-manha", "almoço", "lanche", "janta", "salada", "sobremesa",
    };
    Spinner spinnerType;
    ImageButton imgButton;

    ActivityResultLauncher<Intent> resultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgButton = findViewById(R.id.foodImage);
        registerResult();
        spinnerType = findViewById(R.id.type);

        //spinner options
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapter);

        //image button
        imgButton.setOnClickListener(view -> pickImage());
    }

    private void pickImage(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R && android.os.ext.SdkExtensions.getExtensionVersion(android.os.Build.VERSION_CODES.R) >= 2) {
            Intent i = new Intent(MediaStore.ACTION_PICK_IMAGES);
            resultLauncher.launch(i);
        }
    }

    private void registerResult(){
        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult o) {
                        try {
                            Uri imageUri = o.getData().getData();
                            imgButton.setImageURI(imageUri);
                        }catch(Exception e){
                            Toast.makeText(MainActivity.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        };
                    }
                }
        );
    }

}