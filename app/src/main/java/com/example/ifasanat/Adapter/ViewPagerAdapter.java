package com.example.ifasanat.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.widget.PopupMenu;
import android.view.View;

import com.example.ifasanat.WizardFragments.WizardFragment;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<WizardFragment> fragments = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        WizardFragment f = fragments.get(i);
        return f;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public void addFragment(String title, WizardFragment fragment)
    {
        fragment.setTitle(title);
        addFragment(fragment);
    }

    public void addFragment(WizardFragment fragment){
        fragments.add(fragment);
        notifyDataSetChanged();
    }

    public List<WizardFragment> getFragments(){
        return fragments;
    }

    public String getTitle(int position){
        return fragments.get(position).getTitle();
    }

    public PopupMenu getMenu(int position, Context context, View view)
    {
        return fragments.get(position).getMenu(context,view);
    }

}
