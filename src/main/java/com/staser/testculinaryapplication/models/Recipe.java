package com.staser.testculinaryapplication.models;


public class Recipe {

    private String rTitle;
    private String rHref;
    private String rIngredients;
    private String rThumbnail;


    public Recipe(String rTitle, String rHref, String rIngredients, String rThumbnail) {
        this.rTitle = rTitle;
        this.rHref = rHref;
        this.rIngredients = rIngredients;
        this.rThumbnail = rThumbnail;
    }


    public String getrTitle() {
        return this.rTitle;
    }

    public String getrHref() {
        return this.rHref;
    }

    public String getrIngridients() {
        return this.rIngredients;
    }

    public String getrThrumbnail() {
        return this.rThumbnail;
    }


    @Override
    public String toString() {
        return  "\n" +
                "rTitle:         " + this.rTitle + "\n" +
                "rHref:          " + this.rHref + "\n" +
                "rIngredients:   " + this.rIngredients + "\n" +
                "rThumbnail:     " + this.rThumbnail;
    }
}

