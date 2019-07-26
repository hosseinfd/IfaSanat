package com.example.ifasanat.Dialog;


import android.app.Activity;
import android.graphics.Typeface;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class NumberPickerFragment extends DialogFragment {

    NumberPicker numberPickerDay, numberPickerMonth, numberPickerYear;
    TextView textNumberPickerDay, textNumberPickerMonth, textNumberPickerYear;
    Button buttonSetDate;

    public NumberPickerFragment() {
        // Required empty public constructor
    }

    public interface OnCompleteListener {
        void onComplete(String time);
    }

    private OnCompleteListener mListener;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.mListener = (OnCompleteListener) activity;
        } catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnCompleteListener");
        }
    }


    String day = "1";
    String month = "1";
    String year = "1398";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.layout_date_picker, container, false);


        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "iransanslight.ttf");
        numberPicker(view);
        setFont(typeface);
        buttonSetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTime();
                dismiss();

            }
        });


        return view;
    }

    private void numberPicker(View view) {
        numberPickerDay = view.findViewById(R.id.numberPickerDay);
        numberPickerMonth = view.findViewById(R.id.numberPickerMonth);
        numberPickerYear = view.findViewById(R.id.numberPickerYear);

        textNumberPickerDay = view.findViewById(R.id.text_view_number_picker_day);
        textNumberPickerMonth = view.findViewById(R.id.text_view_number_picker_month);
        textNumberPickerYear = view.findViewById(R.id.text_view_number_picker_year);


        buttonSetDate = view.findViewById(R.id.button_set_date);


        numberPickerDay.setMinValue(1);
        numberPickerDay.setMaxValue(31);

        numberPickerMonth.setMinValue(1);
        numberPickerMonth.setMaxValue(12);

        numberPickerYear.setMinValue(1340);
        numberPickerYear.setMaxValue(1450);
        numberPickerYear.setValue(1397);


        numberPickerDay.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {


                if (oldVal != newVal) {

                    day = String.valueOf(newVal);
                } else {
                    day = String.valueOf(oldVal);
                }
            }
        });

        numberPickerMonth.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if (oldVal != newVal) {

                    month = String.valueOf(newVal);
                } else {
                    month = String.valueOf(oldVal);
                }

            }
        });

        numberPickerYear.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if (oldVal != newVal) {

                    year = String.valueOf(newVal);
                } else {
                    year = String.valueOf(oldVal);
                }

            }
        });


    }

    private void setFont(Typeface typeface) {

        textNumberPickerYear.setTypeface(typeface);
        textNumberPickerMonth.setTypeface(typeface);
        textNumberPickerDay.setTypeface(typeface);

        buttonSetDate.setTypeface(typeface);

    }

    private void setTime() {
        mListener.onComplete(year + "/" + month + "/" + day);
    }

}
