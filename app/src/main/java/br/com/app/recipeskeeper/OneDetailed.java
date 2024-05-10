package br.com.app.recipeskeeper;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class OneDetailed extends AppCompatActivity {
    ImageView recipe_image;
    TextView recipe_name, recipe_type, recipe_lactose, recipe_gluten, recipe_steps;

    String name, hasLactose, hasGluten, type, steps, image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.one_detailed);

        recipe_name = findViewById(R.id.recipeName);
        recipe_type = findViewById(R.id.recipeType);
        recipe_lactose = findViewById(R.id.hasLactose);
        recipe_gluten = findViewById(R.id.hasGluten);
        recipe_image = findViewById(R.id.myImageView);
        recipe_steps= findViewById(R.id.recipeSteps);

        getAndSetIntentData();
    }

    void getAndSetIntentData(){
        if(getIntent().hasExtra("name") && getIntent().hasExtra("type") ){
            //Getting Data from Intent
            name = getIntent().getStringExtra("name");
            hasLactose = getIntent().getStringExtra("lactose");
            hasGluten = getIntent().getStringExtra("gluten");
            image = getIntent().getStringExtra("image");
            steps = getIntent().getStringExtra("steps");
            type = getIntent().getStringExtra("type");
            Log.d("results", hasLactose+" "+hasGluten+" "+image);
            //Setting Intent Data
            recipe_name.setText(name);
            recipe_type.setText(type);
            recipe_lactose.setText(Objects.equals(hasLactose, "1") ? "tem lactose": "sem lactose");
            recipe_gluten.setText(Objects.equals(hasGluten, "1") ? "possui gluten": "gluten free");
            recipe_image.setImageURI(Uri.parse(image));
            recipe_steps.setText(steps);

        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }
}
