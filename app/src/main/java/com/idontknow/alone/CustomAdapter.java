package com.idontknow.alone;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>{
    private Context context;
    private Activity activity;
    private ArrayList book_key, book_value, key_hash;
    Dialog myupdialog;
    public String up_key_text;

    CustomAdapter(Activity activity, Context context,  ArrayList book_key, ArrayList book_value){
        this.activity = activity;
        this.context = context;
        this.book_key = book_key;
        this.book_value = book_value;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.storevaluesscreen, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.key_txt.setText(String.valueOf(book_key.get(position)));
        //holder.itemView.setBackgroundColor(Color.parseColor("#000000"));
        //holder.value_txt.setText(String.valueOf(book_value.get(position)));


        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateData.class);
                intent.putExtra("title", String.valueOf(book_key.get(position)));
                intent.putExtra("value", String.valueOf(book_value.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });

        ////////////////////////////////////////////////////////////////////////////////////////////////////////

        holder.mainLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //MyDialogue myDialogue = new MyDialogue();
                Toast.makeText(context, "LongPressed", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    


    }


    @Override
    public int getItemCount() {
        return book_key.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView  key_txt,update_edtkeytitle,update_key_title;
        LinearLayout mainLayout, update_dialogue;
        Button value_txt;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            key_txt = itemView.findViewById(R.id.user_name);
            value_txt = itemView.findViewById(R.id.user_status);

            mainLayout = itemView.findViewById(R.id.mainLayout);

            update_edtkeytitle = itemView.findViewById(R.id.update_edtkeytitle);
            update_key_title = itemView.findViewById(R.id.update_key_title);
            update_dialogue = itemView.findViewById(R.id.update_dialogue);

        }

    }
    public void openDialogue(){
        MyDialogue myDialogue = new MyDialogue();

//        Bundle args = new Bundle();
//        args.putString("title", "Add Sample Title");
//        args.putString("DisplayText", "Once Upon a time There was a king.. ");
//        args.putString("KeyId", "AnyThing Required... ");
//        args.putString("Key1", "Values222");
//        args.putString("Key2", "Values 3333");
//        args.putBoolean("SHOW_VIEW_INPUT_DIALOG", true); // will show on onCreateView
//        myDialogue.setArguments(args);
//        FragmentTransaction ft = myupdialog.getFr().beginTransaction();
//        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
//        if (prev != null) {
//            ft.remove(prev);
//        }
//        ft.addToBackStack(null);
//        myDialogue.show(ft, "dialog");
    }
}
