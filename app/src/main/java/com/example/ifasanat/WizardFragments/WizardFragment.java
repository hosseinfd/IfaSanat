package com.example.ifasanat.WizardFragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.view.View;

import com.example.ifasanat.DataModel.CapturesDataModel;

import static com.example.ifasanat.VariableKeys.VariableKeys.KEY_WIZARD_MODEL;

public class WizardFragment extends Fragment {
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public PopupMenu getMenu(Context context, View view) {
        return null;
    }


    public static WizardCaptureImageFragment newInstance(String title, CapturesDataModel model) {
        Bundle args = new Bundle();
        args.putParcelable(KEY_WIZARD_MODEL, model);
        WizardCaptureImageFragment fragment = new WizardCaptureImageFragment();
        fragment.setArguments(args);
        fragment.setTitle(title);
        return fragment;
    }

    public void clear(){
        throw new UnsupportedOperationException();

    }

}
