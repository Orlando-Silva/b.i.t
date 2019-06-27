package com.example.bit.View.RecycleViewAdapters;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bit.DAL.Entities.Withdraw;
import com.example.bit.DAL.Repositories.AddressRepository;
import com.example.bit.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class WithdrawAdapter extends RecyclerView.Adapter<WithdrawAdapter.WithdrawViewHolder>  {

    private final LayoutInflater mInflater;
    private List<Withdraw> mWithdraws;
    private AddressRepository mAddressRepository;


    public static class WithdrawViewHolder extends RecyclerView.ViewHolder {

        public Chip dateReceived;
        public Chip  amountReceived;

        public WithdrawViewHolder(View v) {
            super(v);
            dateReceived = v.findViewById(R.id.dateReceived);
            amountReceived = v.findViewById(R.id.amountReceived);
        }
    }

    public WithdrawAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public void setWithdraws(List<Withdraw> withdraws){
        mWithdraws = withdraws;
        notifyDataSetChanged();
    }


    @Override
    public WithdrawAdapter.WithdrawViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.fragment_withdraw_row, parent, false);
        mAddressRepository = new AddressRepository((Application)parent.getContext().getApplicationContext());
        return new WithdrawAdapter.WithdrawViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WithdrawAdapter.WithdrawViewHolder holder, final int position) {

        final String pattern = "dd/MM/yyyy HH:mm";
        DateFormat df = new SimpleDateFormat(pattern);
        Date dateReceived = mWithdraws.get(position).getCreatedAt();
        holder.dateReceived.setText(df.format(dateReceived));

        DecimalFormat decimalFormat = new DecimalFormat("#.########");
        holder.amountReceived.setText(decimalFormat.format(mWithdraws.get(position).getAmount()) + " BTC");

        holder.amountReceived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Application application = (Application) v.getContext().getApplicationContext();

                new MaterialAlertDialogBuilder(v.getContext(), R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered)
                        .setTitle("Retirada")
                        .setMessage("Transação " + (mWithdraws.get(position).getConfirmations() > 6 ? "confirmada" : "pendente") + ". Hash da transação: " + mWithdraws.get(position).getTxId())
                        .setPositiveButton("Ok", null)
                        .create()
                        .show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mWithdraws != null
                ? mWithdraws.size()
                : 0;
    }

}