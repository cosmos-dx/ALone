package com.idontknow.alone;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyDialogue extends AppCompatDialogFragment  implements DialogInterface.OnDismissListener {

    private EditText edtkeytitle;
    MyDbCode db;
    //MyDbCode db = new MyDbCode(getActivity());
    RecyclerView recyclerView;
    ArrayList<String> data_key, value_book;
    CustomAdapter customAdapter;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {


        data_key = new ArrayList<>();
        value_book = new ArrayList<>();


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialogue_layout, null);

        builder.setView(view)
                .setTitle(" ")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("Create-Title", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String key_title = edtkeytitle.getText().toString();
                        if(key_title.isEmpty()|| key_title.contains(" ")){
                            //Error

                            edtkeytitle.setError("Provide Title");
                            Toast.makeText(getContext(), "Whitespace Not Allowed", Toast.LENGTH_SHORT).show();
                        }
                        else {

                            db = new MyDbCode(getContext());
                            db.insertDataStore(key_title, "");
//
//                            storeDataInArrays();
//
//                            customAdapter = new CustomAdapter(getActivity(),getContext(), data_key, value_book);
//                            recyclerView.setAdapter(customAdapter);
//                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//

                            //Change in future
                            Toast.makeText(getContext(), "Added", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

        recyclerView = view.findViewById(R.id.recHome);
        edtkeytitle = view.findViewById(R.id.edtkeytitle);
        return builder.create();
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        //storeDataInArrays();

        super.onDismiss(dialog);
        final Activity activity = getActivity();
        if(activity instanceof DialogInterface.OnDismissListener){
            ((DialogInterface.OnDismissListener) activity).onDismiss(dialog);
        }
    }

    void storeDataInArrays(){
        Cursor cursor = db.readAllData();

        while (cursor.moveToNext()){
            data_key.add(cursor.getString(0));
            value_book.add(cursor.getString(1));

        }

    }


}
