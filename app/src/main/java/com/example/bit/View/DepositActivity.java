package com.example.bit.View;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.bit.DAL.Entities.Address;
import com.example.bit.DAL.Entities.Deposit;
import com.example.bit.DAL.Entities.User;
import com.example.bit.DAL.Repositories.AddressRepository;
import com.example.bit.DAL.Repositories.DepositRepository;
import com.example.bit.R;
import com.example.bit.View.RecycleViewAdapters.AddressesRecycleViewAdapter;
import com.example.bit.View.RecycleViewAdapters.DepositsRecycleViewAdapter;
import com.example.bit.databinding.ActivityDepositsBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;


import java.util.List;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;

public class DepositActivity extends AppCompatActivity {

    ActivityDepositsBinding bindingContent;
    User user;
    AddressRepository addressRepository;
    DepositRepository depositRepository;

    private RecyclerView addressRecyclerView;
    private RecyclerView depositsRecycleView;
    private RecyclerView.Adapter mAddressAdapter;
    private RecyclerView.Adapter mDepositsAdapter;
    private RecyclerView.LayoutManager addresslayoutManager;
    private RecyclerView.LayoutManager depositslayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposits);

        bindingContent = DataBindingUtil.setContentView(this, R.layout.activity_deposits);
        user = (User) getIntent().getSerializableExtra("User");
        addressRepository = new AddressRepository(getApplication());
        depositRepository = new DepositRepository(getApplication());

        setSupportActionBar(bindingContent.homeToolbar);
        setTitle("Depósitos");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        depositsRecycleView = bindingContent.depositsRecycleView;
        addressRecyclerView = bindingContent.addressesRecycleView;

        addressRecyclerView.setHasFixedSize(true);
        depositsRecycleView.setHasFixedSize(true);

        addresslayoutManager = new LinearLayoutManager(this);
        addressRecyclerView.setLayoutManager(addresslayoutManager);
        depositsRecycleView.setLayoutManager(depositslayoutManager);

        List<Address> addresses = addressRepository.getAllByUser(user.getId());

        if(addresses.isEmpty()) {
            try {
                addressRepository.generateFirstAddress(user.getId());
                Intent i = new Intent(DepositActivity.this, DepositActivity.class);
                i.putExtra("User", user);
                startActivity(i);
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mAddressAdapter = new AddressesRecycleViewAdapter(addresses);
        addressRecyclerView.setAdapter(mAddressAdapter);

        List<Deposit> deposits = depositRepository.getAllByUser(user.getId());

        if(deposits != null && !deposits.isEmpty()) {

            mDepositsAdapter = new DepositsRecycleViewAdapter(deposits);
            depositsRecycleView.setAdapter(mDepositsAdapter);
        }

        bindingContent.btnNewAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    addressRepository.generateAddress(user.getId());
                    new MaterialAlertDialogBuilder(v.getContext(), R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered)
                            .setTitle("Aviso")
                            .setMessage("Novo endereço gerado com sucesso!")
                            .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i = new Intent(DepositActivity.this, DepositActivity.class);
                                    i.putExtra("User", user);
                                    startActivity(i);
                                    finish();
                                }
                            })
                            .create()
                            .show();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

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
            case android.R.id.home:
                this.finish();
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

}
