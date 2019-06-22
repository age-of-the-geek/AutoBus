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

public class TicketCheckerAdapter extends RecyclerView.Adapter<TicketCheckerAdapter.myViewHolder> {

    Context ctx;
    List<TicketCheckerModel> mData;
    Dialog mDialog;
    String TkCheckerName,TkCheckerPassword, TCredentials;

    public TicketCheckerAdapter(Context ctx, List<TicketCheckerModel> mData) {
        this.ctx = ctx;
        this.mData = mData;
    }

    @NonNull
    @Override
    public TicketCheckerAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.ticket_checker_detail_design, null);
        final TicketCheckerAdapter.myViewHolder vHolder = new myViewHolder(view);
        mDialog = new Dialog(ctx);
        mDialog.setContentView(R.layout.ticket_checker_detail_dialog);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        vHolder.item_list.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                TkCheckerName= mData.get(vHolder.getAdapterPosition()).getTkCheckerName();
                TkCheckerPassword= mData.get(vHolder.getAdapterPosition()).getTkCheckerPassword();
                TCredentials = "Name:"+TkCheckerName+ "\n" + "Password:" + TkCheckerPassword;
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Share User Name & Password");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, TCredentials);
                ctx.startActivity(Intent.createChooser(sharingIntent, ctx.getResources().getString(R.string.share_using)));
                return true;
            }
        });
        vHolder.item_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView dialog_tv_name = mDialog.findViewById(R.id.t_dialog_name);
                TextView dialog_tv_phone = mDialog.findViewById(R.id.t_dialog_phone);
                TextView dialog_tv_id = mDialog.findViewById(R.id.t_dialog_id);
                TextView dialog_tv_password = mDialog.findViewById(R.id.t_dialog_password);
                TextView dialog_tv_busNumber = mDialog.findViewById(R.id.t_dialog_company);

                dialog_tv_name.setText(mData.get(vHolder.getAdapterPosition()).getTkCheckerName());
                dialog_tv_phone.setText(mData.get(vHolder.getAdapterPosition()).getTkCheckerPhone());
                dialog_tv_id.setText(mData.get(vHolder.getAdapterPosition()).getTkCheckerID());
                dialog_tv_password.setText(mData.get(vHolder.getAdapterPosition()).getTkCheckerPassword());
                dialog_tv_busNumber.setText(mData.get(vHolder.getAdapterPosition()).getBusNumber());

                mDialog.show();

            }
        });
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TicketCheckerAdapter.myViewHolder myViewHolder, int i) {

        myViewHolder.tv_name.setText(mData.get(i).getTkCheckerName());
        myViewHolder.tv_phone.setText(mData.get(i).getTkCheckerPhone());


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_name, tv_phone;
        private LinearLayout item_list;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            item_list = itemView.findViewById(R.id.tkChecker_detail_list_layout);
            tv_name = itemView.findViewById(R.id.tkCheckername);
            tv_phone = itemView.findViewById(R.id.tkCheckernumber);

        }
    }
}
