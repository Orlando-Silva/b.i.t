package com.example.bit.View.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.bit.DAL.Entities.User;
import com.example.bit.R;
import com.example.bit.View.Activities.ConfigurationActivity;
import com.example.bit.View.Activities.EditUserActivity;
import com.example.bit.View.Activities.MainActivity;
import com.example.bit.View.IntentExtras.Constants;
import com.example.bit.databinding.FragmentActionBarBinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

public class ActionBarFragment extends androidx.fragment.app.Fragment  {

    private User mUser;
    private FragmentActionBarBinding mbindingContext;

    public ActionBarFragment() {   }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = bindView(inflater, container, savedInstanceState);
        recoverUserFromIntent();
        return view;
    }

    private void recoverUserFromIntent() {
        mUser = (User) getActivity().getIntent().getSerializableExtra(Constants.USER_INTENT);
    }

    private View bindView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mbindingContext = DataBindingUtil.inflate(inflater, R.layout.fragment_action_bar, container, false);
        Toolbar toolbar = mbindingContext.toolbar;
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return mbindingContext.getRoot();
    }


    public static Intent EditUser(Context context, User user) {
        Intent i = new Intent(context, EditUserActivity.class);
        i.putExtra(Constants.USER_INTENT, user);
        return i;
    }

    public static Intent Logout(Context context) {
        Intent i = new Intent(context, MainActivity.class);
        return i;
    }

    public static Intent Configuration(Context context, User user) {
        Intent i = new Intent(context, ConfigurationActivity.class);
        i.putExtra(Constants.USER_INTENT, user);
        return i;
    }
}
