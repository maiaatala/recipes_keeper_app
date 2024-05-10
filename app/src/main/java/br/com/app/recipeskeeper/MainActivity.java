package br.com.app.recipeskeeper;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton add_button;
    ImageView empty_imageview;
    TextView no_data;
    Database db;
    ArrayList<String> id, name, lactose, type, image, gluten, steps;
    CustomAdapter customAdapter;

    private ActivityResultLauncher<Intent> resultLauncher;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        registerImageButtonResult();
        recyclerView = findViewById(R.id.recyclerView);
        add_button = findViewById(R.id.add_button);
        empty_imageview = findViewById(R.id.empty_imageview);
        no_data = findViewById(R.id.no_data);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RecipeForm.class);
                startActivity(intent);
            }
        });

        db = new Database(MainActivity.this);
        id = new ArrayList<>();
        name = new ArrayList<>();
        lactose = new ArrayList<>();
        image = new ArrayList<>();
        type = new ArrayList<>();
        gluten = new ArrayList<>();
        steps = new ArrayList<>();

        storeDataInArrays();
        customAdapter = new CustomAdapter(MainActivity.this,this, id, name, lactose,
                image, gluten, steps, type);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    void storeDataInArrays(){
        Cursor cursor = db.readAllData();
        if(cursor.getCount() == 0){
            empty_imageview.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
        }else{
            while (cursor.moveToNext()){
                Log.d("queryResult", "0 " + cursor.getString(0));
                Log.d("queryResult", "1 " + cursor.getString(1));
                Log.d("queryResult", "2 " + cursor.getString(2));
                Log.d("queryResult", "3 " + cursor.getString(3));
                Log.d("queryResult", "4 " + cursor.getString(4));
                Log.d("queryResult", "5 " + cursor.getString(5));
                Log.d("queryResult", "6 " + cursor.getString(6));
                id.add(cursor.getString(0));
                image.add(cursor.getString(1));
                name.add(cursor.getString(2));
                steps.add(cursor.getString(3));
                type.add(cursor.getString(4));
                lactose.add(cursor.getString(5));
                gluten.add(cursor.getString(6));
            }
            empty_imageview.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
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
                    MainActivity.this,
                    MainActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }


    private void registerImageButtonResult(){
        resultLauncher = registerForActivityResult(

                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult o) {
                        try {

                            Log.d("on result launcher", o.toString());
                        }catch(Exception e){
                            Toast.makeText(MainActivity.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        };
                    }
                }
        );
    }
}
