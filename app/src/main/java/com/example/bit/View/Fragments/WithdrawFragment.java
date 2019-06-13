package com.example.bit.View.Fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bit.DAL.Entities.User;
import com.example.bit.DAL.Repositories.UserRepository;
import com.example.bit.DAL.Repositories.WithdrawRepository;
import com.example.bit.Helpers.StringHelpers;
import com.example.bit.R;
import com.example.bit.View.IntentExtras.Constants;
import com.example.bit.databinding.FragmentWithdrawBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.text.DecimalFormat;

import androidx.databinding.DataBindingUtil;


public class WithdrawFragment extends androidx.fragment.app.Fragment {

    private User mUser;
    private UserRepository mUserRepository;
    private WithdrawRepository mWithdrawRepository;
    private FragmentWithdrawBinding mbindingContext;

    public WithdrawFragment() {    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  bindView(inflater, container, savedInstanceState);
        recoverUserFromIntent();
        bindRepositories();
        setWithdrawListnerHandler();
        displayBalance();
        return view;
    }

    private void displayBalance() {
        double balance = mUserRepository.getBalance(mUser.getId());
        DecimalFormat df = new DecimalFormat("#.########");
        mbindingContext.currentBalance.setText("Seu saldo atual é de: " + df.format(balance));
    }

    private void bindRepositories() {
        mUserRepository = new UserRepository(getActivity().getApplication());
        mWithdrawRepository = new WithdrawRepository(getActivity().getApplication());
    }

    private void setWithdrawListnerHandler() {
        mbindingContext.withdrawButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValid()) {
                    try {
                        mWithdrawRepository.makeWithdraw (
                                mUser.getId(),
                                Double.parseDouble(mbindingContext.tvWithdrawAmount.getEditText().getText().toString()),
                                mbindingContext.tvDestinationAddress.getEditText().getText().toString()
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private boolean isValid() {

        if(mbindingContext.tvWithdrawAmount.getEditText().getText() == null || StringHelpers.isNullEmptyOrWhitespace(mbindingContext.tvWithdrawAmount.getEditText().getText().toString())) {
            showMaterialMessage("Aviso", "Insira a quantidade desejada!");
            return false;
        }

        if(mbindingContext.tvDestinationAddress.getEditText().getText() == null || StringHelpers.isNullEmptyOrWhitespace(mbindingContext.tvDestinationAddress.getEditText().getText().toString())) {
            showMaterialMessage("Aviso", "Insira o endereço de destino!");
            return false;
        }

        double balance = mUserRepository.getBalance(mUser.getId());

        if(balance < Double.parseDouble(mbindingContext.tvWithdrawAmount.getEditText().getText().toString())) {
            showMaterialMessage("Aviso", "Saldo insuficiente!");
            return false;
        }

        return true;
    }

    private void recoverUserFromIntent() {
        mUser = (User) getActivity().getIntent().getSerializableExtra(Constants.USER_INTENT);
    }

    private View bindView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mbindingContext = DataBindingUtil.inflate(inflater, R.layout.fragment_withdraw, container, false);
        return mbindingContext.getRoot();
    }

    private void showMaterialMessage(String title, String message) {
        new MaterialAlertDialogBuilder(getActivity(), R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Accept", null)
                .create()
                .show();
    }

}