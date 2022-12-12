package com.latutslab_00000053580.foodro_home;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.latutslab_00000053580.sqlite.DbCart;
import com.latutslab_00000053580.sqlite.DbUser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileUser#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileUser extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileUser() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileUser.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileUser newInstance(String param1, String param2) {
        ProfileUser fragment = new ProfileUser();
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

        View view = inflater.inflate(R.layout.home_profile_user, container, false);

        TextView fullname = view.findViewById(R.id.fullname);
        DbUser dbuser = new DbUser(getContext());
        dbuser.open();
        String name = dbuser.getName();
        dbuser.close();
        fullname.setText(name);

        Button btnLogout = (Button) view.findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Logout?")
                        .setMessage("Are you sure you want to log out?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                DbUser dbUser = new DbUser(view.getContext());
                                dbUser.open();
                                dbUser.logout();
                                dbUser.close();

                                // clear cart
                                DbCart dbCart = new DbCart(view.getContext());
                                dbCart.open();
                                dbCart.clearCart();
                                dbCart.close();
                                startActivity(new Intent(view.getContext(), Account_Setup.class));
                            }
                        })

                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();


            }
        });

        CardView cardAbout = (CardView) view.findViewById(R.id.cardAbout);
        cardAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AboutUs.class));
            }
        });

        CardView cardLoc = (CardView) view.findViewById(R.id.cardLocation);
        cardLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MapsLocation.class));
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}