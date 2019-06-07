package com.example.bit.View.RecycleViewAdapters;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bit.DAL.Entities.Address;
import com.example.bit.DAL.Entities.Deposit;
import com.example.bit.DAL.Repositories.AddressRepository;
import com.example.bit.DAL.Repositories.DepositRepository;
import com.example.bit.R;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class DepositAdapter extends RecyclerView.Adapter<DepositAdapter.DepositViewHolder>  {

    private final LayoutInflater mInflater;
    private List<Deposit> mDeposits;
    private AddressRepository mAddressRepository;


    public static class DepositViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewTxId;
        public TextView textViewAddress;
        public TextView  textViewAmount;

        public DepositViewHolder(View v) {
            super(v);
            textViewTxId = v.findViewById(R.id.textViewTxId);
            textViewAddress = v.findViewById(R.id.textViewAddress);
            textViewAmount = v.findViewById(R.id.textViewAmount);
        }
    }

    public DepositAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public void setDeposits(List<Deposit> deposits){
        mDeposits = deposits;
        notifyDataSetChanged();
    }


    @Override
    public DepositAdapter.DepositViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.fragment_deposits_row, parent, false);
        mAddressRepository = new AddressRepository((Application)parent.getContext().getApplicationContext());
        return new DepositAdapter.DepositViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DepositViewHolder holder, final int position) {

        Address address = mAddressRepository.get(mDeposits.get(position).getAddressId());
        holder.textViewAddress.setText(address.getPublicAddress());
        holder.textViewAmount.setText(mDeposits.get(position).getAmount() + "BTC");
        holder.textViewTxId.setText(mDeposits.get(position).getTxId());

    }

    @Override
    public int getItemCount() {
        return mDeposits != null
                ? mDeposits.size()
                : 0;
    }

}
