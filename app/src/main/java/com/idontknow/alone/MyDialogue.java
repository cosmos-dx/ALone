package com.idontknow.alone;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
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

public class MyDialogue extends DialogFragment {
    //------------------------------------------------------------------------------- Initialization
        private String anyKeyId;
        EditText editField, edtValuetitle;
        Button save_btn, cancel_btn;
        MyDbCode db;
        //MyDbCode db = new MyDbCode(getActivity());
        //RecyclerView recyclerView;
        ArrayList<String> data_key, value_book;

        EncryptDecrypt encdcp;
        CustomAdapter customAdapter;
        RecyclerView recyclerView;
        private MydiaInterface mydiaInterface;

    //------------------------------------------------------------------------------- Initialization


        public MyDialogue() {
            // Required empty public constructor
        }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        encdcp = new EncryptDecrypt();
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
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

                    View view = inflater.inflate(R.layout.dialogue_layout, container, false);
                    Bundle args = getArguments();
                    editField = view.findViewById(R.id.edtkeytitle);
                    //edtValuetitle = view.findViewById(R.id.edtValuetitle);
                    save_btn = view.findViewById(R.id.createTitle);
                    cancel_btn = view.findViewById(R.id.cancelBtn);
                    recyclerView = view.findViewById(R.id.recHome);
                    cancel_btn.setOnClickListener(view1 -> {
                        dismiss();
                    });
                    save_btn.setOnClickListener(view2 -> {
                        String key_title = editField.getText().toString();
                        //String key_value = edtValuetitle.getText().toString();
                        if(key_title.isEmpty()){
                            editField.setError("Provide Title");
                            aloneMessageSender("No No Awwwww !!");
                        }

                        else {

                            key_title = key_title.trim().replaceAll(" +", " ");

                            db = new MyDbCode(getContext());
                            db.insertDataStore(key_title,"");

                            //getActivity().recreate();
                            mydiaInterface.text(key_title);

                            dismiss();
                        }
                    });
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

    private void aloneMessageSender(String setMessage) {
        ((FinalScreen) requireActivity()).messageFromAloneFragment(setMessage);
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            mydiaInterface = (MydiaInterface) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + "issue in interface");
        }

    }

    public interface MydiaInterface{
            void text(String key_title);
    }

}