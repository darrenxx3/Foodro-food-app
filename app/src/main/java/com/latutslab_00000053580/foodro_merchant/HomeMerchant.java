package com.latutslab_00000053580.foodro_merchant;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.latutslab_00000053580.foodro.APIHandler;
import com.latutslab_00000053580.foodro_home.MerchantAddMenu;
import com.latutslab_00000053580.foodro_home.R;
import com.latutslab_00000053580.sqlite.DbUser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeMerchant#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeMerchant extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Context mContext;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeMerchant() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeMerchantFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeMerchant newInstance(String param1, String param2) {
        HomeMerchant fragment = new HomeMerchant();
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.merchant_home, container, false);
        TextView txtUser = view.findViewById(R.id.txtName);

        DbUser dbuser = new DbUser(getContext());
        dbuser.open();
        int user_id = dbuser.getID();
        String name = dbuser.getName();
        Log.i("NAMA", name);
        dbuser.close();
        txtUser.setText("Hello, "+name);

        RecyclerView merchantRV = view.findViewById(R.id.menuRV);
        APIHandler handler = new APIHandler();
        handler.getFoodByMerchant(getContext(), user_id, merchantRV, 2);

        TextView txtNoFood = (TextView) view.findViewById(R.id.txtNoFood);
        txtNoFood.setVisibility(View.GONE);

        if(merchantRV.getVisibility() == View.GONE){
            txtNoFood.setVisibility(View.VISIBLE);
        }

        Button btnAddmenu = (Button) view.findViewById(R.id.buttonAddMenu);
        btnAddmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.getContext().startActivity(new Intent(getContext(), MerchantAddMenu.class));
            }
        });

        return view;
    }
}