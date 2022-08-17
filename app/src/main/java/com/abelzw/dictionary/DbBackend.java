package com.abelzw.dictionary;
import android.content.Context;

import android.database.Cursor;

import java.util.ArrayList;

public class DbBackend extends DbObject{

    public DbBackend(Context context) {

        super(context);

    }
    public String[] historyWords(int swtch)
    {
        //5 word else//meaning
        String colname;
        String query = "Select kaffigna,amharic,english from dictionary where history=1 order by clickedAt  DESC";

        if(swtch==2) {
            colname="kaffigna";
        }
        else if(swtch==0) {
            colname="amharic";
        }
        else
            colname="english";
        Cursor cursor = this.getDbConnection().rawQuery(query, null);

        ArrayList<String> wordTerms = new ArrayList<String>();

        if(cursor.moveToFirst()) {

            do {

                String word = cursor.getString(cursor.getColumnIndexOrThrow(colname));

                wordTerms.add(word);

            } while (cursor.moveToNext());
        }
        cursor.close();

        String[] hisWords = new String[wordTerms.size()];

        hisWords = wordTerms.toArray(hisWords);

        return hisWords;
    }
    public String get_Date()
    {
        String query= "Select day_data from changer ";
        Cursor cursor = this.getDbConnection().rawQuery(query, null);
        String word="0";
        if(cursor.moveToFirst()) {

            do {

                word = cursor.getString(cursor.getColumnIndexOrThrow("day_data"));



            } while (cursor.moveToNext());
        }
        cursor.close();





        return word;
    }
    public String getRand_no()
    {
        String query= "Select rand_no from changer ";
        Cursor cursor = this.getDbConnection().rawQuery(query, null);
        String word="0";
        if(cursor.moveToFirst()) {

            do {

                word = cursor.getString(cursor.getColumnIndexOrThrow("rand_no"));



            } while (cursor.moveToNext());
        }
        cursor.close();





        return word;
    }
    public  String[] favoriteids()
    {
        String query="Select id ,kaffigna,amharic,english from dictionary where favorite=1 order by kaffigna";
        Cursor cursor = this.getDbConnection().rawQuery(query, null);
        ArrayList<String> wordTerms = new ArrayList<String>();

        if(cursor.moveToFirst()) {

            do {

                String word = Integer.toString(cursor.getInt(cursor.getColumnIndexOrThrow("id")));

                wordTerms.add(word);

            } while (cursor.moveToNext());
        }
        cursor.close();

        String[] favids = new String[wordTerms.size()];
        favids = wordTerms.toArray(favids);
        return favids;

    }
    public  String[] historyids()
    {
        String query="Select id,kaffigna,amharic,english from dictionary where history=1 order by clickedAt  DESC";
        Cursor cursor = this.getDbConnection().rawQuery(query, null);
        ArrayList<String> wordTerms = new ArrayList<String>();

        if(cursor.moveToFirst()) {
            do {


                String word = Integer.toString(cursor.getInt(cursor.getColumnIndexOrThrow("id")));

                wordTerms.add(word);

            } while (cursor.moveToNext());
        }
        cursor.close();

        String[] hisids = new String[wordTerms.size()];
        hisids = wordTerms.toArray(hisids);
        return hisids;

    }
    public  String[] dictionaryids()
    {
        String query="Select id,kaffigna,amharic,english from dictionary order by kaffigna";
        Cursor cursor = this.getDbConnection().rawQuery(query, null);
        ArrayList<String> wordTerms = new ArrayList<String>();

        if(cursor.moveToFirst()) {

            do {

                String word = Integer.toString(cursor.getInt(cursor.getColumnIndexOrThrow("id")));

                wordTerms.add(word);

            } while (cursor.moveToNext());
        }
        cursor.close();

        String[] dicids = new String[wordTerms.size()];
        dicids = wordTerms.toArray(dicids);
        return dicids;

    }
    public  String filterids(String kaf)
    {
        String query="Select id,kaffigna,amharic,english from dictionary where kaffigna==" +  '\'' + kaf+  '\'';
        Cursor cursor = this.getDbConnection().rawQuery(query, null);
        String filterids="1";

        if(cursor.moveToFirst()) {

            do {

                String word = Integer.toString(cursor.getInt(cursor.getColumnIndexOrThrow("id")));

                filterids=word;

            } while (cursor.moveToNext());
        }
        cursor.close();


        return filterids;

    }
    public String[] favoriteWords(int swtch)
    {
        //5 word else//meaning
        String query= "Select id ,kaffigna,amharic,english from dictionary where favorite=1 order by kaffigna";
        String colname;
        Cursor cursor = this.getDbConnection().rawQuery(query, null);
        if(swtch==2) {
            colname="kaffigna";
        }
        else if(swtch==0) {
            colname="amharic";
        }
        else
            colname="english";
        ArrayList<String> wordTerms = new ArrayList<String>();

        if(cursor.moveToFirst()) {

            do {

                String word = cursor.getString(cursor.getColumnIndexOrThrow(colname));

                wordTerms.add(word);

            } while (cursor.moveToNext());
        }
        cursor.close();

        String[] favWords = new String[wordTerms.size()];

        favWords = wordTerms.toArray(favWords);

        return favWords;
    }

    public String[] dictionaryWords(int flag){
        int check=flag;
        String query = "Select id ,kaffigna,amharic,english from dictionary order by kaffigna ";

        Cursor cursor = this.getDbConnection().rawQuery(query, null);
        ArrayList<String> wordTerms = new ArrayList<String>();
        ArrayList<String> word_amh = new ArrayList<String>();
        ArrayList<String> word_eng = new ArrayList<String>();


        if(cursor.moveToFirst()){
            String language;
            if(check==2)
            {
                language="kaffigna";
            }
            else if(check==0)
            {
                language="amharic";
            }
            else language="english";
            do{

                String word = cursor.getString(cursor.getColumnIndexOrThrow(language));

                wordTerms.add(word);


            }while(cursor.moveToNext());

        }

        cursor.close();

        String[] dictionaryWords = new String[wordTerms.size()];

        dictionaryWords = wordTerms.toArray(dictionaryWords);

        return dictionaryWords;

    }

    public  String getTranslator()
    {
        String query= "Select translator from changer ";
        Cursor cursor = this.getDbConnection().rawQuery(query, null);
        String word="0";
        if(cursor.moveToFirst()) {

            do {

                 word = cursor.getString(cursor.getColumnIndexOrThrow("translator"));



            } while (cursor.moveToNext());
        }
        cursor.close();





        return word;
    }
    public QuizObject getQuizById(int quizId){

        QuizObject quizObject = null;

        String query = "select id,kaffigna,amharic,english,favorite,history from dictionary where id = " + quizId;

        Cursor cursor = this.getDbConnection().rawQuery(query, null);

        if(cursor.moveToFirst()){

            do{

                String word = cursor.getString(cursor.getColumnIndexOrThrow("kaffigna"));

                String meaning = cursor.getString(cursor.getColumnIndexOrThrow("amharic"));
                String meaning1 = cursor.getString(cursor.getColumnIndexOrThrow("english"));

                int history = cursor.getInt(cursor.getColumnIndexOrThrow("history"));
                int favorite = cursor.getInt(cursor.getColumnIndexOrThrow("favorite"));

                quizObject = new QuizObject(word, meaning,meaning1,favorite,history);

            }while(cursor.moveToNext());

        }

        cursor.close();

        return quizObject;

    }

}