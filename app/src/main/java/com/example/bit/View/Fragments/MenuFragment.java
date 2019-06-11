package com.example.bit.View.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.bit.DAL.Entities.User;
import com.example.bit.R;
import com.example.bit.View.Activities.DepositActivity;
import com.example.bit.View.Activities.HomeActivity;
import com.example.bit.View.Activities.WithdrawActivity;
import com.example.bit.View.IntentExtras.Constants;
import com.example.bit.databinding.FragmentMenuBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

public class MenuFragment extends androidx.fragment.app.Fragment {

    private User mUser;
    private FragmentMenuBinding mbindingContext;
    private BottomNavigationView navigationView;


    public MenuFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = bindView(inflater, container, savedInstanceState);
        recoverUserFromIntent();
        navigationView = mbindingContext.navigationView;
        navigationView.setOnNavigationItemSelectedListener(BottomNavOnClickHandler());
        return view;
    }

    private void recoverUserFromIntent() {
        mUser = (User) getActivity().getIntent().getSerializableExtra(Constants.USER_INTENT);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener BottomNavOnClickHandler() {
        return new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true); 
                switch (item.getItemId()) {
                    case R.id.navigation_Deposits:
                        Deposits(getView());
                        break;
                    case R.id.navigation_Home:
                        Home(getView());
                        break;
                    case R.id.navigation_Withdraw:
                        Withdraw(getView());
                        break;
                }
                return true;
            }
        };
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

    public void Withdraw(View view) {
        Intent i = new Intent(getContext(), WithdrawActivity.class);
        i.putExtra(Constants.USER_INTENT, mUser);
        startActivity(i);
        getActivity().finish();
    }

}
