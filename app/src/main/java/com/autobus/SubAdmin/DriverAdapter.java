package com.autobus.SubAdmin;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.autobus.R;

import java.util.List;

public class DriverAdapter extends RecyclerView.Adapter<DriverAdapter.myViewHolder> {

    Context ctx;
    List<DriverModel> mData;
    Dialog mDialog;
    DriverAdapter recyclerViewAdapter;
    String DriverName, DriverPassword, credentials;

    public DriverAdapter(Context ctx, List<DriverModel> mData) {
        this.ctx = ctx;
        this.mData = mData;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.driver_detail_design, null);
        final myViewHolder vHolder = new myViewHolder(view);
       // recyclerViewAdapter.notifyDataSetChanged();

        mDialog = new Dialog(ctx);
        mDialog.setContentView(R.layout.driver_detail_dialog);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        vHolder.item_list.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                DriverName = mData.get(vHolder.getAdapterPosition()).getDriverName();
                DriverPassword = mData.get(vHolder.getAdapterPosition()).getDriverPassword();
                credentials = "Name:" + DriverName + "\n" + "Password:" + DriverPassword;
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Share User Name & Password");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, credentials);
                ctx.startActivity(Intent.createChooser(sharingIntent, ctx.getResources().getString(R.string.share_using)));
                return true;
            }
        });
        vHolder.item_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView dialog_tv_name = mDialog.findViewById(R.id.dialog_name);
                TextView dialog_tv_phone = mDialog.findViewById(R.id.dialog_phone);
                TextView dialog_tv_id = mDialog.findViewById(R.id.dialog_id);
                TextView dialog_tv_password = mDialog.findViewById(R.id.dialog_password);
                TextView dialog_tv_busNumber = mDialog.findViewById(R.id.dialog_company);

                dialog_tv_name.setText(mData.get(vHolder.getAdapterPosition()).getDriverName());
                dialog_tv_phone.setText(mData.get(vHolder.getAdapterPosition()).getDriverPhone());
                dialog_tv_id.setText(mData.get(vHolder.getAdapterPosition()).getDriverID());
                dialog_tv_password.setText(mData.get(vHolder.getAdapterPosition()).getDriverPassword());
                dialog_tv_busNumber.setText(mData.get(vHolder.getAdapterPosition()).getBusNumber());

                mDialog.show();

            }
        });
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder myViewHolder, int i) {

        myViewHolder.tv_name.setText(mData.get(i).getDriverName());
        myViewHolder.tv_phone.setText(mData.get(i).getDriverPhone());


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_name, tv_phone;
        public LinearLayout item_list;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            item_list = itemView.findViewById(R.id.detail_list_layout);
            tv_name = itemView.findViewById(R.id.drivername);
            tv_phone = itemView.findViewById(R.id.number);

        }
    }
}
