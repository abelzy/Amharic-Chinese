package com.abelzw.dictionary;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.implments.SwipeItemRecyclerMangerImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class RecyclerViewAdapter_Home extends RecyclerView.Adapter<RecyclerViewAdapter_Home.SimpleViewHolder> {
    SwipeLayout swipeLayout1;
    boolean fav_check[];
    static View flop;





    public static class SimpleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        SwipeLayout swipeLayout;
        TextView textViewData;
        TextView textViewMean;
        private ArrayList<String> mDataids2;



        public SimpleViewHolder(View itemView,ArrayList<String> mDataids) {
            super(itemView);
          //  swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            textViewData = (TextView) itemView.findViewById(R.id.i_word);
            textViewMean=(TextView) itemView.findViewById(R.id.detail_mean);
            mDataids2=mDataids;

            flop=itemView;
            itemView.setOnClickListener(this);


        }





        public void callActivity(int position,View v) {
            Intent intent = new Intent(v.getContext(), DictionaryActivity.class);

            intent.putExtra("DICTIONARY_ID", position);
            intent.putExtra("colorapp","#013220");
            v.getContext().startActivity(intent);
        }

        @Override
        public void onClick(View v) {
            callActivity(Integer.parseInt(mDataids2.get(getAdapterPosition()))-1,v);

            DbObject g=new DbObject(v.getContext());

//            g.getDbConnection().execSQL("UPDATE dictionary SET history='1',clickeAt=datetime() WHERE id="+(getAdapterPosition()+1));
            
            ContentValues cv = new ContentValues();
            cv.put("history",1); //These Fields should be your String values of actual column names
            cv.put("clickedAt",getDateTime());

            g.getDbConnection().update("dictionary", cv, "id="+mDataids2.get(getAdapterPosition()), null);

            g.getDbConnection().close();
        }

        public String getDateTime() {
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            Date date = new Date();
            return dateFormat.format(date);
        }
    }

    private Context mContext;
    private ArrayList<String> mDataset;
    private ArrayList<String> mDatamean;
    private ArrayList<String> mDataids;


//    protected SwipeItemRecyclerMangerImpl mItemManger = new SwipeItemRecyclerMangerImpl(this);

    public RecyclerViewAdapter_Home(Context context, ArrayList<String> objects, ArrayList<String> objects1, ArrayList<String> objects2) {
        this.mContext = context;
        this.mDataset = objects;
        this.mDatamean= objects1;
        this.mDataids=objects2;
        fav_check=new boolean[this.mDataset.size()];
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item, parent, false);


        return new SimpleViewHolder(view,mDataids);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder viewHolder, final int position) {
        String item = mDataset.get(position);
        String item1= mDatamean.get(position);
        viewHolder.textViewMean.setText(item1);
        viewHolder.textViewData.setText(item);


    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }





}

