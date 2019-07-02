package com.app.bit.View.RecycleViewAdapters;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.bit.DAL.Entities.Deposit;
import com.app.bit.DAL.Repositories.AddressRepository;
import com.app.bit.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

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

        final String pattern = "dd/MM/yyyy HH:mm";
        DateFormat df = new SimpleDateFormat(pattern);
        Date dateReceived = mDeposits.get(position).getCreatedAt();
        holder.dateReceived.setText(df.format(dateReceived));

        DecimalFormat decimalFormat = new DecimalFormat("#.########");
        holder.amountReceived.setText(decimalFormat.format(mDeposits.get(position).getAmount()) + " BTC");

        holder.amountReceived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Application application = (Application) v.getContext().getApplicationContext();

                new MaterialAlertDialogBuilder(v.getContext(), R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered)
                        .setTitle("Retirada")
                        .setMessage("Transação " + (mDeposits.get(position).getConfirmations() > 6 ? "confirmada" : "pendente") + ". Hash da transação: " + mDeposits.get(position).getTxId())
                        .setPositiveButton("Ok", null)
                        .create()
                        .show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDeposits != null
                ? mDeposits.size()
                : 0;
    }

}
