package com.financebazaar.gigs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class history_fragment extends Fragment {


    RecyclerView recyclerView;
    TextView tx;
    String number, token;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history_fragment, container, false);
        tx = view.findViewById(R.id.result);
        recyclerView = view.findViewById(R.id.history_rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        number = String.valueOf(com.financebazaar.gigs.userHelperClass.get(view.getContext(), "number", "number"));
        token = String.valueOf(userHelperClass.get(view.getContext(), "token", "token"));
        getSuccess();
        return view;
    }

    private void getSuccess(){

        Call<Status> gigsCall = Retrofit.getServices().getSuccessItem(number, token);
        gigsCall.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {

                List<SuccessItem> gigs = response.body().getSuccess();
                if(gigs.size() == 0){
                    tx.setText("Nothing to show");
                }else{
                    tx.setVisibility(View.GONE);
                }
                recyclerView.setAdapter(new successAdapter(gigs));
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {

                Toast.makeText(getActivity(), "connection failed.." , Toast.LENGTH_LONG).show();
            }
        });
    }
}