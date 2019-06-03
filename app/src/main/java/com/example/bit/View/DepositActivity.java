package com.example.bit.View;

import android.os.Bundle;
import android.app.Activity;

import com.example.bit.DAL.Entities.Address;
import com.example.bit.DAL.Entities.User;
import com.example.bit.DAL.Repositories.AddressRepository;
import com.example.bit.R;
import com.example.bit.databinding.ActivityDepositsBinding;


import java.util.List;

import androidx.databinding.DataBindingUtil;

public class DepositActivity extends Activity {

    ActivityDepositsBinding bindingContent;
    User user;
    AddressRepository addressRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposits);
        bindingContent = DataBindingUtil.setContentView(this, R.layout.activity_deposits);
        user = (User) getIntent().getSerializableExtra("User");
        addressRepository = new AddressRepository(getApplication());
        String finalResponse = "";

        if(addressRepository.userHasAddress(user.getId())) {
            List<Address> addresses = addressRepository.getAllByUser(user.getId());


            for (Address address: addresses) {
                finalResponse += "User Public Address: " + address.getPublicKey() + ", User Private Address" + address.getPrivateKey() + ", ";
            }


        } else {
            try {
                Address address = addressRepository.generateFirstAddress(user.getId());
                finalResponse += "User Public Address: " + address.getPublicKey() + ", User Private Address" + address.getPrivateKey() + ". ";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        bindingContent.Result.setText(finalResponse);

    }

}
