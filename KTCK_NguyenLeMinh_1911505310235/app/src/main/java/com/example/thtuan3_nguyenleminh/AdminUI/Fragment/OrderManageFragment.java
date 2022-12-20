package com.example.thtuan3_nguyenleminh.AdminUI.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.thtuan3_nguyenleminh.AdminUI.MyViewPagerAdapter;
import com.example.thtuan3_nguyenleminh.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class OrderManageFragment extends Fragment {

    private TabLayout mTabLayout;
    private ViewPager2 mViewPager;
    private MyViewPagerAdapter myViewPagerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.order_management_fragment, container, false);

//        Toolbar toolbar = rootview.findViewById(R.id.drawer_layout_toolbar);
//        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        mTabLayout =  rootview.findViewById(R.id.tab_layout);
        mViewPager = rootview.findViewById(R.id.view_pager2);

        myViewPagerAdapter = new MyViewPagerAdapter(this);
        mViewPager.setAdapter(myViewPagerAdapter);

        new TabLayoutMediator(mTabLayout, mViewPager, (tab, position) -> {
            switch (position){
                case 0:
                    tab.setText("Order");
                    break;
                case 1:
                    tab.setText("Accept");
                    break;
                case 2:
                    tab.setText("Process");
                    break;
            }
        }).attach();


        return rootview;
    }
}
