package com.pje.def.wikibook.bdd;

import com.j256.ormlite.field.DatabaseField;

/**
 * Représente un élément de la table du même nom
 * Created by Sim on 03/12/2015.
 */
public class GenreDetails {

    @DatabaseField(id = true, generatedId = false, columnName = "genre_id")
    protected int genreId;

    @DatabaseField(columnName = "genre_title")
    protected String genreTitle;

    /**
     * Constructeur vide nécessaire au framework
     */
    public GenreDetails(){

    }

    /**
     * Contructeur du GenreDetail
     */
    public GenreDetails(int genreId, String genreTitle) {
        this.genreId = genreId;
        this.genreTitle = genreTitle;
    }

    /**
     * Getters & Setters
     */

    public String getGenreTitle() {
        return genreTitle;
    }

    public void setGenreTitle(String genreTitle) {
        this.genreTitle = genreTitle;
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }
}
