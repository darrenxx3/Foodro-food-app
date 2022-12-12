package com.latutslab_00000053580.foodro_home;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class OrderNavigationAdapter extends FragmentStateAdapter {
    public OrderNavigationAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 1){
            return new OrderHistory();
        }

        return new OrderIncoming();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
