package com.pje.def.wikibook.model;

import com.pje.def.wikibook.bdd.BookDetails;
import com.pje.def.wikibook.bdd.GenreDetails;

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
    public Genre(GenreDetails details) {
        this.genreTitle = details.getGenreTitle();
        this.genreId = details.getGenreId();
    }

    //GETTER & SETTER
    public GenreDetails getBookDetails(){
        return new GenreDetails(this.genreId, this.genreTitle);
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
