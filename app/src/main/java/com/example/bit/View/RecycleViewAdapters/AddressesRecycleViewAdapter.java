package com.example.bit.View.RecycleViewAdapters;

import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bit.DAL.Entities.Address;
import com.example.bit.DAL.Repositories.AddressRepository;
import com.example.bit.Helpers.StringHelpers;
import com.example.bit.R;
import com.example.bit.View.DepositActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import net.glxn.qrgen.android.QRCode;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class  AddressesRecycleViewAdapter extends RecyclerView.Adapter<AddressesRecycleViewAdapter.MyViewHolder>  {

    private List<Address> mDataset;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;
        public TextView textViewLabel;
        public ImageView imageView;

        public MyViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.addressPublicKey);
            imageView = v.findViewById(R.id.addressQrCode);
            textViewLabel = v.findViewById(R.id.addressLabel);
        }
    }

    public AddressesRecycleViewAdapter(List<Address> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public AddressesRecycleViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.addresslist_row_fragment, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.textView.setText(mDataset.get(position).getPublicKey());
        Bitmap myBitmap = QRCode.from(mDataset.get(position).getPublicKey()).bitmap();
        holder.imageView.setImageBitmap(myBitmap);

        final int addressId = mDataset.get(position).getId();

        final boolean addressHasLabel = !StringHelpers.isNullEmptyOrWhitespace(mDataset.get(position).getLabel());

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = (addressHasLabel
                        ? "Altere"
                        : "Adicione") + " um identificador para o seu endereço:";

                final EditText edittext = new EditText(v.getContext());

                final Application application = (Application) v.getContext().getApplicationContext();

                new MaterialAlertDialogBuilder(v.getContext(), R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered)
                        .setTitle(title)
                        .setView(edittext)
                        .setPositiveButton(addressHasLabel ? "Adicionar" : "Alterar" + " identificador", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                final String label = !StringHelpers.isNullEmptyOrWhitespace(edittext.getText().toString())
                                        ? edittext.getText().toString()
                                        : null;

                                new AddressRepository(application).addLabelToAddress(addressId, label);
                            }
                        })
                        .setNegativeButton("Cancelar", null)
                        .create()
                        .show();
            }
        });

        if(addressHasLabel) {
            holder.textViewLabel.setText(mDataset.get(position).getLabel());
        }
        else {
            holder.textViewLabel.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
