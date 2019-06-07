package com.example.bit.View.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bit.DAL.Entities.Deposit;
import com.example.bit.DAL.Entities.User;
import com.example.bit.R;
import com.example.bit.View.IntentExtras.Constants;
import com.example.bit.View.RecycleViewAdapters.DepositAdapter;
import com.example.bit.View.ViewModels.DepositViewModel;
import com.example.bit.databinding.FragmentDepositBinding;

import java.util.List;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class DepositFragment extends androidx.fragment.app.Fragment {

    private User mUser;
    private DepositViewModel mDepositViewModel;
    private RecyclerView mDepositRecyclerView;
    private DepositAdapter mDepositAdapter;
    private RecyclerView.LayoutManager mDepositlayoutManager;
    private FragmentDepositBinding mbindingContext;

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
    }

    private void createRecycleView(ViewGroup container) {

        mDepositRecyclerView = mbindingContext.depositsRecycleView;
        mDepositRecyclerView.setHasFixedSize(true);
        mDepositlayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        mDepositRecyclerView.setLayoutManager(mDepositlayoutManager);

        mDepositAdapter = new DepositAdapter(getActivity());
        mDepositRecyclerView.setAdapter(mDepositAdapter);
    }

    private void createViewModel() {
        mDepositViewModel = ViewModelProviders.of(getActivity()).get(DepositViewModel.class);

        mDepositViewModel.getAllLiveDataByUser(mUser.getId()).observe(this, new Observer<List<Deposit>>() {
            @Override
            public void onChanged(List<Deposit> deposits) {
                mDepositAdapter.setDeposits(deposits);
            }
        });
    }

    private void recoverUserFromIntent() {
        mUser = (User) getActivity().getIntent().getSerializableExtra(Constants.USER_INTENT);
    }

    private View bindView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mbindingContext = DataBindingUtil.inflate(inflater, R.layout.fragment_deposit, container, false);
        return mbindingContext.getRoot();
    }

}
