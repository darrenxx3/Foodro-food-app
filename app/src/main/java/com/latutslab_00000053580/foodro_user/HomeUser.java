package com.latutslab_00000053580.foodro_user;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.latutslab_00000053580.foodro.APIHandler;
import com.latutslab_00000053580.foodro.User;
import com.latutslab_00000053580.foodro_home.BasketUser;
import com.latutslab_00000053580.foodro_home.R;
import com.latutslab_00000053580.sqlite.DbUser;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeUser#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeUser extends Fragment {

    static final public ArrayList<User> userArrayList= new ArrayList<User>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeUser() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeNavigationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeUser newInstance(String param1, String param2) {
        HomeUser fragment = new HomeUser();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.user_home, container, false);
        DbUser dbUser = new DbUser(getContext());
        dbUser.open();

        TextView txtUser = view.findViewById(R.id.txtUsername);
        txtUser.setText(dbUser.getName());
        dbUser.close();
        FloatingActionButton fabCart = (FloatingActionButton) view.findViewById(R.id.fabCart);
        fabCart.bringToFront();
        fabCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCart = new Intent(view.getContext(), BasketUser.class);
                startActivity(intentCart);
            }
        });

        RecyclerView userRV = view.findViewById(R.id.userRV);
        APIHandler handler = new APIHandler();
        handler.getAllMerchant(view.getContext(), userRV);

        return view;
    }
}