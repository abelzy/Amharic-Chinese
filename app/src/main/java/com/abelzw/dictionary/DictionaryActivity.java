package com.abelzw.dictionary;
import android.annotation.TargetApi;

import android.content.Intent;

import android.database.Cursor;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Build;

import android.os.Bundle;

import android.speech.tts.TextToSpeech;

import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import android.view.GestureDetector;
import android.view.Menu;

import android.view.MenuItem;

import android.view.MotionEvent;
import android.view.View;

import android.widget.Button;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.HashMap;
import java.util.Locale;

public class DictionaryActivity extends AppCompatActivity  {

    private TextView wordMeaning,wordMeaning1;
    private FloatingActionButton favdetail;
    private TextToSpeech convertToSpeech;

    int check;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.dictionary_detail);

        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();

        int dictionaryId = bundle.getInt("DICTIONARY_ID");
        String colorapp= bundle.getString("colorapp");

        final int id = dictionaryId + 1;

        TextView word = (TextView)findViewById(R.id.word);

        wordMeaning = (TextView)findViewById(R.id.textView2);
     //   constraintLayout_detail=(ConstraintLayout) findViewById(R.id.cons_layout_detail);
        wordMeaning1= (TextView)findViewById(R.id.textView4);
        AppBarLayout appBarLayout=(AppBarLayout) findViewById(R.id.appbar_detail);
        appBarLayout.setBackgroundColor(Color.parseColor(colorapp));
        favdetail =(FloatingActionButton) findViewById(R.id.imageView3);

        final ImageView textToSpeech = (ImageView) findViewById(R.id.imageView2);





        DbBackend dbBackend = new DbBackend(DictionaryActivity.this);
        QuizObject allQuizQuestions = dbBackend.getQuizById(id);
        ImageButton shared=(ImageButton)findViewById(R.id.share);


       word.setText(allQuizQuestions.getWord());
        check= allQuizQuestions.getFavorite();
        if(check==1)
        {
            favdetail.setImageResource(R.drawable.fav);
        }
        else {
            favdetail.setImageResource(R.drawable.notfav);

        }
        wordMeaning.setText(allQuizQuestions.getDefinition());
        wordMeaning1.setText(allQuizQuestions.getDefinition1());
        final String sharekaff= (String) word.getText();
        final String shareahm=(String)wordMeaning.getText();
        final String shareeng=(String)wordMeaning1.getText();
        shared.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody ="Kaffigna : "+sharekaff+"\nAmharic : "+shareahm+"\nEnglish : "+shareeng;
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Send Word With It's Meaning");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Send Kaffigna Word With It's Meaning Via:"));
            }
        });
//        constraintLayout_detail.setOnTouchListener(new OnSwipeTouchListener(DictionaryActivity.this) {
//            @Override
//            public void onSwipeRight() {
//                onBackPressed();
//                overridePendingTransition(R.anim.fadein,R.anim.slidefromleft);
////                FragmentManager fm = getSupportFragmentManager();
////                Favorite fav = (Favorite) fm.findFragmentByTag("Favorite");
////                fav.refreshAdapter();
//
//            }
//        });
        dbBackend.closeDbConnection();
        favdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbBackend dbBackend1=new DbBackend(v.getContext());
                if(check==1)
                {
                    String query="Update dictionary set favorite=0 where id ="+ id;
                    dbBackend1.getDbConnection().execSQL(query);
                    dbBackend1.closeDbConnection();
                    Toast.makeText(v.getContext(), "Removed", Toast.LENGTH_SHORT).show();


                   favdetail.setImageResource(R.drawable.notfav);
                    //  sharedPreference.removeFavorite(context, products.get(position));
                    check=0;

                }
                else {
                    String query="Update dictionary set favorite=1 where id ="+ id;
                    dbBackend1.getDbConnection().execSQL(query);
                    dbBackend1.closeDbConnection();
                    Toast.makeText(v.getContext(), "Added", Toast.LENGTH_SHORT).show();
                    favdetail.setImageResource(R.drawable.fav);
                    //  sharedPreference.removeFavorite(context, products.get(position));
                    check=1;
                }
                dbBackend1.closeDbConnection();
            }
        });

        textToSpeech.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                final String convertTextToSpeech = wordMeaning1.getText().toString();


                    convertToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {



                        @Override

                        public void onInit(int status) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                                if (status != TextToSpeech.ERROR) {

                                    convertToSpeech.setLanguage(Locale.US);

                                    convertToSpeech.speak(convertTextToSpeech, TextToSpeech.QUEUE_FLUSH, null, null);

                                }
                            }
                                else{
                                    HashMap<String, String> param = new HashMap<>();
                                    param.put(TextToSpeech.Engine.KEY_PARAM_STREAM, String.valueOf(AudioManager.STREAM_MUSIC));
                                    convertToSpeech.speak(convertTextToSpeech, TextToSpeech.QUEUE_FLUSH, param);
                                }



                        }
                    });



            }

        });

    }

    @Override

    public boolean onCreateOptionsMenu(Menu menu) {

// Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_dictionary, menu);


        return true;

    }

    @Override

    public boolean onOptionsItemSelected(MenuItem item) {

// Handle action bar item clicks here. The action bar will

// automatically handle clicks on the Home/Up button, so long

// as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

//noinspection SimplifiableIfStatement

//        if (id == R.id.action_settings) {
//
//            return true;
//
//        }

        return super.onOptionsItemSelected(item);

    }

    @Override

    protected void onPause() {

        if(convertToSpeech != null){

            convertToSpeech.stop();

            convertToSpeech.shutdown();

        }

        super.onPause();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein,R.anim.slidefromleft);

    }
}