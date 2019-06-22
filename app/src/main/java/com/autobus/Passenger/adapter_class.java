package com.autobus.Passenger;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


        holder.ticket_price.setText(String.valueOf(busDetail.getTicket_price()));
        holder.bus_company.setText(String.valueOf(busDetail.getbus_company()));
        holder.bus_route_from.setText(String.valueOf(busDetail.getBus_from()));
        holder.bus_route_to.setText(String.valueOf(busDetail.getBus_to()));
        holder.bus_day.setText(String.valueOf(busDetail.getDay()));


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mCtx, BusDetailsList.class);
                // passing data to the book activity
                intent.putExtra("Image", productList.get(position).getImage());
                intent.putExtra("RouteFrom", productList.get(position).getBus_from());
                intent.putExtra("RouteTo", productList.get(position).getBus_to());
                intent.putExtra("Company", productList.get(position).getbus_company());
                intent.putExtra("TotalSeats", productList.get(position).getbus_total_seats());
                intent.putExtra("AvailableSeats", productList.get(position).getbus_available_seats());
                intent.putExtra("LeavingTime", productList.get(position).getbus_leaving_time());
                intent.putExtra("ReachingTime", productList.get(position).getbus_reaching_time());
                intent.putExtra("DriverName", productList.get(position).getbus_driver_name());
                intent.putExtra("TicketCheckerName", productList.get(position).getbus_ticketchecker_name());
                intent.putExtra("BreakTime", productList.get(position).getbus_break_time());
                intent.putExtra("Number", productList.get(position).getbus_number());
                intent.putExtra("Day", productList.get(position).getDay());
                intent.putExtra("TicketPrice", productList.get(position).getTicket_price());

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


        TextView bus_company, bus_route_from, bus_route_to, bus_day, ticket_price;
        ImageView bus_img;
        CardView cardView;


        public bus_dataViewHolder(View itemView) {
            super(itemView);
            bus_img = itemView.findViewById(R.id.bus_image_card);
            ticket_price = itemView.findViewById(R.id.ticket_price);
            bus_route_from = itemView.findViewById(R.id.bus_route_from);
            bus_route_to = itemView.findViewById(R.id.bus_route_to);
            bus_company = itemView.findViewById(R.id.bus_company_card);
            bus_day = itemView.findViewById(R.id.bus_day_card);
            cardView = itemView.findViewById(R.id.bus_cardview_id);
        }
    }
}
