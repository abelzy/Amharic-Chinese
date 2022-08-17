package com.abelzw.dictionary;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Developers_Fragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view ;
        view=initialize();


    return view;
    }
    public View initialize ()
    {

        Element versionElement = new Element();
        versionElement.setTitle("Brought to you by : ");
        Element adsElement = new Element();
        adsElement.setTitle("Tadiyo     |     Abel     |     Negasi     |     Melkamu");

        View aboutPage = new AboutDevelopersPage(getActivity())
                .isRTL(false)
                .setImage(R.drawable.ic_launcher)
                .addItem(versionElement)
                .addItem(adsElement)
                .addGroup("Connect with us on Email")
                .addEmail("betretechnology@gmail.com")
                .addWebsite("https://betretech.yolasite.com/")
                .addFacebook("Betre-Tech-442572619869734")
                .addTwitter("BetreTech")
                .addYoutube("UCbblRShZnuq8dOiAkWysAww")
//                .addPlayStore("com.ideashower.readitlater.pro")
                .addGitHub("BetreTech")
                .addInstagram("betretech")
                .create();
        return aboutPage;
    }
}