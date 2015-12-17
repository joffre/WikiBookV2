package com.pje.def.wikibook.model;

import com.pje.def.wikibook.bdd.FilterDetails;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Filter;

/**
 * Represent a bookFilter with all its information
 * Created by Geoffrey on 06/10/2015.
 */
public class BookFilter{

    /**
     * List all the filterType
     */
    public enum FilterType{
        TITLE, AUTHOR, YEAR, GENDER, DESCRIPTION, ISBN
    }
    private Map<FilterType, String> criteria;

    private String name;
    private FilterDetails fDetails;
    public BookFilter(String name, Map<FilterType, String> criteria){
        this.name = name;
        this.criteria = criteria;
    }

    public BookFilter(FilterDetails filterDetails){
        this.name = filterDetails.getFilterName();
        this.criteria = new HashMap<>();
        this.criteria.put(FilterType.TITLE, filterDetails.getFilterTitle());
        this.criteria.put(FilterType.AUTHOR, filterDetails.getFilterAuthor());
        this.criteria.put(FilterType.YEAR, filterDetails.getFilterYear());
        this.criteria.put(FilterType.GENDER, filterDetails.getFilterGenre());
        this.criteria.put(FilterType.DESCRIPTION, filterDetails.getFilterDescription());
        this.criteria.put(FilterType.ISBN, filterDetails.getFilterIsbn());
        this.fDetails = filterDetails;
    }

    public FilterDetails getFilterDetails(){
        return fDetails;
    }

    // GETTER & SETTER
    public String getName(){
        return this.name;
    }

    public String getCriterion(FilterType filterType){
        return (criteria.get(filterType) != null) ? criteria.get(filterType) : "";
    }

}
