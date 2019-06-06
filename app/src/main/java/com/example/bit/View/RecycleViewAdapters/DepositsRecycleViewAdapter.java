package com.example.bit.View.RecycleViewAdapters;

import android.app.Application;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bit.DAL.Entities.Address;
import com.example.bit.DAL.Entities.Deposit;
import com.example.bit.DAL.Repositories.AddressRepository;
import com.example.bit.DAL.Repositories.DepositRepository;
import com.example.bit.Helpers.StringHelpers;
import com.example.bit.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import net.glxn.qrgen.android.QRCode;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class DepositsRecycleViewAdapter extends RecyclerView.Adapter<DepositsRecycleViewAdapter.MyViewHolder>  {

    private List<Deposit> mDataset;
    private DepositRepository mDepositRepository;
    private AddressRepository mAddressRepository;


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewTxId;
        public TextView textViewAddress;
        public TextView  textViewAmount;


        public MyViewHolder(View v) {
            super(v);
            textViewTxId = v.findViewById(R.id.textViewTxId);
            textViewAddress = v.findViewById(R.id.textViewAddress);
            textViewAmount = v.findViewById(R.id.textViewAmount);
        }
    }

    public DepositsRecycleViewAdapter(List<Deposit> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public DepositsRecycleViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.deposits_row_fragment, parent, false);
        mDepositRepository = new DepositRepository((Application)parent.getContext().getApplicationContext());
        mAddressRepository = new AddressRepository((Application)parent.getContext().getApplicationContext());
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        Address address = mAddressRepository.get(mDataset.get(position).getAddressId());

        holder.textViewAddress.setText(address.getPublicAddress());
        holder.textViewAmount.setText(mDataset.get(position).getAmount() + "BTC");
        holder.textViewTxId.setText(mDataset.get(position).getTxId());

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}