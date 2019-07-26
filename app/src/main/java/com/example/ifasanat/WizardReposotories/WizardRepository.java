package com.example.ifasanat.WizardReposotories;

import android.support.v4.app.FragmentManager;

import com.example.ifasanat.Adapter.ViewPagerAdapter;
import com.example.ifasanat.WizardFragments.WizardFragment;

import java.util.List;

public class WizardRepository {

    private static List<WizardFragment> wizardFragments;

    public static ViewPagerAdapter getWizardAdapter(FragmentManager fm) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(fm);
        if(wizardFragments != null)
        {
            for (WizardFragment wf:
                    wizardFragments) {
                adapter.addFragment(wf);
            }
        }
        return adapter;
    }

    public static void setWizardFragments(List<WizardFragment> wizardFragments) {
        WizardRepository.wizardFragments = wizardFragments;
    }

    public static List<WizardFragment> getWizardFragments() {
        return wizardFragments;
    }

}
