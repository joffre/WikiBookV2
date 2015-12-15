package com.pje.def.wikibook.model;

import com.pje.def.wikibook.bdd.FilterDetails;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Filter;

/**
 * Created by Geoffrey on 06/10/2015.
 */
public class BookFilter{

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

    public String getName(){
        return this.name;
    }

    public boolean isSelected(Book book){
        String tCrit = getCriterion(FilterType.TITLE);
        if(!book.getTitle().toLowerCase().contains(format(tCrit))){
            return false;
        }
        String tAuthor = getCriterion(FilterType.AUTHOR);
        if(!book.getAuthor().toLowerCase().contains(format(tAuthor))){
            return false;
        }
        String tYEAR = getCriterion(FilterType.YEAR);
        if(!book.getYear().toLowerCase().contains(format(tYEAR))){
            return false;
        }
        String tGender = getCriterion(FilterType.GENDER);
        if(!book.getGender().toLowerCase().contains(format(tGender))){
            return false;
        }
        String tDescription = getCriterion(FilterType.DESCRIPTION);
        if(!book.getDescription().toLowerCase().contains(format(tDescription))){
            return false;
        }
        String tISBN = getCriterion(FilterType.ISBN);
        if(!book.getDescription().toLowerCase().contains(format(tISBN))){
            return false;
        }
        return true;
    }

    public String getCriterion(FilterType filterType){
        return (criteria.get(filterType) != null) ? criteria.get(filterType) : "";
    }

    private static String format(String characters){
        return characters.trim().toLowerCase();
    }


}
