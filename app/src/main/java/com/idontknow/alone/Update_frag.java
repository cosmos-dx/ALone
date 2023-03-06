package com.idontknow.alone;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Update_frag extends DialogFragment {


    //------------------------------------------------------------------------------- Initialization
    private String anyKeyId, key_titleUpdateDia,key_valueUpdateDia;
    EditText edit_text_key,filledTextField_Value;
    Button update_btn, gen_btn;
    MyDbCode db;
    //MyDbCode db = new MyDbCode(getActivity());
    //RecyclerView recyclerView;
    ArrayList<String> data_key, value_book;

    EncryptDecrypt encdcp;
    CustomAdapter customAdapter;
    RecyclerView recyclerView;


    //------------------------------------------------------------------------------- Initialization


    public Update_frag() {
        // Required empty public constructor
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        data_key = new ArrayList<>();
        value_book = new ArrayList<>();
        if (getArguments() != null){
            if(getArguments().getBoolean("SHOW_VIEW_INPUT_DIALOG")){
                return super.onCreateDialog(savedInstanceState);
            }
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        Bundle args = getArguments();
        anyKeyId = args.getString("KeyId");
        builder.setTitle(args.getString("title"));
        builder.setMessage(args.getString("DisplayText"));
        builder.setNegativeButton("Cancel", ((dialogInterface, i) -> dismiss()));
        builder.setPositiveButton("Create-Title", ((dialogInterface, i) ->
                myCreateFunction("Hello from Dialog")));


        return builder.create();
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.update_dialoge, container, false);
        Bundle args = getArguments();
        edit_text_key = view.findViewById(R.id.edit_text_key);
        filledTextField_Value = view.findViewById(R.id.edit_text_key2);
        update_btn = view.findViewById(R.id.update_btn);
        gen_btn = view.findViewById(R.id.gen_btn);

        key_titleUpdateDia = args.getString("Key");
        key_valueUpdateDia = args.getString("Value");

        edit_text_key.setText(key_titleUpdateDia);
        filledTextField_Value.setText(key_valueUpdateDia);



        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
        }
    }
    @Override
    public void onResume(){
        super.onResume();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean setFullScreen = false;
        if (getArguments() != null) {
            setFullScreen = getArguments().getBoolean("fullScreen");
        }
        if (setFullScreen){
            setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_DeviceDefault);
        }
    }
    @Override
    public void onDestroyView(){
        super.onDestroyView();
    }
    @Override
    public void onDismiss(@NonNull final DialogInterface dialog) {
        super.onDismiss(dialog);
        final Activity activity = getActivity();
        if (activity instanceof DialogInterface.OnDismissListener) {
            ((DialogInterface.OnDismissListener) activity).onDismiss(dialog);
        }
    }
    private void myCreateFunction(String getMessage) {
        Toast.makeText(getContext(), "Do Required for Create\n"+getMessage,
                Toast.LENGTH_LONG);
    }







}