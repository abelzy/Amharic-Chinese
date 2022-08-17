package com.abelzw.dictionary;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.daimajia.swipe.implments.SwipeItemRecyclerMangerImpl;
import com.daimajia.swipe.util.Attributes;

import java.util.ArrayList;
import java.util.Arrays;

import jp.wasabeef.recyclerview.animators.FadeInLeftAnimator;







public class History extends Fragment {

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
    private ArrayList<String> mDataSet;
    private Button buttonClear;
    private ArrayList<String> mDatamean;
    private ArrayList<String> mDataids;
    DividerItemDecorations dv;
    private int translator;

//        private ArrayList<String> mDataSet;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.hist_layout, container, false);
        recyclerView = (RecyclerView)view. findViewById(R.id.recycle_hist);
        buttonClear = (Button)view.findViewById(R.id.clearall);
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Are you sure to clear all History ? ");


                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Here is the query code will be added
                        clearall(getContext());

                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        // Here is the disproval code will be added

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                //

                //
            }
        });

        // Layout Managers:
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Item Decorator:
        dv =new DividerItemDecorations(getResources().getDrawable(R.drawable.divider));
        recyclerView.addItemDecoration(dv);
        recyclerView.setItemAnimator(new FadeInLeftAnimator());

        // Adapter:
//        String[] adapterData = new String[]{"Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut", "Delaware", "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Carolina", "North Dakota", "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota", "Tennessee", "Texas", "Utah", "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming"};

       inflate_History();


//        mDataSet = new ArrayList<String>(Arrays.asList(cursor));
//        mAdapter = new RecyclerViewAdapter_History(getActivity(), mDataSet);
//        ((RecyclerViewAdapter_History) mAdapter).setMode(Attributes.Mode.Single);
//        recyclerView.setAdapter(mAdapter);










        /* Listeners */
        recyclerView.setOnScrollListener(onScrollListener);
        return view;
    }
    public void inflate_History()
    {
        DbBackend dbBackend = new DbBackend(getActivity());
        // Adapter
        translator=Integer.parseInt(dbBackend.getTranslator());
        String[] adapterData = dbBackend.historyWords(2);
        String[] meaning =dbBackend.historyWords(translator);
        String[] ids =dbBackend.historyids();

        dbBackend.closeDbConnection();
        mDataSet = new ArrayList<String>(Arrays.asList(adapterData));
        mDatamean = new ArrayList<String>(Arrays.asList(meaning));
        mDataids = new ArrayList<String>(Arrays.asList(ids));

        mAdapter = new RecyclerViewAdapter_History(getActivity(), mDataSet,mDatamean,mDataids);
        ((RecyclerViewAdapter_History) mAdapter).setMode(Attributes.Mode.Single);
        recyclerView.setAdapter(mAdapter);
    }



    public void clearall(Context view){
        DbBackend dbBackend = new DbBackend(view.getApplicationContext());
        String query="Update dictionary set history=0";
        dbBackend.getDbConnection().execSQL(query);
        dbBackend.closeDbConnection();
        inflate_History();

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


}




