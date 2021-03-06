package com.pje.def.wikibook.bdd;

import android.provider.Settings;

import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.pje.def.wikibook.MainActivity;
import com.pje.def.wikibook.R;
import com.pje.def.wikibook.bdd.BookDetails;
import com.pje.def.wikibook.bdd.FilterDetails;
import com.pje.def.wikibook.model.BookFilter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Manage the bookFilterCollection
 * Created by Geoffrey on 07/10/2015.
 */
public class BookFilterCollection {

    public BookFilterCollection(){

    }

    /**
     * Request db, Get all the bookFilter in the db
     * @return
     */
    public static List<BookFilter> getBookFilters(){
        List<BookFilter> filters = new ArrayList<BookFilter>();
        try{
            List<FilterDetails> filtersDetails = MainActivity.getHelper().getFilterDao().queryForAll();
            for(FilterDetails fDetails : filtersDetails){
                BookFilter filter = new BookFilter(fDetails);
                filters.add(filter);
            }
        } catch (SQLException e){
        }
        return filters;
    }

    /**
     * add a bookFilter in the db
     * @param bookFilter
     * @return
     */
    public static boolean addBookFilter(BookFilter bookFilter){
        return addBookFilter(bookFilter.getFilterDetails());
    }

    /**
     * add bookFilter in the db
     * @param bookFilter
     * @return
     */
    public static boolean addBookFilter(FilterDetails bookFilter){
        try{
            MainActivity.getHelper().getFilterDao().create(bookFilter);
            return true;
        } catch(SQLException exception){
            return false;
        }
    }

    /**
     * remove the following filter in the db
     * @param name
     * @return
     */
    public static boolean removeBookFilter(String name){
        try{
            System.out.println("Suppression de  : " + name);
            DeleteBuilder<FilterDetails, Integer> deleteBuilder = MainActivity.getHelper().getFilterDao().deleteBuilder();
            deleteBuilder.where().eq("filter_name", name);
            deleteBuilder.delete();
            return true;
        } catch(SQLException exception){
            return false;
        }
    }

    /**
     * Request in the db, get a filter with its name
     * @param name
     * @return
     */
    public static BookFilter getBookFilter(String name){
        try {
            QueryBuilder<FilterDetails, Integer> queryBuilder = MainActivity.getHelper().getFilterDao().queryBuilder();

            queryBuilder.where().eq("filter_name", name);

            PreparedQuery<FilterDetails> preparedQuery = queryBuilder.prepare();

            List<FilterDetails> filterList = MainActivity.getHelper().getFilterDao().query(preparedQuery);
            if(!filterList.isEmpty()) return new BookFilter(filterList.get(0));
        } catch (SQLException e){
        }
        return null;
    }
}
