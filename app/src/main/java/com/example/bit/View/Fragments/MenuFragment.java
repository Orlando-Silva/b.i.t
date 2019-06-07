package com.example.bit.View.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bit.DAL.Entities.User;
import com.example.bit.R;
import com.example.bit.View.Activities.DepositActivity;
import com.example.bit.View.Activities.HomeActivity;
import com.example.bit.View.IntentExtras.Constants;
import com.example.bit.databinding.FragmentMenuBinding;

import androidx.databinding.DataBindingUtil;

public class MenuFragment extends androidx.fragment.app.Fragment {

    private User mUser;
    private FragmentMenuBinding mbindingContext;

    public MenuFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = bindView(inflater, container, savedInstanceState);
        recoverUserFromIntent();
        addOnClickListeners();
        return view;
    }

    private void addOnClickListeners() {

        mbindingContext.homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Home(v);
            }
        });

        mbindingContext.depositsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Deposits(v);
            }
        });

    }

    private void recoverUserFromIntent() {
        mUser = (User) getActivity().getIntent().getSerializableExtra(Constants.USER_INTENT);
    }

    private View bindView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mbindingContext = DataBindingUtil.inflate(inflater, R.layout.fragment_menu, container, false);
        return mbindingContext.getRoot();
    }

    public void Home(View view) {
        Intent i = new Intent(getContext(), HomeActivity.class);
        i.putExtra(Constants.USER_INTENT, mUser);
        startActivity(i);
        getActivity().finish();
    }

    public void Deposits(View view) {
        Intent i = new Intent(getContext(), DepositActivity.class);
        i.putExtra(Constants.USER_INTENT, mUser);
        startActivity(i);
        getActivity().finish();
    }

}
