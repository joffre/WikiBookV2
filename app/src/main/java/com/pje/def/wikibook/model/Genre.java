package com.pje.def.wikibook.model;

import com.pje.def.wikibook.bdd.BookDetails;
import com.pje.def.wikibook.bdd.GenderDetails;

/**
 * Represent a genre
 * Created by Sim on 03/12/2015.
 */
public class Genre {

    /**
     * Id genre
     */
    public int genreId;
    /**
     * Genre title
     */
    public String genreTitle;

    public Genre(int genreId, String genreTitle) {
        this.genreId = genreId;
        this.genreTitle = genreTitle.trim();
    }

    /**
     * Create a genre with GenderDetails
     * @param details
     */
    public Genre(GenderDetails details) {
        this.genreTitle = details.getGenderTitle();
        this.genreId = details.getGenderId();
    }

    //GETTER & SETTER
    public GenderDetails getBookDetails(){
        return new GenderDetails(this.genreId, this.genreTitle);
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public String getGenreTitle() {
        return genreTitle;
    }

    public void setGenreTitle(String genreTitle) {
        this.genreTitle = genreTitle;
    }
}
