package com.example.bit.View;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.bit.DAL.Entities.Address;
import com.example.bit.DAL.Entities.User;
import com.example.bit.DAL.Repositories.AddressRepository;
import com.example.bit.R;
import com.example.bit.View.RecycleViewAdapters.AddressesRecycleViewAdapter;
import com.example.bit.databinding.ActivityDepositsBinding;


import java.util.List;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;

public class DepositActivity extends AppCompatActivity {

    ActivityDepositsBinding bindingContent;
    User user;
    AddressRepository addressRepository;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposits);

        bindingContent = DataBindingUtil.setContentView(this, R.layout.activity_deposits);
        user = (User) getIntent().getSerializableExtra("User");
        addressRepository = new AddressRepository(getApplication());
        recyclerView = bindingContent.addressesRecycleView;


        setSupportActionBar(bindingContent.homeToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        List<Address> addresses = addressRepository.getAllByUser(user.getId());

        mAdapter = new AddressesRecycleViewAdapter(addresses);
        recyclerView.setAdapter(mAdapter);

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
            default:
        }
        return super.onOptionsItemSelected(item);
    }

}
