package com.example.bit.View.Fragments;


import android.app.Application;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bit.DAL.Entities.Address;
import com.example.bit.DAL.Entities.User;
import com.example.bit.DAL.Repositories.AddressRepository;
import com.example.bit.R;
import com.example.bit.View.IntentExtras.Constants;
import com.example.bit.View.RecycleViewAdapters.AddressAdapter;
import com.example.bit.View.ViewModels.AddressViewModel;
import com.example.bit.databinding.FragmentDepositAddressesBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class AddressFragment extends androidx.fragment.app.Fragment {

    private User mUser;
    private AddressRepository mAddressRepository;
    private AddressViewModel mAddressViewModel;
    private RecyclerView mAddressRecyclerView;
    private AddressAdapter mAddressAdapter;
    private RecyclerView.LayoutManager mAddresslayoutManager;
    private FragmentDepositAddressesBinding mbindingContext;

    public AddressFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = bindView(inflater, container, savedInstanceState);
        bindRepositories(container);
        recoverUserFromIntent();
        bindFragmentElements(container);
        return view;
    }

    private void bindRepositories(ViewGroup container) {
        mAddressRepository = new AddressRepository(getActivity().getApplication());
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

        List<Address> userAddress = getUserAddressesData();
        mAddressAdapter = new AddressAdapter(getActivity());
        mAddressRecyclerView.setAdapter(mAddressAdapter);
    }

    private void createViewModel() {
        mAddressViewModel = ViewModelProviders.of(getActivity()).get(AddressViewModel.class);

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
                    mAddressRepository.generateAddress(mUser.getId());
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

    private List<Address> getUserAddressesData() {
        try {
            List<Address> addresses = mAddressRepository.getAllByUser(mUser.getId());

            if(addresses.isEmpty()) {
                addresses = generateFirstUserAddress();
            }
            return addresses;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<Address> generateFirstUserAddress() throws Exception {
        List<Address> addresses;
        mAddressRepository.generateFirstAddress(mUser.getId());
        addresses = mAddressRepository.getAllByUser(mUser.getId());
        return addresses;
    }

    private void recoverUserFromIntent() {
        mUser = (User) getActivity().getIntent().getSerializableExtra(Constants.USER_INTENT);
    }

    private View bindView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mbindingContext = DataBindingUtil.inflate(inflater, R.layout.fragment_deposit_addresses, container, false);
        return mbindingContext.getRoot();
    }

}
