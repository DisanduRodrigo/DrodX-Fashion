package com.example.drodx.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drodx.R;
import com.example.drodx.models.AddressModel;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {

   Context context;
   List <AddressModel>addressModelList;
   SelectedAddress selectedAddress;

   private RadioButton selectedRadioBtn;

    public AddressAdapter(Context context, List<AddressModel> addressModelList, SelectedAddress selectedAddress) {
        this.context = context;
        this.addressModelList = addressModelList;
        this.selectedAddress = selectedAddress;
    }

    @NonNull
    @Override
    public AddressAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.address_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AddressAdapter.ViewHolder holder, int position) {

        holder.addressName.setText(addressModelList.get(position).getUserAddressName());
        holder.addressLane.setText(addressModelList.get(position).getUserAddressLane());
        holder.addressCity.setText(addressModelList.get(position).getUserAddressCity());
        holder.addressPostalCode.setText(addressModelList.get(position).getUserAddressPostalCode());
        holder.addressNumber.setText(addressModelList.get(position).getUserAddressNumber());
        holder.radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(AddressModel address :addressModelList){
                    address.setSelected(false);
                }
                addressModelList.get(position).setSelected(true);

                if(selectedRadioBtn!=null){
                    selectedRadioBtn.setChecked(false);
                }
                selectedRadioBtn = (RadioButton) view;
                selectedRadioBtn.setChecked(true);
                selectedAddress.setAddress(addressModelList.get(position).getUserAddressName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return addressModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView addressName,addressLane,addressCity,addressPostalCode,addressNumber;
        RadioButton radioButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            addressName = itemView.findViewById(R.id.address_add_name);
            addressLane = itemView.findViewById(R.id.address_add_lane);
            addressCity = itemView.findViewById(R.id.address_add_city);
            addressPostalCode = itemView.findViewById(R.id.address_add_postalcode);
            addressNumber = itemView.findViewById(R.id.address_add_number);
            radioButton = itemView.findViewById(R.id.select_address);

        }
    }

    public  interface SelectedAddress{
        void setAddress(String address);
    }


}
