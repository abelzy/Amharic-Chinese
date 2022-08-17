package com.abelzw.dictionary;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.daimajia.swipe.implments.SwipeItemRecyclerMangerImpl;

import java.util.ArrayList;

public class RecyclerViewAdapter_History extends RecyclerSwipeAdapter<RecyclerViewAdapter_History.SimpleViewHolder> {
    SwipeLayout swipeLayout1;



    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        public String word;
        SwipeLayout swipeLayout;
        TextView textViewData;
        TextView textViewMean;
        Button buttonDelete;
        ImageView arrow_view;



        public void callActivity(int position,View v) {
            Intent intent = new Intent(v.getContext(), DictionaryActivity.class);

            intent.putExtra("DICTIONARY_ID", position);
            intent.putExtra("colorapp","#11522e");
            v.getContext().startActivity(intent);
        }
        public SimpleViewHolder(View itemView) {
            super(itemView);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipehis);
            textViewData = (TextView) itemView.findViewById(R.id.text_datahis);
            textViewMean=(TextView) itemView.findViewById(R.id.detail_hist) ;
            buttonDelete = (Button) itemView.findViewById(R.id.deletehis);
            arrow_view=(ImageView) itemView.findViewById(R.id.imgarrow);







            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   // callActivity(getAdapterPosition(),view);
                  //  Log.d(getClass().getSimpleName(), "onItemSelected: " + textViewData.getText().toString());
                  //  Toast.makeText(view.getContext(), "onItemSelected: " + textViewData.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    private Context mContext;
    private ArrayList<String> mDataset;
    private ArrayList<String> mDatamean;
    private ArrayList<String> mDataids;


    protected SwipeItemRecyclerMangerImpl mItemManger = new SwipeItemRecyclerMangerImpl(this);

    public RecyclerViewAdapter_History(Context context, ArrayList<String> objects,ArrayList<String> objects1,ArrayList<String> objects2) {
        this.mContext = context;
        this.mDataset = objects;
        this.mDatamean= objects1;
        this.mDataids =objects2;
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hist_item, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder viewHolder, final int position) {
        String item = mDataset.get(position);
        String item1 = mDatamean.get(position);
        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
       swipeLayout1= viewHolder.swipeLayout;
        viewHolder.swipeLayout.setTag("No");
        YoYo.with(Techniques.BounceInRight).duration(2000).delay(100).playOn(viewHolder.itemView.findViewById(R.id.imgarrow));


        viewHolder.swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                        YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.trashhis));
            }
            @Override
            public void onStartOpen(SwipeLayout layout) {
                viewHolder.swipeLayout.setTag("Yes");
            }

            @Override
            public void onClose(SwipeLayout layout) {
                viewHolder.swipeLayout.setTag("No");

            }
        });








        viewHolder.swipeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((viewHolder.swipeLayout.getTag().equals("No")))
                {
                    viewHolder.callActivity(Integer.parseInt(mDataids.get(viewHolder.getAdapterPosition()))-1,viewHolder.itemView);
                }
            }
        });

        viewHolder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //
                DbBackend dbBackend = new DbBackend(view.getContext());
                mItemManger.removeShownLayouts(viewHolder.swipeLayout);
                String query="Update dictionary set history=0 where kaffigna==" +  '\'' + mDataset.get(position) +  '\'';
                dbBackend.getDbConnection().execSQL(query);
                dbBackend.closeDbConnection();
                mDataset.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mDataset.size());
                mItemManger.closeAllItems();
                notifyDataSetChanged();
                Toast.makeText(view.getContext(), "Deleted " + viewHolder.textViewData.getText().toString() + "!", Toast.LENGTH_SHORT).show();
                //
                Toast.makeText(view.getContext(), "Deleted " + viewHolder.textViewData.getText().toString() + "!", Toast.LENGTH_SHORT).show();
            }
        });






        viewHolder.textViewMean.setText(item1);
        viewHolder.textViewData.setText(item);
        mItemManger.bindView(viewHolder.itemView, position);
    }






    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipehis;
    }
}
