package com.pje.def.wikibook.model;

import com.pje.def.wikibook.bdd.BookDetails;
import com.pje.def.wikibook.bdd.GenderDetails;

/**
 * Created by Sim on 03/12/2015.
 */
public class Genre {

    public int genreId;
    public String genreTitle;

    public Genre(int genreId, String genreTitle) {
        this.genreId = genreId;
        this.genreTitle = genreTitle.trim();
    }

    public Genre(GenderDetails details) {
        this.genreTitle = details.getGenderTitle();
        this.genreId = details.getGenderId();
    }

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
