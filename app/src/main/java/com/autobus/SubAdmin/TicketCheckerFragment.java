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
import com.autobus.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TicketCheckerFragment extends Fragment {
    View v;
    private RecyclerView mRecyclerView;
    private static final String URL_PRODUCTS = "http://192.168.43.197/AutoBus/show_ticket_checker.php";
    List<TicketCheckerModel> mData;

    public TicketCheckerFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        v = inflater.inflate(R.layout.ticket_checker_fragment, container, false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.ticket_checker_data);
        TicketCheckerAdapter recyclerViewAdapter = new TicketCheckerAdapter(getContext(), mData);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(recyclerViewAdapter);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mData = new ArrayList<>();
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
                                mData.add(new TicketCheckerModel(
                                        data.getString("tk_checker_uname"),
                                        data.getString("tk_checker_password"),
                                        data.getString("tk_checker_id"),
                                        data.getString("tk_checker_phone"),
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
}
