package com.app.bit.View.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.app.bit.DAL.Entities.User;
import com.app.bit.R;
import com.app.bit.View.Fragments.ActionBarFragment;
import com.app.bit.View.Fragments.AddressFragment;
import com.app.bit.View.Fragments.DepositFragment;
import com.app.bit.View.Helpers.TabAdapter;
import com.app.bit.View.IntentExtras.Constants;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class DepositActivity extends AppCompatActivity {

    private TabAdapter tabAdapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposits);
        bindViewPager();
        recoverUserFromIntent();
        getSupportActionBar().setTitle("Depósitos");
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
                i = ActionBarFragment.Configuration(this, mUser);
                startActivity(i);
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
        tabAdapter.addFragment(new AddressFragment(), "Endereços de depósito");
        tabAdapter.addFragment(new DepositFragment(), "Histórico de depósito");
    }

    private void bindElements() {
        viewPager = findViewById( R.id.viewPager);
        tabLayout = findViewById(R.id.tabs);
        tabAdapter = new TabAdapter(getSupportFragmentManager());
    }
}
