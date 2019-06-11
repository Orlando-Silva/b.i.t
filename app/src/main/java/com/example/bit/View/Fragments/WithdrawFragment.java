package com.example.bit.View.Fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bit.DAL.Entities.User;
import com.example.bit.R;
import com.example.bit.View.IntentExtras.Constants;
import com.example.bit.databinding.FragmentWithdrawBinding;

import androidx.databinding.DataBindingUtil;


public class WithdrawFragment extends androidx.fragment.app.Fragment {

    private User mUser;
    private FragmentWithdrawBinding mbindingContext;

    public WithdrawFragment() {    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  bindView(inflater, container, savedInstanceState);
        recoverUserFromIntent();
        setWithdrawListnerHandler();
        return view;
    }

    private void setWithdrawListnerHandler() {
        mbindingContext.withdrawButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }

    private void recoverUserFromIntent() {
        mUser = (User) getActivity().getIntent().getSerializableExtra(Constants.USER_INTENT);
    }

    private View bindView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mbindingContext = DataBindingUtil.inflate(inflater, R.layout.fragment_withdraw, container, false);
        return mbindingContext.getRoot();
    }

}
