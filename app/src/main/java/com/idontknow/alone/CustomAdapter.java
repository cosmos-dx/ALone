package com.idontknow.alone;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Vibrator;
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

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>  {
    private Context context;
    private Activity activity;
    private ArrayList book_key, book_value, key_hash;
    Dialog myupdialog;
    TextView passStar;
    public String up_key_text;



    public CustomAdapter(Activity activity, Context context,  ArrayList book_key, ArrayList book_value){
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
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.key_txt.setText(String.valueOf(book_key.get(position)));
        //holder.value_txt.setText(String.valueOf(book_value.get(position)));
        String value = String.valueOf(book_value.get(position));
        if(!value.isEmpty()){
            passStar = holder.itemView.findViewById(R.id.user_status);
            passStar.setTextColor(Color.parseColor("#B24BF3"));
            passStar.setTypeface(passStar.getTypeface(), Typeface.BOLD);
        }
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
                String key_titleUpdateDia = String.valueOf(book_key.get(position));
                String key_valueUpdateDia = String.valueOf(book_value.get(position));

                //((MyAdapterInterface) context).openUpdateDialogue(key_titleUpdateDia, key_valueUpdateDia);
                ((MyAdapterInterface) context).AlertDeleteOne(key_titleUpdateDia, key_valueUpdateDia);

                //((MyAdapterInterface) context).AlertDeleteAll();



                return true;
            }
        });

        holder.user_status.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Vibrator vibe = (Vibrator)
                        context.getSystemService(context.VIBRATOR_SERVICE) ;
                String key_valueUpdateDia = String.valueOf(book_value.get(position));
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Copied", key_valueUpdateDia);
                clipboard.setPrimaryClip(clip);

                Toast.makeText(context,"Copied", Toast.LENGTH_SHORT).show();
                vibe.vibrate(50);

                return true;
            }
        });
    


    }


    @Override
    public int getItemCount() {
        return book_key.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView  key_txt,update_edtkeytitle ,update_key_title;
        LinearLayout mainLayout, update_dialogue;
        Button value_txt, user_status;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            key_txt = itemView.findViewById(R.id.user_name);
            value_txt = itemView.findViewById(R.id.user_status);

            user_status = itemView.findViewById(R.id.user_status);

            mainLayout = itemView.findViewById(R.id.mainLayout);

//            update_edtkeytitle = itemView.findViewById(R.id.update_edtkeytitle);
//            update_key_title = itemView.findViewById(R.id.update_key_title);
//            update_dialogue = itemView.findViewById(R.id.update_dialogue);

        }

    }

    public interface MyAdapterInterface {
        //void openUpdateDialogue(String key_titleUpdateDia, String key_valueUpdateDia);
        void AlertDeleteOne(String key_titleUpdateDia, String key_valueUpdateDia);

        //void AlertDeleteAll();
        //void openDialogue();
    }


}
