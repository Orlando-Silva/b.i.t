package com.app.bit.View.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.bit.DAL.Entities.User;
import com.app.bit.DAL.Entities.Withdraw;
import com.app.bit.R;
import com.app.bit.View.IntentExtras.Constants;
import com.app.bit.View.RecycleViewAdapters.WithdrawAdapter;
import com.app.bit.View.ViewModels.WithdrawViewModel;
import com.app.bit.databinding.FragmentWithdrawListBinding;

import java.util.List;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class WithdrawListFragment extends androidx.fragment.app.Fragment {

    private User mUser;
    private WithdrawViewModel mWithdrawViewModel;
    private RecyclerView mWithdrawRecyclerView;
    private WithdrawAdapter mWithdrawAdapter;
    private RecyclerView.LayoutManager mWithdrawlayoutManager;
    private FragmentWithdrawListBinding mbindingContext;

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

        mWithdrawRecyclerView = mbindingContext.withdraawRecycleView;
        mWithdrawRecyclerView.setHasFixedSize(true);
        mWithdrawlayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        mWithdrawRecyclerView.setLayoutManager(mWithdrawlayoutManager);

        mWithdrawAdapter = new WithdrawAdapter(getActivity());
        mWithdrawRecyclerView.setAdapter(mWithdrawAdapter);
    }

    private void createViewModel() {
        mWithdrawViewModel = ViewModelProviders.of(getActivity()).get(WithdrawViewModel.class);

        mWithdrawViewModel.getAllLiveDataByUser(mUser.getId()).observe(this, new Observer<List<Withdraw>>() {
            @Override
            public void onChanged(List<Withdraw> withdraws) {
                mWithdrawAdapter.setWithdraws(withdraws);
            }
        });
    }

    private void recoverUserFromIntent() {
        mUser = (User) getActivity().getIntent().getSerializableExtra(Constants.USER_INTENT);
    }

    private View bindView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mbindingContext = DataBindingUtil.inflate(inflater, R.layout.fragment_withdraw_list, container, false);
        return mbindingContext.getRoot();
    }
}