package com.abelzw.dictionary;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class MainActivity extends AppCompatActivity {


    BottomNavigationView bottomNavigation;


    private FragmentManager fragmentManager;
    //private HomeFragment fragment;


    @Override

    protected void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        fragmentManager=getSupportFragmentManager();
        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottom_navigation);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_home) {
                    HomeFragment fragment = new HomeFragment();
                    final FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.add(R.id.main_container, fragment, "Home");
                    transaction.replace(R.id.main_container, fragment).commit();
                }
              else  if (tabId == R.id.tab_favorite) {
                   Favorite Favfragment = new Favorite();
                    final FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.add(R.id.main_container, Favfragment, "Favorite").replace(R.id.main_container, Favfragment).commit();
                }
                else  if (tabId == R.id.tab_history) {
                    History fragment = new History();
                    final FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.add(R.id.main_container, fragment, "History");
                    transaction.replace(R.id.main_container, fragment).commit();

                }
                else  if (tabId == R.id.tab_setting) {
                    Settings fragment = new Settings();
                    final FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.main_container, fragment).commit();

                }

//                switch (tabId) {
//                    case R.id.tab_favorite:
//                        fragment = new HomeFragment();
//
//                        break;
//                    case R.id.fav:
//                       fragment = new Favorite();
//
//
//                     break;
//                   case R.id.hist:
//                       fragment = new History();
//                       break;
                //         }

//
//            }
            }
        });

        fragmentManager = getSupportFragmentManager();
        if(savedInstanceState==null)
        {

            getSupportFragmentManager().beginTransaction().replace(R.id.main_container,new HomeFragment())
                    .commit();

        }
//        b.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//
//
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                int id = item.getItemId();
//                switch (id) {
//                    case R.id.home:
//                        fragment = new HomeFragment();
//
//                        break;
//                    case R.id.fav:
//                       fragment = new Favorite();
//
//
//                     break;
//                   case R.id.hist:
//                       fragment = new History();
//                       break;
//                }
//                final FragmentTransaction transaction = fragmentManager.beginTransaction();
//                transaction.replace(R.id.main_container, fragment).commit();
//                return true;
//            }
//        });


//    @Override
//
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//// Inflate the menu; this adds items to the action bar if it is present.
//
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//
//        return true;
//
//    }
//
//    @Override
//
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//// Handle action bar item clicks here. The action bar will
//
//// automatically handle clicks on the Home/Up button, so long
//
//// as you specify a parent activity in AndroidManifest.xml.
//
//        int id = item.getItemId();
//
////noinspection SimplifiableIfStatement
//
////        if (id == R.id.action_settings) {
////
////            return true;
////
////        }
//
//        return super.onOptionsItemSelected(item);
//
//    }

    }
    public void btn_Nav_Visiblity(boolean check)
    {
        if(check)
            bottomNavigation.setVisibility(View.VISIBLE);
        else
            bottomNavigation.setVisibility(View.INVISIBLE);
    }
    public void showSoftKeyboard(View view) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }
}
