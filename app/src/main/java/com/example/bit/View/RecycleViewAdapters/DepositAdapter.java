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
import com.google.android.material.chip.Chip;

import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class DepositAdapter extends RecyclerView.Adapter<DepositAdapter.DepositViewHolder>  {

    private final LayoutInflater mInflater;
    private List<Deposit> mDeposits;
    private AddressRepository mAddressRepository;


    public static class DepositViewHolder extends RecyclerView.ViewHolder {

        public Chip dateReceived;
        public Chip  amountReceived;

        public DepositViewHolder(View v) {
            super(v);
            dateReceived = v.findViewById(R.id.dateReceived);
            amountReceived = v.findViewById(R.id.amountReceived);
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

        final String pattern = "MM/dd/yyyy HH:mm:ss";
        DateFormat df = new SimpleDateFormat(pattern);
        Date dateReceived = mDeposits.get(position).getCreatedAt();
        holder.dateReceived.setText(df.format(dateReceived));

        DecimalFormat decimalFormat = new DecimalFormat("#.########");
        decimalFormat.setRoundingMode(RoundingMode.UNNECESSARY);
        holder.amountReceived.setText(decimalFormat.format(mDeposits.get(position).getAmount()) + " BTC");

    }

    @Override
    public int getItemCount() {
        return mDeposits != null
                ? mDeposits.size()
                : 0;
    }

}
