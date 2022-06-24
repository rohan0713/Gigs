package com.financebazaar.gigs;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class moneyPageAdapter extends FragmentPagerAdapter {

    int tab_count;
    public moneyPageAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        tab_count = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new payoutFragment();
        }
        else if (position == 1) {
            return new hold_fragment();
        } else {

            return new history_fragment();
        }
    }

    @Override
    public int getCount() {
        return tab_count;
    }
}
