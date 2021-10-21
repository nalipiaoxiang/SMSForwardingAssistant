package com.cpc.smsforwardingassistant.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

/**
 * 首页Fragment适配器
 */
public class HomeFragmentsAdapter extends FragmentStateAdapter {

    public List<Fragment> fragments;

    public HomeFragmentsAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle,@NonNull List<Fragment> fragments) {
        super(fragmentManager, lifecycle);
        this.fragments = fragments;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }
}
