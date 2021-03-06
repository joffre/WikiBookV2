package com.pje.def.wikibook.bdd;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

/**
 * Represent an table element of table of the same name
 * Created by Geoffrey on 22/10/2015.
 */
public class FilterDetails implements Serializable {

    @DatabaseField(id = true, columnName= "filter_name")
    protected String filterName;

    @DatabaseField(columnName= "filter_title")
    protected String filterTitle;

    @DatabaseField(columnName= "filter_author")
    protected String filterAuthor;

    @DatabaseField(columnName= "filter_year")
    protected String filterYear;

    @DatabaseField(columnName= "filter_genre")
    protected String filterGenre;

    @DatabaseField(columnName= "filter_description")
    protected String filterDescription;

    @DatabaseField(columnName= "filter_isbn")
    protected String filterIsbn;

    /**
     * FilterDetails constructor
     */
    public FilterDetails(String filterName, String filterTitle, String filterAuthor, String filterYear, String filterGenre, String filterDescription, String filterIsbn) {
        this.filterName = filterName;
        this.filterTitle = filterTitle;
        this.filterAuthor = filterAuthor;
        this.filterYear = filterYear;
        this.filterGenre = filterGenre;
        this.filterDescription = filterDescription;
        this.filterIsbn = filterIsbn;
    }

    /**
     * Empty constructor used by orm framework
     */
    public FilterDetails() {
    }

    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    public String getFilterTitle() {
        return filterTitle;
    }

    public void setFilterTitle(String filterTitle) {
        this.filterTitle = filterTitle;
    }

    public String getFilterAuthor() {
        return filterAuthor;
    }

    public void setFilterAuthor(String filterAuthor) {
        this.filterAuthor = filterAuthor;
    }

    public String getFilterYear() {
        return filterYear;
    }

    public void setFilterYear(String filterYear) {
        this.filterYear = filterYear;
    }

    public String getFilterGenre() {
        return filterGenre;
    }

    public void setFilterGenre(String filterGenre) {
        this.filterGenre = filterGenre;
    }

    public String getFilterDescription() {
        return filterDescription;
    }

    public void setFilterDescription(String filterDescription) {
        this.filterDescription = filterDescription;
    }

    public String getFilterIsbn() {
        return filterIsbn;
    }

    public void setFilterIsbn(String filterIsbn) {
        this.filterIsbn = filterIsbn;
    }
}
