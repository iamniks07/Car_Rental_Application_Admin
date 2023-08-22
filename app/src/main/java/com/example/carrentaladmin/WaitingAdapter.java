package com.example.carrentaladmin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class WaitingAdapter extends FirebaseRecyclerAdapter<Booking2,WaitingAdapter.myviewholder>
{

    public WaitingAdapter(@NonNull FirebaseRecyclerOptions<Booking2> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull Booking2 model) {
        holder.vehicleName.setText(model.getVehicleName());
        holder.bookingID.setText(model.getBookingID()+"");
        holder.customerName.setText(model.getCustomerName());
        holder.pickupDate.setText(model.getPickupTime());
        holder.returnDate.setText(model.getReturnTime());
        holder.approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map map = new HashMap();
                map.put("bookingStatus","Approved");

                FirebaseDatabase.getInstance().getReference().child("Booking")
                        .child(getRef(holder.getAdapterPosition()).getKey()).updateChildren(map)
                        .addOnSuccessListener(new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                
                            }
                        });
            }
        });

        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map map = new HashMap();
                map.put("bookingStatus","Rejected");

                FirebaseDatabase.getInstance().getReference().child("Booking")
                        .child(getRef(holder.getAdapterPosition()).getKey()).updateChildren(map)
                        .addOnSuccessListener(new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
            }
        });
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_card,parent,false);
        return new myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder
    {
        TextView vehicleName, bookingID, customerName,
                pickupDate, returnDate, bookingStatus;
        Button approve, reject;
        public myviewholder(@NonNull View itemView) {
            super(itemView);

            bookingID = itemView.findViewById(R.id.bookingId);
            pickupDate = itemView.findViewById(R.id.pickDate);
            returnDate = itemView.findViewById(R.id.retDate);
            vehicleName = itemView.findViewById(R.id.vehName);
            customerName = itemView.findViewById(R.id.customerName);
            approve = itemView.findViewById(R.id.approve);
            reject = itemView.findViewById(R.id.reject);
            bookingStatus = itemView.findViewById(R.id.bookingStatus);
        }
    }
}
