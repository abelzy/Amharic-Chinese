package com.abelzw.dictionary;
public class QuizObject {

    private String word;
    private String definition;
    private String definition1;

    private int history;

    public int getHistory() {
        return history;
    }

    public void setHistory(int history) {
        this.history = history;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    private  int favorite;

    public QuizObject(String word, String definition,String definition1, int favorite, int history) {
        this.word = word;
        this.definition = definition;
        this.definition1 = definition1;
        this.favorite=favorite;
        this.history=history;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDefinition() {
        return definition;
    }
    public String getDefinition1() {
        return definition1;
    }


    public void setDefinition(String definition) {
        this.definition = definition;
    }
    public void setDefinition1(String definition1) {
        this.definition1 = definition1;
    }

}