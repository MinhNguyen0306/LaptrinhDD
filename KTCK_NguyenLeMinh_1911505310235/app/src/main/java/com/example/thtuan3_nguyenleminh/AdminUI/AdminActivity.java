package com.example.thtuan3_nguyenleminh.AdminUI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.thtuan3_nguyenleminh.AdminUI.Fragment.OrderManageFragment;
import com.example.thtuan3_nguyenleminh.R;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

public class AdminActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final int FRAGMENT_ORDER = 0;
    private static final int FRAGMENT_FOOD_MANAGEMENT = 1;
    private int mCurrentFragment = FRAGMENT_FOOD_MANAGEMENT;

    private DrawerLayout drawerLayout;
    private TabLayout mTabLayout;
    private ViewPager2 mViewPager;
    private MyViewPagerAdapter myViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        //Mapped to components
        Toolbar toolbar = findViewById(R.id.drawer_layout_toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

//        replaceFragment(new FoodManagementFragment());
        navigationView.getMenu().findItem(R.id.nav_drawer_food).setChecked(true);


//        mTabLayout =  findViewById(R.id.tab_layout);
//        mViewPager = findViewById(R.id.view_pager2);
//
//        myViewPagerAdapter = new MyViewPagerAdapter(this);
//        mViewPager.setAdapter(myViewPagerAdapter);
//
//        new TabLayoutMediator(mTabLayout, mViewPager, (tab, position) -> {
//            switch (position){
//                case 0:
//                    tab.setText("Order");
//                    break;
//                case 1:
//                    tab.setText("Accept");
//                    break;
//                case 2:
//                    tab.setText("Process");
//                    break;
//            }
//        }).attach();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_drawer_order){
            if(mCurrentFragment != FRAGMENT_ORDER) {
                replaceFragment(new OrderManageFragment());
                mCurrentFragment = FRAGMENT_ORDER;
            }
        }else if(id == R.id.nav_drawer_food){
            if(mCurrentFragment != FRAGMENT_FOOD_MANAGEMENT) {
//                replaceFragment(new FoodManagementFragment());
                mCurrentFragment = FRAGMENT_FOOD_MANAGEMENT;
            }
        }else if(id == R.id.nav_drawer_user){

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.commit();
    }
}