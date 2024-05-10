package br.com.app.recipeskeeper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>{

    private Context context;
    private Activity activity;
    private ArrayList id, name, lactose, image, steps, gluten, type;

    public CustomAdapter(Activity activity, Context context, ArrayList id, ArrayList name, ArrayList lactose,
                         ArrayList image, ArrayList gluten, ArrayList steps, ArrayList type){
        this.activity = activity;
        this.context = context;
        this.id = id;
        this.name = name;
        this.lactose = lactose;
        this.image = image;
        this.gluten = gluten;
        this.steps = steps;
        this.type = type;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recipe_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.id_text.setText(String.valueOf(id.get(position)));
        holder.name_text.setText(String.valueOf(name.get(position)));
        holder.type_text.setText(String.valueOf(type.get(position)));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, OneDetailed.class);
                intent.setAction(Intent.ACTION_VIEW);
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.putExtra("id", String.valueOf(id.get(position)));
                intent.putExtra("name", String.valueOf(name.get(position)));
                intent.putExtra("lactose", String.valueOf(lactose.get(position)));
                intent.putExtra("type", String.valueOf(type.get(position)));
                intent.putExtra("gluten", String.valueOf(gluten.get(position)));
                intent.putExtra("image", String.valueOf(image.get(position)));
                intent.putExtra("steps", String.valueOf(steps.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return id.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView id_text, name_text, type_text;
        LinearLayout mainLayout;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id_text = itemView.findViewById(R.id.id_text);
            name_text = itemView.findViewById(R.id.name_text);
            type_text = itemView.findViewById(R.id.type_text);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            //Animate Recyclerview
            Animation translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);
        }

    }
}
