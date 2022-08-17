package com.abelzw.dictionary;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Settings extends Fragment {
    TextView sharebtn,aboutus,aboutapp;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.setting, container, false);
        sharebtn=(TextView) view.findViewById(R.id.share_app);
        sharebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ShareApkFile().shareApplication(getActivity());

            }
        });


        aboutus=(TextView)view.findViewById(R.id.about_us);
        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        aboutapp=(TextView)view.findViewById(R.id.about_app);
        aboutapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                About_App_Fragment fragment2 = new About_App_Fragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(android.R.id.content, fragment2);
                fragmentTransaction.commit();


            }
        });



        return view;
    }

}
