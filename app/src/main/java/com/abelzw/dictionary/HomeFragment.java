package com.abelzw.dictionary;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;


public class HomeFragment extends Fragment {
    private EditText filterText;
    private Button language_Change;
    private TextView current_Date;
    private TextView random_word;
    private TextView random_mean;
    private RecyclerView itemList;
    private RecyclerViewAdapter_Home mAdapter;
    private ArrayList<String> mDataSet;
    private ArrayList<String> mDataids;
    private CollapsingToolbarLayout  mCollapsingToolbarLayout;
    private  AppBarLayout appBarLayout;
    private ArrayList<String> mDataMean;
   int translator,ck,random_no;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.home, container, false);
        filterText = (EditText) view.findViewById(R.id.et_search);
        current_Date=(TextView) view.findViewById(R.id.date_text);
        language_Change=(Button) view.findViewById(R.id.bt_language);
        random_word=(TextView) view.findViewById(R.id.random_word);
        random_mean=(TextView) view.findViewById(R.id.random_meaning);
        itemList = (RecyclerView) view.findViewById(R.id.recycle_home);
        mCollapsingToolbarLayout=(CollapsingToolbarLayout) view.findViewById(R.id.collapsing);
        appBarLayout = (AppBarLayout) view.findViewById(R.id.appbar_detail);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {

                    //placing toolbar in place of actionbar
                   // View view = inflater.inflate(R.layout.home, container, false);

                    isShow = true;
                } else if(isShow) {
                    mCollapsingToolbarLayout.setTitle(" ");//careful there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });

        // Layout Managers:
        itemList.setLayoutManager(new LinearLayoutManager(getActivity()));


      influate_home();
        // getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);



        language_Change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String[] listItems = {"Amharic", "English"};

                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Translate Kaffigna to : ");

                int checkedItem = translator; //this will checked the item when user open the dialog

                builder.setSingleChoiceItems(listItems, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(MainActivity.this, "Position: " + which + " Value: " + listItems[which], Toast.LENGTH_LONG).show();
                        ck=which;
                    }
                });

                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(translator==ck){
//                            Toast.makeText(MainActivity.this, "Same as current", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }
                        else {
                            if(translator==1){
                                DbBackend dbBackend = new DbBackend(getActivity());
                                translator=ck;
                                String query="Update changer set translator="+ translator;
                                dbBackend.getDbConnection().execSQL(query);
                                dbBackend.closeDbConnection();
                                change_meaning(translator);
//                                Toast.makeText(MainActivity.this, "Not same and new Amharic", Toast.LENGTH_LONG).show();

                              //  getSupportFragmentManager().beginTransaction().detach(HomeFragment).attach(new HomeFragment()).commit();

                            }
                            else if(translator==0){
                                DbBackend dbBackend = new DbBackend(getActivity());
                                translator=ck;
                                change_meaning(translator);
                                String query="Update changer set translator="+ translator;
                                dbBackend.getDbConnection().execSQL(query);
                                dbBackend.closeDbConnection();
//                                Toast.makeText(MainActivity.this, "Not same and new English", Toast.LENGTH_LONG).show();

                              //  getSupport


                                //FragmentManager().beginTransaction().detach(this).attach(new HomeFragment())
                                //        .commit();

                            }
                        }


                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });


                AlertDialog dialog = builder.create();
                dialog.show();



            }
        });

        //
        filterText.addTextChangedListener(new TextWatcher() {

            @Override

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList mDataSetTemp = new ArrayList<String>();
                ArrayList mDataMeanTemp = new ArrayList<String>();
                ArrayList mDataidsTemp = new ArrayList<String>();



                mDataSetTemp.clear();
                mDataMeanTemp.clear();

                if (filterText.getText().toString().isEmpty()) {
                    influate_home();
                } else {

                    for (int i = 0; i < mDataSet.size(); i++) {
                        if (mDataSet
                                .get(i)
                                .toLowerCase()
                                .contains(
                                        filterText.getText().toString()
                                                .toLowerCase())) {
                            mDataSetTemp.add(mDataSet.get(i));
                            mDataMeanTemp.add(mDataMean.get(i));
                            DbBackend dbBack=new DbBackend(getContext());
                            String ids=dbBack.filterids(mDataSet.get(i));
                            mDataidsTemp.add(ids);
                        }
                        mAdapter = new RecyclerViewAdapter_Home(getActivity(), mDataSetTemp,mDataMeanTemp,mDataidsTemp);
                        itemList.setAdapter(mAdapter);

                    }

                }
            }



            @Override

            public void afterTextChanged(Editable s) {

            }

        });

        return view;
    }

    private void change_meaning(int tr) {
        DbBackend dbBack=new DbBackend(getContext());
        String[] meaning =dbBack.dictionaryWords(tr);
        dbBack.closeDbConnection();
        random_mean.setText(meaning[random_no]);
        mDataMean = new ArrayList<String>(Arrays.asList(meaning));
        mAdapter = new RecyclerViewAdapter_Home(getActivity(), mDataSet,mDataMean,mDataids);
        itemList.setAdapter(mAdapter);




    }
public void  influate_home(){
    DbBackend dbBackend = new DbBackend(getActivity());
    // Adapter
    translator=Integer.parseInt(dbBackend.getTranslator());
    String[] terms = dbBackend.dictionaryWords(2);
    String[] meaning =dbBackend.dictionaryWords(translator);
    String[] ids =dbBackend.dictionaryids();
    String db_date=dbBackend.get_Date();

    itemList.addItemDecoration(new DividerItemDecoration(itemList.getContext(), DividerItemDecoration.VERTICAL));
    mDataSet = new ArrayList<String>(Arrays.asList(terms));
    mDataMean = new ArrayList<String>(Arrays.asList(meaning));
    mDataids = new ArrayList<String>(Arrays.asList(ids));
    mAdapter = new RecyclerViewAdapter_Home(getActivity(), mDataSet,mDataMean,mDataids);
    itemList.setAdapter(mAdapter);


    String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
    current_Date.setText(date);
    Random rand = new Random();
    ContentValues cv = new ContentValues();

    cv.put("day_data",date);

    if(db_date.equals(date))
    {
        int random=Integer.parseInt(dbBackend.getRand_no());
        random_word.setText(terms[random]);
        random_mean.setText(meaning[random]);
    }
    else {
        random_no = rand.nextInt(terms.length);
        random_word.setText(terms[random_no]);
        random_mean.setText(meaning[random_no]);
        String query="Update changer set rand_no="+random_no;
        dbBackend.getDbConnection().update("changer", cv, null, null);
     //   String query1="Update changer set day_data="+date;
        dbBackend.getDbConnection().execSQL(query);
     //   dbBackend.getDbConnection().execSQL(query1);
    }
    //


}

}

