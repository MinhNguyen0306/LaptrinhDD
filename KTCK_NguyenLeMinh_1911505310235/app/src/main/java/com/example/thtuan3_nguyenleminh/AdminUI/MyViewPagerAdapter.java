package com.example.thtuan3_nguyenleminh.AdminUI;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.thtuan3_nguyenleminh.AdminUI.Fragment.AcceptFragment;
import com.example.thtuan3_nguyenleminh.AdminUI.Fragment.OrderFragment;
import com.example.thtuan3_nguyenleminh.AdminUI.Fragment.ProcessFragment;

public class MyViewPagerAdapter extends FragmentStateAdapter {


    public MyViewPagerAdapter(@NonNull Fragment fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new OrderFragment();
            case 1:
                return new AcceptFragment();
            case 2:
                return new ProcessFragment();
            default:
                return new OrderFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
