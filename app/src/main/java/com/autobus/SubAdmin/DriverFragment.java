package com.autobus.SubAdmin;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.autobus.Passenger.Bus_Details;
import com.autobus.Passenger.adapter_class;
import com.autobus.Passenger.bus_data;
import com.autobus.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DriverFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    View v;
    private RecyclerView mRecyclerView;
    DriverAdapter recyclerViewAdapter;
    Context ctx;
    SwipeRefreshLayout refreshLayout;
    private static final String URL_PRODUCTS = "http://192.168.10.11/AutoBus/show_driver.php";
    List<DriverModel> mData;

    public DriverFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        v = inflater.inflate(R.layout.driver_frgment, container, false);

        /*refreshLayout = v.findViewById(R.id.refresh);
        refreshLayout.setOnRefreshListener(this);*/

        mRecyclerView = (RecyclerView) v.findViewById(R.id.mon_data);
        DriverAdapter recyclerViewAdapter = new DriverAdapter(getContext(), mData);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(recyclerViewAdapter);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mData = new ArrayList<>();

        /*mData.add(new DriverModel("Asad","092 4587455","Asss","ssss","dddddd"));
        mData.add(new DriverModel("Asad","092 4587455","Asss","ssss","dddddd"));
        mData.add(new DriverModel("Asad","092 4587455","Asss","ssss","dddddd"));
        mData.add(new DriverModel("Asad","092 4587455","Asss","ssss","dddddd"));
*/

        loadDetails();
    }

    private void loadDetails() {


        /*
         * Creating a String Request
         * The request type is GET defined by first parameter
         * The URL is defined in the second parameter
         * Then we have a Response Listener and a Error Listener
         * In response listener we will get the JSON response as a String
         * */
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_PRODUCTS,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject data = array.getJSONObject(i);

                                //adding the product to product list
                                mData.add(new DriverModel(
                                        data.getString("driver_uname"),
                                        data.getString("driver_password"),
                                        data.getString("driver_id"),
                                        data.getString("driver_phone"),
                                        data.getString("bus_number")
                                ));
                                //Toast.makeText(getActivity(), ""+data, Toast.LENGTH_LONG).show();
                            }
                            //after set data on list,call here notifiyDataSetChanged() method


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "Error" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Error" + error.toString(), Toast.LENGTH_SHORT).show();


                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(Objects.requireNonNull(getActivity())).add(stringRequest);
    }

    @Override
    public void onRefresh() {
        //recyclerViewAdapter.notifyDataSetChanged();

    }



}