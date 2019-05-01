package com.autobus.Passenger;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.autobus.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class adapter_class extends RecyclerView.Adapter<adapter_class.bus_dataViewHolder> {


    private Context mCtx;
    private List<bus_data> productList;

    public adapter_class(Context mCtx, List<bus_data> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public bus_dataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.bus_detail_list, null);
        return new bus_dataViewHolder(view);
    }


    @Override
    public void onBindViewHolder(bus_dataViewHolder holder, int position) {
        bus_data busDetail = productList.get(position);

        //loading the image

        Glide.with(mCtx)
                .load(busDetail.getImage())
                .into(holder.imageView);

        holder.bus_number.setText(busDetail.getbus_number());
        holder.total_seats.setText(busDetail.getbus_total_seats());
        holder.available_seats.setText(String.valueOf(busDetail.getbus_available_seats()));
        holder.bus_route.setText(String.valueOf(busDetail.getbus_route()));
        holder.bus_leaving_time.setText(String.valueOf(busDetail.getbus_leaving_time()));
        holder.bus_reaching_time.setText(String.valueOf(busDetail.getbus_reaching_time()));
        holder.bus_driver_name.setText(String.valueOf(busDetail.getbus_driver_name()));
        holder.bus_ticketchecker_name.setText(String.valueOf(busDetail.getbus_ticketchecker_name()));
        holder.bus_rating.setText(String.valueOf(busDetail.getbus_rating()));
        holder.bus_break_time.setText(String.valueOf(busDetail.getbus_break_time()));
        holder.bus_company.setText(String.valueOf(busDetail.getbus_company()));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class bus_dataViewHolder extends RecyclerView.ViewHolder {

       TextView bus_number, total_seats, available_seats, bus_route, bus_leaving_time, bus_reaching_time,
                bus_driver_name, bus_ticketchecker_name, bus_rating, bus_break_time, bus_company;
       ImageView imageView;


        public bus_dataViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            bus_number = itemView.findViewById(R.id.bus_number);
            total_seats = itemView.findViewById(R.id.total_seats);
            available_seats = itemView.findViewById(R.id.available_seats);
            bus_route = itemView.findViewById(R.id.route);
            bus_leaving_time = itemView.findViewById(R.id.leaving_time);
            bus_reaching_time = itemView.findViewById(R.id.reaching_time);
            bus_driver_name = itemView.findViewById(R.id.driver_name);
            bus_ticketchecker_name = itemView.findViewById(R.id.tk_checker_name);
            bus_rating = itemView.findViewById(R.id.rating);
            bus_break_time = itemView.findViewById(R.id.break_time);
            bus_company = itemView.findViewById(R.id.bus_company);
        }
    }
}
