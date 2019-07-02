package com.app.bit.View.RecycleViewAdapters;

import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.bit.DAL.Entities.Address;
import com.app.bit.DAL.Repositories.AddressRepository;
import com.app.bit.Helpers.StringHelpers;
import com.app.bit.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import net.glxn.qrgen.android.QRCode;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder>  {

    private final LayoutInflater mInflater;
    private List<Address> mAddress;

    public static class AddressViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;
        public TextView textViewLabel;
        public ImageView imageView;

        public AddressViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.addressPublicKey);
            imageView = v.findViewById(R.id.addressQrCode);
            textViewLabel = v.findViewById(R.id.addressLabel);
        }
    }

    public AddressAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public void setAddress(List<Address> address){
        mAddress = address;
        notifyDataSetChanged();
    }

    @Override
    public AddressAdapter.AddressViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.fragment_address_row, parent, false);
        return new AddressViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AddressViewHolder holder, final int position) {

        final Address currentAddress = mAddress.get(position);
        final boolean addressHasLabel = !StringHelpers.isNullEmptyOrWhitespace(currentAddress.getLabel());
        
        holder.textView.setText(currentAddress.getPublicAddress());
        holder.imageView.setOnClickListener(createEditLabelEvent(addressHasLabel, currentAddress));
        generateQrCode(holder, position);

        SetLabelVisibility(holder, currentAddress, addressHasLabel);
    }

    private void SetLabelVisibility(AddressViewHolder holder, Address currentAddress, boolean addressHasLabel) {
        if(addressHasLabel) {
            holder.textViewLabel.setText(currentAddress.getLabel());
        }
        else {
            holder.textViewLabel.setVisibility(View.GONE);
        }
    }


    private void generateQrCode(AddressViewHolder holder, int position) {
        Bitmap myBitmap = QRCode.from(mAddress.get(position).getPublicAddress()).bitmap();
        holder.imageView.setImageBitmap(myBitmap);
    }


    private View.OnClickListener createEditLabelEvent(final boolean addressHasLabel, final Address currentAddress) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = generateDialogTitle(addressHasLabel);
                final EditText edittext = new EditText(v.getContext());
                edittext.setText(currentAddress.getLabel());

                final Application application = (Application) v.getContext().getApplicationContext();

                new MaterialAlertDialogBuilder(v.getContext(), R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered)
                        .setTitle(title)
                        .setView(edittext)
                        .setPositiveButton(generateDialogPositiveButtonText(addressHasLabel), saveLabelChangeEvent(edittext, application, currentAddress))
                        .setNegativeButton("Cancelar", null)
                        .create()
                        .show();
            }
        };
    }

    private String generateDialogPositiveButtonText(boolean addressHasLabel) {
        return addressHasLabel ? "Alterar" : "Adicionar";
    }

    private String generateDialogTitle(boolean addressHasLabel) {
        return addressHasLabel
                ? "Altere o identificador do seu endereço"
                : "Adicione um identificador para o seu endereço:";
    }

    private DialogInterface.OnClickListener saveLabelChangeEvent(final EditText edittext,final Application application, final Address address) {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                final String label = getEditedLabelValue(edittext);
                new AddressRepository(application).addLabelToAddress(address.getId(), label);
                mAddress.get(mAddress.indexOf(address)).setLabel(label);
                notifyItemChanged(mAddress.indexOf(address));
            }
        };
    }

    private String getEditedLabelValue(EditText edittext) {
        return !StringHelpers.isNullEmptyOrWhitespace(edittext.getText().toString())
                ? edittext.getText().toString()
                : null;
    }

    @Override
    public int getItemCount() {
        return mAddress != null 
                ? mAddress.size()
                : 0;
    }

}
