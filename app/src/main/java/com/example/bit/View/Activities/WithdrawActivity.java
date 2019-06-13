package com.example.bit.View.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.bit.DAL.Entities.User;
import com.example.bit.R;
import com.example.bit.View.Fragments.ActionBarFragment;
import com.example.bit.View.Fragments.WithdrawFragment;
import com.example.bit.View.Fragments.WithdrawListFragment;
import com.example.bit.View.Helpers.TabAdapter;
import com.example.bit.View.IntentExtras.Constants;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class WithdrawActivity extends AppCompatActivity {

    private TabAdapter tabAdapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);
        recoverUserFromIntent();
        bindViewPager();
        getSupportActionBar().setTitle("Retiradas");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent i = null;
        switch (id)  {
            case R.id.action_editAccount:
                i = ActionBarFragment.EditUser(this, mUser);
                startActivity(i);
                break;
            case R.id.action_configurations:
                break;
            case R.id.action_logout:
                i = ActionBarFragment.Logout(this);
                startActivity(i);
                break;
            case android.R.id.home:
                finish();
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }


    private void bindViewPager() {
        bindElements();
        addFragments();
        setupAdapter();
    }

    private void recoverUserFromIntent() {
        mUser = (User) this.getIntent().getSerializableExtra(Constants.USER_INTENT);
    }

    private void setupAdapter() {
        viewPager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void addFragments() {
        tabAdapter.addFragment(new WithdrawFragment(), "Efetuar Retirada");
        tabAdapter.addFragment(new WithdrawListFragment(), "Histórico de Retirada");
    }

    private void bindElements() {
        viewPager = findViewById( R.id.viewPager);
        tabLayout = findViewById(R.id.tabs);
        tabAdapter = new TabAdapter(getSupportFragmentManager());
    }


}