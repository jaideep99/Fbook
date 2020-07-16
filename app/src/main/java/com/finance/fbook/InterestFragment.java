package com.finance.fbook;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class InterestFragment extends Fragment {

    Context context;

    public InterestFragment(Context cont) {
        this.context = cont;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root =  inflater.inflate(R.layout.fragment_interest, container, false);

        return root;
    }

}
