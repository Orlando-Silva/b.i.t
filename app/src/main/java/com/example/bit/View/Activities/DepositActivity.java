package com.example.bit.View.Activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.bit.DAL.Entities.Deposit;
import com.example.bit.DAL.Entities.User;
import com.example.bit.DAL.Repositories.AddressRepository;
import com.example.bit.DAL.Repositories.DepositRepository;
import com.example.bit.R;
import com.example.bit.View.Fragments.AddressFragment;
import com.example.bit.View.Helpers.TabAdapter;
import com.example.bit.View.RecycleViewAdapters.DepositsRecycleViewAdapter;
import com.example.bit.databinding.ActivityDepositsBinding;
import com.google.android.material.tabs.TabLayout;


import java.util.List;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class DepositActivity extends AppCompatActivity {

    ActivityDepositsBinding bindingContent;
    User user;
    AddressRepository addressRepository;
    DepositRepository depositRepository;

    private RecyclerView depositsRecycleView;
    private RecyclerView.Adapter mDepositsAdapter;
    private RecyclerView.LayoutManager depositslayoutManager;

    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposits);

         // bindingContent = DataBindingUtil.setContentView(this, R.layout.activity_deposits);
        user = (User) getIntent().getSerializableExtra("User");
        addressRepository = new AddressRepository(getApplication());
        depositRepository = new DepositRepository(getApplication());

        setSupportActionBar((androidx.appcompat.widget.Toolbar)findViewById(R.id.homeToolbar));
        setTitle("Depósitos");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = findViewById( R.id.AddressViewPager);
        tabLayout = findViewById(R.id.tabs);
        adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new AddressFragment(), "Endereços de depósito");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


        /*


        depositsRecycleView = bindingContent.depositsRecycleView;

        depositsRecycleView.setHasFixedSize(true);

        depositslayoutManager = new LinearLayoutManager(this);

        depositsRecycleView.setLayoutManager(depositslayoutManager);



        List<Deposit> deposits = depositRepository.getAllByUser(user.getId());


        if(deposits != null && !deposits.isEmpty()) {

            mDepositsAdapter = new DepositsRecycleViewAdapter(deposits);
            depositsRecycleView.setAdapter(mDepositsAdapter);
        }

        bindingContent.homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Home(v);
            }
        });

        bindingContent.depositsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Deposits(v);
            }
        });
        */

    }

    public void Home(View view) {
        Intent i = new Intent(DepositActivity.this, HomeActivity.class);
        i.putExtra("User", user);
        startActivity(i);
        finish();
    }

    public void Deposits(View view) {

    }

    public void EditUser() {
        Intent i = new Intent(DepositActivity.this, EditUserActivity        .class);
        i.putExtra("User", user);
        startActivity(i);
    }

    public void Logout() {
        Intent i = new Intent(DepositActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id)  {
            case R.id.action_editAccount:
                EditUser();
                break;
            case R.id.action_configurations:
                break;
            case R.id.action_logout:
                Logout();
            case android.R.id.home:
                this.finish();
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

}
