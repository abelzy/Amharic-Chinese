package com.abelzw.dictionary;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.swipe.util.Attributes;

import java.util.ArrayList;
import java.util.Arrays;

import jp.wasabeef.recyclerview.animators.FadeInLeftAnimator;


public class Favorite extends Fragment {

        /**
         * RecyclerView: The new recycler view replaes the list view. Its more modular and therefore we
         * must implement some of the functionality ourselves and attach it to our recyclerview.
         * <p/>
         * 1) Position items on the screen: This is done with LayoutManagers
         * 2) Animate & Decorate views: This is done with ItemAnimators & ItemDecorators
         * 3) Handle any touch events apart from scrolling: This is now done in our adapter's ViewHolder
         */

        private RecyclerView recyclerView;
        private RecyclerView.Adapter mAdapter;
    DividerItemDecorations dv;

        private ArrayList<String> mDataSet;
    private ArrayList<String> mDatamean;
    private ArrayList<String> mDataids;
    private int translator;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fav_layout, container, false);
        recyclerView = (RecyclerView)view. findViewById(R.id.recycle_fav);


        // Layout Managers:
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        // Item Decorator:
        dv =new DividerItemDecorations(getResources().getDrawable(R.drawable.divider));
        recyclerView.addItemDecoration(dv);
        recyclerView.setItemAnimator(new FadeInLeftAnimator());

        // Adapter:
        DbBackend dbBackend = new DbBackend(getActivity());
        // Adapter
        translator=Integer.parseInt(dbBackend.getTranslator());
        String[] adapterData = dbBackend.favoriteWords(2);
        String[] meaning =dbBackend.favoriteWords(translator);
        String[] ids =dbBackend.favoriteids();
        dbBackend.closeDbConnection();
      //  String[] adapterData = new String[]{"ABel", "Tadiyo", "Negasi", "Melke", "Betre", "Tech", "Sheger", "Hawasa", "Jimma", "Harrar", "DireDawa", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Carolina", "North Dakota", "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota", "Tennessee", "Texas", "Utah", "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming"};
        mDataSet = new ArrayList<String>(Arrays.asList(adapterData));
        mDatamean = new ArrayList<String>(Arrays.asList(meaning));
        mDataids = new ArrayList<String>(Arrays.asList(ids));

        mAdapter = new RecyclerViewAdapter_Favorite(getActivity(), mDataSet,mDatamean,mDataids);
        ((RecyclerViewAdapter_Favorite) mAdapter).setMode(Attributes.Mode.Single);
        recyclerView.setAdapter(mAdapter);

        /* Listeners */
        recyclerView.setOnScrollListener(onScrollListener);
        return view;
    }

    /**
     * Substitute for our onScrollListener for RecyclerView
     */
    RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            Log.e("ListView", "onScrollStateChanged");
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);


        }
    };
 public void refreshAdapter()
 {
     DbBackend dbBackend = new DbBackend(getActivity());
     // Adapter
     translator=Integer.parseInt(dbBackend.getTranslator());
     String[] adapterData = dbBackend.favoriteWords(2);
     String[] meaning =dbBackend.favoriteWords(translator);
     String[] ids =dbBackend.favoriteids();
     dbBackend.closeDbConnection();
     mDataSet = new ArrayList<String>(Arrays.asList(adapterData));
     mDatamean = new ArrayList<String>(Arrays.asList(meaning));
     mDataids = new ArrayList<String>(Arrays.asList(ids));

     mAdapter = new RecyclerViewAdapter_Favorite(getActivity(), mDataSet,mDatamean,mDataids);
     recyclerView.setAdapter(mAdapter);
 }

}




