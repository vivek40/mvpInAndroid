package com.assignment.t2s.ui;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.assignment.t2s.IStoreIDListener;
import com.assignment.t2s.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class StoreIDFragment extends DialogFragment{

    View storeIDView;

    @BindView(R.id.store_id)
    EditText storeID;

    @BindView(R.id.cancel_btn)
    Button cancel;

    @BindView(R.id.proceed_btn)
    Button proceed;

    IStoreIDListener storeIDListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        storeIDView = inflater.inflate(R.layout.fragment_store_id, container);
        ButterKnife.bind(this, storeIDView);


        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        getDialog().setTitle("Please enter StoreID");

        return storeIDView;
    }

    @OnClick(R.id.cancel_btn)
    public void cancelClicked(){
        dismiss();
    }

    @OnClick(R.id.proceed_btn)
    public void proceedClicked(){
        if(storeID.getText().toString().length() > 0){
            storeIDListener.onStoreIDEntered(storeID.getText().toString());
            this.dismiss();
        }
    }

    public void setProceedClickListener(IStoreIDListener storeIDListener){
        this.storeIDListener = storeIDListener;
    }


}
