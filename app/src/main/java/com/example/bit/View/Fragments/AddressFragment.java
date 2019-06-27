package com.example.bit.View.Fragments;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bit.DAL.Entities.Address;
import com.example.bit.DAL.Entities.User;
import com.example.bit.R;
import com.example.bit.View.Activities.DepositActivity;
import com.example.bit.View.Activities.HomeActivity;
import com.example.bit.View.IntentExtras.Constants;
import com.example.bit.View.RecycleViewAdapters.AddressAdapter;
import com.example.bit.View.ViewModels.AddressViewModel;
import com.example.bit.databinding.FragmentAddressBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class AddressFragment extends androidx.fragment.app.Fragment {

    private User mUser;
    private AddressViewModel mAddressViewModel;
    private RecyclerView mAddressRecyclerView;
    private AddressAdapter mAddressAdapter;
    private RecyclerView.LayoutManager mAddresslayoutManager;
    private FragmentAddressBinding mbindingContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = bindView(inflater, container, savedInstanceState);
        recoverUserFromIntent();
        bindFragmentElements(container);
        return view;
    }

    private void bindFragmentElements(ViewGroup container) {
        createRecycleView(container);
        createViewModel();
        createNewAddressListener();
    }

    private void createRecycleView(ViewGroup container) {

        mAddressRecyclerView = mbindingContext.addressesRecycleView;
        mAddressRecyclerView.setHasFixedSize(true);
        mAddresslayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        mAddressRecyclerView.setLayoutManager(mAddresslayoutManager);

        mAddressAdapter = new AddressAdapter(getActivity());
        mAddressRecyclerView.setAdapter(mAddressAdapter);
    }

    private void createViewModel() {
        mAddressViewModel = ViewModelProviders.of(getActivity()).get(AddressViewModel.class);
        verifyAndGenerateFirstAddress();
        mAddressViewModel.getAllLiveDataByUser(mUser.getId()).observe(this, new Observer<List<Address>>() {
            @Override
            public void onChanged(List<Address> addresses) {
                mAddressAdapter.setAddress(addresses);
            }

        });
    }

    private void createNewAddressListener() {
        mbindingContext.btnNewAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mAddressViewModel.generateAddress(mUser.getId());
                    generateCreateAddressMaterialDialog(v)
                        .create()
                        .show();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private MaterialAlertDialogBuilder generateCreateAddressMaterialDialog(View v) {
        return new MaterialAlertDialogBuilder(v.getContext(), R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered)
                .setTitle("Aviso")
                .setMessage("Novo endereço gerado com sucesso!")
                .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAddressAdapter.notifyDataSetChanged();
                    }
                });
    }

    private void verifyAndGenerateFirstAddress() {
        try {
            List<Address> addresses = mAddressViewModel.getAllByUser(mUser.getId());

            if (addresses.isEmpty()) {
                mAddressViewModel.generateFirstAddress(mUser.getId());
                Intent i = new Intent(getContext(), DepositActivity.class);
                i.putExtra(Constants.USER_INTENT, mUser);
                startActivity(i);
                getActivity().finish();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void recoverUserFromIntent() {
        mUser = (User) getActivity().getIntent().getSerializableExtra(Constants.USER_INTENT);
    }

    private View bindView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mbindingContext = DataBindingUtil.inflate(inflater, R.layout.fragment_address, container, false);
        return mbindingContext.getRoot();
    }

}
