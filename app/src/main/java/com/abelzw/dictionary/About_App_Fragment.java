package com.abelzw.dictionary;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class About_App_Fragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view ;
        view=initialize();

        return view;
    }
    public View initialize ()
    {
        Element inf = new Element();
        inf.setTitle("Information");
        Element version = new Element();
        version.setTitle("Version :   1.0");
        Element date = new Element();
        date.setTitle("Updated : 16 May 2019");
        Element perm = new Element();
        perm.setTitle("Permission : Internet / Phone / Storage");
        Element type = new Element();
        type.setTitle("Type : APK");
        Element cat = new Element();
        cat.setTitle("Category : Social");
        Element rate = new Element();
        rate.setTitle("Rating : Teen");
        Element get = new Element();
        get.setTitle("Get it on : Google Play");

        View aboutPage = new AboutAppPage(getActivity())
                .isRTL(false)
                .addItem(inf)
                .addItem(version)
                .addItem(date)
                .addItem(perm)
                .addItem(type)
                .addItem(cat)
                .addItem(rate)
                .addItem(get)
                .create();
        return aboutPage;
    }
}