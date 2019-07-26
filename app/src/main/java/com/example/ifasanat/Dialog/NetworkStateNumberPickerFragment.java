package com.example.ifasanat.Dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ifasanat.R;


public class NetworkStateNumberPickerFragment extends DialogFragment {

    private OnCompleteListenerNetworkState mListener;


    public  interface OnCompleteListenerNetworkState {
        void onCompleteNetworkState(String time);
    }

    public NetworkStateNumberPickerFragment() {
        // Required empty public constructor
    }


    NumberPicker numberPickerNetworkState;
    TextView textViewNumberPicker;
    String networkState;
    Button buttonSetNetworkState;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_network_state_number_picker, container, false);
        Typeface typeface=Typeface.createFromAsset(getActivity().getAssets(),"iransanslight.ttf");
        numberPicker(view);
        setFont(typeface);

        buttonSetNetworkState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (networkState == null ){
                    setTime("0");
                    dismiss();
                }else {
                    setTime(networkState);
                    dismiss();
                }

            }
        });

        return view;
    }

    private void numberPicker(View view){
        numberPickerNetworkState = view.findViewById(R.id.numberPickerNetworkState);

        textViewNumberPicker =view. findViewById(R.id.text_view_number_picker_network_state);


        buttonSetNetworkState = view.findViewById(R.id.button_set_network_state);

        numberPickerNetworkState.setMinValue(0);
        numberPickerNetworkState.setMaxValue(100);


        numberPickerNetworkState.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if(newVal != oldVal){
                    networkState = String.valueOf(newVal);
                }else {
                    networkState = String.valueOf(oldVal);
                }
            }
        });



    }

    private void setFont(Typeface typeface){

        textViewNumberPicker.setTypeface(typeface);

        buttonSetNetworkState.setTypeface(typeface);

    }

    private void  setTime(String networkState){
        mListener.onCompleteNetworkState(networkState + "%") ;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed (String uri) {
        if (mListener != null) {
            mListener.onCompleteNetworkState(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.mListener = (NetworkStateNumberPickerFragment.OnCompleteListenerNetworkState)activity;
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnCompleteListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
