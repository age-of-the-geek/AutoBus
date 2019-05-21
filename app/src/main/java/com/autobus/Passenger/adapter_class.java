package com.autobus.Passenger;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
        View view = inflater.inflate(R.layout.bus_detail_cardview, null);
        return new bus_dataViewHolder(view);
    }


    @Override
    public void onBindViewHolder(bus_dataViewHolder holder, int position) {
        bus_data busDetail = productList.get(position);

        //loading the image

        Glide.with(mCtx)
                .load(busDetail.getImage())
                .into(holder.bus_img);

        /*holder.bus_number.setText(busDetail.getbus_number());
        holder.total_seats.setText(busDetail.getbus_total_seats());
        holder.available_seats.setText(String.valueOf(busDetail.getbus_available_seats()));
        holder.bus_leaving_time.setText(String.valueOf(busDetail.getbus_leaving_time()));
        holder.bus_reaching_time.setText(String.valueOf(busDetail.getbus_reaching_time()));
        holder.bus_driver_name.setText(String.valueOf(busDetail.getbus_driver_name()));
        holder.bus_ticketchecker_name.setText(String.valueOf(busDetail.getbus_ticketchecker_name()));
        holder.bus_rating.setText(String.valueOf(busDetail.getbus_rating()));
        holder.bus_break_time.setText(String.valueOf(busDetail.getbus_break_time()));*/
        holder.bus_company.setText(String.valueOf(busDetail.getbus_company()));
        holder.bus_route.setText(String.valueOf(busDetail.getbus_route()));
        holder.bus_day.setText(String.valueOf(busDetail.getDay()));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mCtx,BusDetailsList.class);
                // passing data to the book activity
                intent.putExtra("Image",productList.get(position).getImage());
                intent.putExtra("Route",productList.get(position).getbus_route());
                intent.putExtra("Company",productList.get(position).getbus_company());
                intent.putExtra("TotalSeats",productList.get(position).getbus_total_seats());
                intent.putExtra("AvailableSeats",productList.get(position).getbus_available_seats());
                intent.putExtra("LeavingTime",productList.get(position).getbus_leaving_time());
                intent.putExtra("ReachingTime",productList.get(position).getbus_reaching_time());
                intent.putExtra("DriverName",productList.get(position).getbus_driver_name());
                intent.putExtra("TicketCheckerName",productList.get(position).getbus_ticketchecker_name());
                intent.putExtra("Rating",productList.get(position).getbus_rating());
                intent.putExtra("BreakTime",productList.get(position).getbus_break_time());
                intent.putExtra("Number",productList.get(position).getbus_number());
                intent.putExtra("Day",productList.get(position).getDay());

                // start the activity
                mCtx.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class bus_dataViewHolder extends RecyclerView.ViewHolder {

        /*TextView bus_number, total_seats, available_seats, bus_route, bus_leaving_time, bus_reaching_time,
                bus_driver_name, bus_ticketchecker_name, bus_rating, bus_break_time, bus_company;
        ImageView imageView;*/
        TextView bus_company, bus_route, bus_day;
        ImageView bus_img;
        CardView cardView ;


        public bus_dataViewHolder(View itemView) {
            super(itemView);
            bus_img = itemView.findViewById(R.id.bus_image_card);
           /* bus_number = itemView.findViewById(R.id.bus_number);
            total_seats = itemView.findViewById(R.id.total_seats);
            available_seats = itemView.findViewById(R.id.available_seats);*/
            bus_route = itemView.findViewById(R.id.bus_route_card);
            /*bus_leaving_time = itemView.findViewById(R.id.leaving_time);
            bus_reaching_time = itemView.findViewById(R.id.reaching_time);
            bus_driver_name = itemView.findViewById(R.id.driver_name);
            bus_ticketchecker_name = itemView.findViewById(R.id.tk_checker_name);
            bus_rating = itemView.findViewById(R.id.rating);
            bus_break_time = itemView.findViewById(R.id.break_time);*/
            bus_company = itemView.findViewById(R.id.bus_company_card);
            bus_day = itemView.findViewById(R.id.bus_day_card);
            cardView = (CardView) itemView.findViewById(R.id.bus_cardview_id);
        }
    }
}
