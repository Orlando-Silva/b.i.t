package com.example.bit.View.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bit.R;


public class WithdrawListFragment extends  androidx.fragment.app.Fragment {

    public WithdrawListFragment() { }

   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_withdraw_list, container, false);
    }

}
